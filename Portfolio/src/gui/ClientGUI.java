package gui;

import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import data.storage.Document;
import data.storage.FileNode;
import server.communication.Client.Connection;

/*
 * Main gui that the user sees after logging in. 
 * Allows the user to: preview, edit, read, add, and delete files hosted on the server. 
 * Sends requests to the server for these actions.
 * 
 */
public class ClientGUI extends JFrame implements TreeSelectionListener, Observer {

	private static final long serialVersionUID = -4218365539974626814L;
	
	// JFrame
	protected JFrame frame;							// JFrame that holds the JPanel
	private JPanel contentPane;						// Pane to hold all gui components
	private JScrollPane scrollTree;					// Displays the file tree for the user
	
	// Components
	private JTree tree;								// Holds the file tree from the server
	private DefaultTreeModel model;					// Model for the tree
	private DefaultMutableTreeNode root;			// Root of the file tree
	DefaultMutableTreeNode selected;				// The currently selected node in the filetree
	
	private JTextField textField;					// Used to add files to the server. Filename set here
	private JTextArea prevTxt;						// Area for Documents to be previewed before opening
	
	private JComboBox<String> filefolder;			// Selects whether to add a file or folder to the server
	private static final String file = "File";		// String to be used in the combobox for index 0
	private static final String folder = "Folder";	// String to be used in the combobox for index 1
	private boolean fileSelected;					// Index 0 of the combo box is selected
	private boolean folderSelected;					// Index 1 of the combo box is selected
	private String[] comboBoxArr = {file, folder};	// The options in the combobox
	
	// Other GUIs
	private ArrayList<Editor> editors;				// List of editors currently opened by this client process
	private Login login = null;						// Login gui used to access this ClientGUI
	
	// Connection
	private Connection con;							// Connection to the server
	
	/*
	 * Create the frame.
	 * Initialize all components not stored on the server. 
	 * Assigns all listeners and launches the Login gui.
	 * Does not make this gui visible.
	 */
	public ClientGUI(Connection con) {
		
		this.con = con;

		/**
		 * Frame
		 */
		frame = new JFrame();
		
		frame.setTitle("Shared Drive");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 800, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setSize(800,800);
		
		/**
		 * Bottom Panel and components
		 */
		JPanel panelBot = new JPanel();
		contentPane.add(panelBot, BorderLayout.SOUTH);
		
		JButton btnOpen = new JButton("Open");
		btnOpen.addActionListener(new openListener());
		panelBot.add(btnOpen);
		
		JButton btnAdd = new JButton("Add:");
		btnAdd.addActionListener(new addListener());
		panelBot.add(btnAdd);
		
		textField = new JTextField();
		panelBot.add(textField);
		textField.setColumns(10);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new delListener());
		panelBot.add(btnDelete);
		
		filefolder = new JComboBox<String>(comboBoxArr);
		panelBot.add(filefolder);
		filefolder.setSelectedIndex(0);
		filefolder.addActionListener(new comboBoxListener());
		
		fileSelected = true;
		folderSelected = false;
		editors = new ArrayList<Editor>();
		
		/**
		 * Top Panel(Right) and components
		 */
		JPanel rightPane = new JPanel();
		rightPane.setPreferredSize(new Dimension(280, 150));
		contentPane.add(rightPane, BorderLayout.EAST);
		rightPane.setLayout(new BorderLayout());
		
		JButton prevBtn = new JButton("Preview:");
		rightPane.add(prevBtn, BorderLayout.NORTH);
		prevBtn.addActionListener(new prevListener());
		
		JScrollPane scrollTxt = new JScrollPane();
		rightPane.add(scrollTxt, BorderLayout.CENTER);
		prevTxt = new JTextArea();
		prevTxt.setEditable(false);
		scrollTxt.setViewportView(prevTxt);
		
		scrollTree = new JScrollPane();
		scrollTree.setPreferredSize(new Dimension(200, 10));
		contentPane.add(scrollTree, BorderLayout.WEST);
		
		// Set action to complete by default closing operation
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	
		    	int size = editors.size();
		    	for ( int i = 0; i < size; i++ )
		    		editors.get(i).releaseDoc();
		    	
		    	editors = null;
		    	
		    	frame.setVisible(false);
		    	System.exit(0);
		    }
		});
		
		startLogin();	// creates a new login dialog
	 }
	
	/*
	 * Creates a new login dialog.
	 * Waits for user to input username and password
	 */
	public void startLogin(){
		login = new Login(this);
		login.addWindowListener(
			    new WindowAdapter() {
			        @Override
			        public void windowClosing(WindowEvent event) {
			            login.dispose();
			            System.exit(0);
			        }
			    }
		);
		login.setVisible(true);
	}
	
	/*
	 * Returns the login gui
	 */
	public Login getLogin() {
		return login;
	}
	
	/*
	 * Returns the frame of the GUI
	 */
	public JFrame getFrame(){
		return frame;
	}
	
	/*
	 * Returns the Connection to the server
	 */
	public Connection getCon() {
		return con;
	}
	
	/*
	 * Returns the area used to preview a document
	 */
	public JTextArea prevTxt(){
		return prevTxt;
	}
	
	/*************************************************************************************
	 * 
	 * Tree component methods
	 */
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
	 * 
	 * Sets the selected node of in the tree to the user's input
	 */
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		selected = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
	}

	/*
	 * Traces through the tree to the current selected node.
	 * Appends the each node's contents in the path to a string,
	 * returns this string as a path.
	 */
	private String getFilePath(){
		
		String s = "";
		try {
			for ( int i = 0; i < selected.getUserObjectPath().length - 1; i++ ) { 
				String str = (String)selected.getUserObjectPath()[i];
				s += str + "\\";
			}
			return s + (String)selected.getUserObjectPath()[selected.getUserObjectPath().length - 1];
		} catch (NullPointerException e) {
			return s = (String)root.getUserObject();
		}
	}
	
	/*************************************************************************************
	 * 
	 * Methods to send requests to the server
	 */
	
	/*
	 * Adds a file or folder to the server.
	 * 
	 * Looks at the currently selected node in the file tree, the combobox selected index,
	 * and the text in the textfield. Uses this information to add a file or folder of the 
	 * name listed in the textfield and the location of the node in the tree to the server. 
	 * Creates a FileNode object with this information and sends it through the Connection.
	 */
	public void addFile() {
		
		String s = textField.getText();
		
		if(s != null && s != "\\s"){
			
			if (selected == null)
				selected = root;
			
			String fn = (String)selected.getUserObject();
			if (fn.contains(".")) {
				selected = (DefaultMutableTreeNode) selected.getParent();
				fn = (String)selected.getUserObject();
			}
			
			FileNode toSend = null;
			if (fileSelected) {
				if (!s.contains("."))  {	// default to a text file if no extension is given
					s += ".txt";
				}
				toSend = new FileNode(s, false, Arrays.copyOf(selected.getUserObjectPath(),selected.getUserObjectPath().length,String[].class));
			} else if (folderSelected) {
				if (s.contains(".")) {
					showErrorDialog("Invalid character: .\nFolder name cannot contain a file extension.");
					return;
				}
				toSend = new FileNode(s, true, Arrays.copyOf(selected.getUserObjectPath(),selected.getUserObjectPath().length,String[].class));
			}	
			
			con.send(toSend);
			System.out.println("Sent file/folder Add request to the server");
		}
	}
	
	/*
	 * Deletes a file or folder from the server.
	 * 
	 * Looks at the selected node in the file tree and sends a delete request to the server.
	 */
	public void delFile() {
		
		if (selected == root || selected == null)
			return;
		
		String s = (String)selected.getUserObject();
		
			
		FileNode toSend = new FileNode(s, false, Arrays.copyOf(selected.getUserObjectPath(),
				selected.getUserObjectPath().length,String[].class));
		
		toSend.setDelete(true);
		
		con.send(toSend);
	}
	
	/*
	 * Send a request to the server to open the given file
	 */
	public void accessDoc(String filename) {
		con.send(filename);
	}
	
	/*************************************************************************************
	 * 
	 * Callback functions 
	 * (called from the server-handling thread after receiving a response from the server)
	 */
	
	/*
	 * Will set the tree in our scrollpane to what the server sends to the client.
	 * (This should contain the current server file tree.
	 * 
	 * Callback method
	 */
	public void setTree(JTree newtree) {
		tree = newtree;
		
		model = (DefaultTreeModel) tree.getModel();
		root = (DefaultMutableTreeNode) model.getRoot();
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(this);
		
		scrollTree.setViewportView(null);
		scrollTree.setViewportView(tree);
		
		tree.expandRow(0);
	}
	
	/*
	 * Will create a new Editor gui for the user to edit or read a document in.
	 * 
	 * Callback method
	 */
	public void createEditor(Document doc){
		
		Editor e = new Editor(doc, con);
		if ( !doc.getReadOnly() ) {
			editors.add(e);
		}
	}
	
	/*
	 * Sets the preview area of the gui with the contents of a Document
	 * 
	 * Callback method
	 */
	public void setPreviewText(Document docprev) {
		String txt = "";
		for ( String s : docprev.getDataModel().getArr())
			txt += s;
		
		prevTxt.setText(txt);
		prevTxt.setFont(new Font("Arial", Font.PLAIN, 10));
	}
	
	/*
	 * Displays a dialogbox with an error message
	 */
	public void showErrorDialog(String error) {
		System.out.println("Recieved JDialog Message: " + error);
		JOptionPane.showMessageDialog(frame, error);
	}
	
	/*************************************************************************************
	 * 
	 * Listeners
	 */
	
	/*
	 * Listens for changes in a combobox. 
	 * Sets whether or not file or folder is selected
	 */
	private class comboBoxListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if (e.getSource().equals(filefolder)) {
				
				String val = (String)filefolder.getSelectedItem();
				
				if ( val.equals(file) ) {
					fileSelected = true;
					folderSelected = false;
				}
				else if ( val.equals(folder) ) {
					folderSelected = true;
					fileSelected = false;
				}
			}
		}
	}
	
	/*
	 * Listens for the open button press.
	 * Sends a request to the server to edit/read the selected file
	 */
	private class openListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if ( ((String)selected.getUserObject()).contains(".") )
				con.send(getFilePath());
		}
	}
	
	/*
	 * Listens for the add button press.
	 * Calls the addFile() method to request the server to add a file
	 * with a given name and location in its file tree
	 */
	private class addListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			addFile();
		}
	}
	
	/*
	 * Listens for the add button press.
	 * Calls the delFile() method to request the server to delete a file
	 * at the given location in its file tree
	 */
	private class delListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			delFile();
		}
	}
	
	/*
	 * Listens for the preview button press.
	 * Only allows the server to be queried if a 'file' is selected.
	 * Will query the server for a document to be sent in preview mode.
	 * Sends the server an empty document to be filled and sent back.
	 */
	private class prevListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if ( ((String)selected.getUserObject()).contains(".") ) {
				String filename = getFilePath();
				Document doc = new Document(filename, true);
				doc.setToPreview();
				con.send(doc);
			}
		}
	}
	
	public void observe(Observable o) {
	    o.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		scrollTree.repaint();
		scrollTree.revalidate();
	}
	
}
	

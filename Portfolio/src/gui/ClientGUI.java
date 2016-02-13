package gui;

import java.awt.*;
import java.awt.event.*;

import java.io.*;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import data.storage.Document;
import data.storage.FileNode;
import server.communication.Client.Connection;


public class ClientGUI extends JFrame implements TreeSelectionListener {

	protected JFrame frame;
	private JPanel contentPane;
	AddElementDialog listDialog;
	
	private JTree tree;
	private DefaultTreeModel model;
	private DefaultMutableTreeNode root;
	AddElementDialog treeDialog;
	private JRadioButton file;
	private JRadioButton folder;
	
	DefaultMutableTreeNode selected;
	
	// connection information
	private Connection con;
	private String name = null;
	
	private JTextField textField;
	private JTextArea prevTxt;
	
	private Login login = null;
	
	
	/**
	 * Create the frame.
	 */
	public ClientGUI(Connection con) {
		
		this.con = con;
		
		frame = new JFrame();
		
		frame.setTitle("Documents");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setSize(800,800);
		
		
		
		// creates a new login dialog
		//startLogin();
		
		/**
		 * Bottom Panel
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
		panelBot.add(btnDelete);
		
		file = new JRadioButton("file");
		folder = new JRadioButton("folder");
		
		ButtonGroup bg = new ButtonGroup();
		
		bg.add(file);
		bg.add(folder);
		panelBot.add(file);
		panelBot.add(folder);
		file.setSelected(true);
		
		/**
		 * Top Panel(Right)
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
		
		frame.setVisible(true);
		
	 }
	
	// creates a new login dialog
	// waits for user to input username and password
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
	
	public void recieveLogin(){
		name = login.getLoginName();
		
		if(name == null){
			System.out.println("Good Bye!");
			System.exit(0);
		}
	}
	
	public Login getLogin() {
		return login;
	}
	
	private class openListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try {
				if ( ((String)selected.getUserObject()).contains(".") )
					con.getOOS().writeObject((String)selected.getUserObject());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private class addListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			addFile();
		}
	}
	
	private class prevListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			//Only if a file is selected
			String txt = "";
//			String fileName = tree.getName();
			String filename = "chat.txt";
			Document doc = new Document(filename, true);
			doc.setToPreview();
			try {
				con.getOOS().writeObject(doc);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	
	public JFrame getFrame(){
		return frame;
	}
	
	public Connection getCon() {
		return con;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	// sets up the tree
	public void setTree(JTree newtree) {
		tree = newtree;

		JScrollPane scrollTree = new JScrollPane();
		contentPane.add(scrollTree, BorderLayout.WEST);
		model = (DefaultTreeModel) tree.getModel();
		root = (DefaultMutableTreeNode) model.getRoot();
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(this);
		
		scrollTree.setViewportView(tree);
		scrollTree.setPreferredSize(new Dimension(200, 10));
		
	}
	
	
	private class treeAddListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			treeDialog.setLocationRelativeTo(frame);
			treeDialog.setVisible(true);
			
		}
	}
	
	private class treeRemoveListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
				if (selected.equals(root))
					model.setRoot(null);
				else
					model.removeNodeFromParent(selected);
			}
	}
	
	void addFile() {
		
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
			if (file.isSelected())
				toSend = new FileNode(s, true, Arrays.copyOf(selected.getUserObjectPath(),selected.getUserObjectPath().length,String[].class));
			else if (folder.isSelected()) 
				toSend = new FileNode(s, false, Arrays.copyOf(selected.getUserObjectPath(),selected.getUserObjectPath().length,String[].class));
			
			con.send(toSend);
		}
	}
	
	
	
	public void accessDoc(String filename) {
		con.send(filename);
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		selected = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
	}
	
	public JTextArea prevTxt(){
		return prevTxt;
	}
	
}
	

package gui;

import java.awt.*;
import java.awt.event.*;

import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import data.storage.Document;
import data.storage.UserPass;
import server.communication.Client.Connection;


public class ClientGUI extends JFrame implements TreeSelectionListener {

	protected JFrame frame;
	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private JFrame listPopup;
	private JList list;
	private JTextField text;
	AddElementDialog listDialog;
	
	
	private JTree tree;
	private DefaultTreeModel model;
	private DefaultMutableTreeNode root;
	AddElementDialog treeDialog;
	
	DefaultMutableTreeNode selected;
	
	// connection information
	private Connection con;
	private String name = null;
	
	private JTextField textField;
	private JTextArea prevTxt;
	
	
	/**
	 * Create the frame.
	 */
	public ClientGUI(Connection con) {
		
		this.con = con;
		//this.con.addObserver((Observer) this);
		
//		try {
//			ois = new ObjectInputStream(con.getSocket().getInputStream());
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
		
		frame = new JFrame();
		
		frame.setTitle("Documents");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 1000, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setSize(800,800);
		
		Login login = new Login(this);
		login.setVisible(true);
		name = login.getLoginName();
		
		if(name == null){
			System.out.println("Good Bye!");
			System.exit(0);
		}
		
		/**
		 * Bottom Panel
		 */
		JPanel panelBot = new JPanel();
		contentPane.add(panelBot, BorderLayout.SOUTH);
		
		JButton btnOpen = new JButton("Open");
		btnOpen.addActionListener(new openListener());
		panelBot.add(btnOpen);
		
		JButton btnAdd = new JButton("Add:");
		panelBot.add(btnAdd);
		
		textField = new JTextField();
		panelBot.add(textField);
		textField.setColumns(10);
		
		JButton btnDelete = new JButton("Delete");
		panelBot.add(btnDelete);
		
		/**
		 * Top Panel(Left)
		 */
		JScrollPane scrollTree = new JScrollPane();
		contentPane.add(scrollTree, BorderLayout.WEST);
		JTree tree = new JTree(new DefaultMutableTreeNode("Files"));
		model = (DefaultTreeModel) tree.getModel();
		root = (DefaultMutableTreeNode) model.getRoot();
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(this);
		makeTree();
		
		scrollTree.setViewportView(tree);
		scrollTree.setPreferredSize(new Dimension(200, 10));
		
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
	
	
	private class openListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try {
				con.getOOS().writeObject(textField.getText());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private class prevListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			//Only if a file is selected
			String txt = "";
//			String fileName = tree.getName();
			String filename = "jazz";
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
	
	// creates the base tree
	private DefaultMutableTreeNode makeTree() {
		
		// create mammals
		DefaultMutableTreeNode mammals = new DefaultMutableTreeNode("Mammals");
		mammals.add(new DefaultMutableTreeNode("Human"));
		mammals.add(new DefaultMutableTreeNode("Kangaroo"));
		mammals.add(new DefaultMutableTreeNode("Elephant"));
		mammals.add(new DefaultMutableTreeNode("Goat"));
		model.insertNodeInto(mammals, root, root.getChildCount());
		
		// create reptiles
		DefaultMutableTreeNode reptiles = new DefaultMutableTreeNode("Reptiles");
		reptiles.add(new DefaultMutableTreeNode("Lizard"));
		reptiles.add(new DefaultMutableTreeNode("Boa"));
		reptiles.add(new DefaultMutableTreeNode("Iguana"));
		model.insertNodeInto(reptiles, root, root.getChildCount());
		
		// create birds
		DefaultMutableTreeNode birds = new DefaultMutableTreeNode("Birds");
		birds.add(new DefaultMutableTreeNode("Duck"));
		birds.add(new DefaultMutableTreeNode("Pidgeon"));
		birds.add(new DefaultMutableTreeNode("Turkey"));
		birds.add(new DefaultMutableTreeNode("Goose"));
		model.insertNodeInto(birds, root, root.getChildCount());
		
		// create insects
		DefaultMutableTreeNode insects = new DefaultMutableTreeNode("Insects");
		insects.add(new DefaultMutableTreeNode("Termite"));
		insects.add(new DefaultMutableTreeNode("Ladybug"));
		insects.add(new DefaultMutableTreeNode("Fly"));
		insects.add(new DefaultMutableTreeNode("Ant"));
		model.insertNodeInto(insects, root, root.getChildCount());
		
		// create fish
		DefaultMutableTreeNode fish = new DefaultMutableTreeNode("Fish");
		fish.add(new DefaultMutableTreeNode("Sword Fish"));
		fish.add(new DefaultMutableTreeNode("Shark"));
		fish.add(new DefaultMutableTreeNode("Eel"));
		model.insertNodeInto(fish, root, root.getChildCount());
		
		return root;
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
	
	void setAdd() {
		String s = treeDialog.getValidatedText();
		if(s != null && s != "\\s"){
			// get parent node
			DefaultMutableTreeNode temp = selected;
			if ( selected != null ) {
				if ( selected.equals(root))
					temp = selected;
				else if ( selected.getParent().getParent().equals(root))
					temp = (DefaultMutableTreeNode) selected.getParent();
			}
			else if (root == null) {
				model.setRoot(new DefaultMutableTreeNode(s));
			}
			model.insertNodeInto( new DefaultMutableTreeNode(s), temp, root.getChildCount());
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
	

package hw2;



import java.awt.BorderLayout;

import java.awt.*;
import java.awt.event.*;

import java.beans.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

public class Java2Swing extends JFrame implements TreeSelectionListener {

	protected static Java2Swing frame;
	private JPanel contentPane;
	private JFrame listPopup;
	private JList list;
	private JTextField text;
	CustomDialog customDialog;
	
	
	private JTree tree;
	private DefaultTreeModel model;
	private DefaultMutableTreeNode root;
	CustomDialog treeDialog;
	
	DefaultMutableTreeNode selected;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Java2Swing();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Java2Swing() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		// list dialog
		customDialog = new CustomDialog(frame, "geisel", "Enter new company name", "What is the new company?", this);
		customDialog.pack();
		
		// tree dialog
		treeDialog = new CustomDialog(frame, "geisel", "Enter new animal name", "What is the new animal?", this);
		treeDialog.pack();
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel listPanel = new JPanel();
		
		
		try{
			list = new JList(read());
		}
		catch(FileNotFoundException e){
			System.out.println("File Not Found!");
		}
		listPanel.add(list, BorderLayout.CENTER);
		
		
		// create list/tree/table panes
		JPanel listPane = new JPanel();
		
		tabbedPane.addTab("List", null, listPane, null);
		listPane.setLayout(new BorderLayout(0, 0));
		
		JPanel botPane = new JPanel();
		listPane.add(botPane, BorderLayout.SOUTH);
		
		JButton btnAdd = new JButton("Add");
		botPane.add(btnAdd);
		btnAdd.addActionListener(new listAddListener());
		
		JButton btnRemove = new JButton("Remove");
		botPane.add(btnRemove);
		
		JScrollPane scrollPane = new JScrollPane();
		listPane.add(scrollPane, BorderLayout.CENTER);
		
		try{
			list = new JList(read());
		}
		catch(FileNotFoundException e){
			System.out.println("File Not Found!");
		}
		
		tree = new JTree(new DefaultMutableTreeNode("Animals"));
		model = (DefaultTreeModel) tree.getModel();
		root = (DefaultMutableTreeNode) model.getRoot();
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(this);
		
		makeTree();
		
		// create list/tree/table panes
		JPanel treePane = new JPanel();
		
		tabbedPane.addTab("Tree", null, treePane, null);
		treePane.setLayout(new BorderLayout(0, 0));
		
		JPanel botTreePane = new JPanel();
		treePane.add(botTreePane, BorderLayout.SOUTH);
		
		JButton btnTreeAdd = new JButton("Add");
		botTreePane.add(btnTreeAdd);
		btnTreeAdd.addActionListener(new treeAddListener());
		
		JButton btnTreeRemove = new JButton("Remove");
		botTreePane.add(btnTreeRemove);
		btnTreeRemove.addActionListener(new treeRemoveListener());
		
		JScrollPane scrollTreePane = new JScrollPane();
		treePane.add(scrollTreePane, BorderLayout.CENTER);
		
		// set the view for list/tree/table
		scrollPane.setViewportView(list);
		scrollTreePane.setViewportView(tree);
		
		JPanel tablePanel = new JPanel();
		tabbedPane.addTab("Table", null, tablePanel, null);
		
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

	private String[] read() throws FileNotFoundException{
		File file = new File("companies.txt");

		Scanner scan = new Scanner(file);
		
		//Makes the temporary list of strings
		ArrayList<String> temp = new ArrayList<>();
		while(scan.hasNext()){
			temp.add(scan.nextLine());
		}
		
		//Makes the official array of strings
		String[] arr = new String[temp.size()];
		for(int i = 0; i < temp.size(); i++){
			arr[i] = temp.get(i);
		}
		scan.close();
		return arr;
	}
	
	private void rewrite(String input, int index) throws FileNotFoundException{
		File file = new File("companies.txt");

		Scanner scan = new Scanner(file);
		
		PrintWriter writer = new PrintWriter("companies.txt");
		
		//Makes the temporary list of strings
		ArrayList<String> arr = new ArrayList<>();
		for(int i = 0; i <= index; i++){
			arr.add(scan.nextLine());
		}
		arr.add(input);
		while(scan.hasNextLine()){
			arr.add(scan.nextLine());
		}
		scan.close();
		for(int i = 0; i < arr.size(); i++){
			writer.println(arr.get(i));
		}
		
		list = new JList(read());
	}
	
	private class listAddListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			customDialog.setLocationRelativeTo(frame);
			customDialog.setVisible(true);
			
			String s = customDialog.getValidatedText();
			if(s != null || s != "\\s"){
				try{
					rewrite(s, list.getSelectedIndex());
					list = new JList(read());
				}
				catch(FileNotFoundException e){
					System.out.println("File Not Found");
				}
			}
			
		}
	}
	
	private class treeAddListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			treeDialog.setLocationRelativeTo(frame);
			treeDialog.setVisible(true);
			
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
	
	private class treeRemoveListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
				if (selected.equals(root))
					model.setRoot(null);
				else
					model.removeNodeFromParent(selected);
			}
		}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		selected = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
	}

}
	
	
	
	
class CustomDialog extends JDialog implements ActionListener, PropertyChangeListener {
	
	JTextField textfield;
	JOptionPane opPane;
	String company;
	String okButton = "Ok";
	String cancelButton = "Cancel";
	String text = null;
	Frame mainFrame;
//	ImageIcon icon = new ImageIcon("happyFace.gif");
	
	
	
	public CustomDialog(Frame aFrame, String aWord, String title, String question, Java2Swing parent) {
		mainFrame = aFrame;
		setTitle(title);
		
		textfield = new JTextField(10);
		
		Object[] arr = {question, textfield};
		Object[] buttons = {cancelButton, okButton};
		
		opPane = new JOptionPane();
		opPane = new JOptionPane(arr, JOptionPane.QUESTION_MESSAGE,
				JOptionPane.YES_NO_OPTION, null, buttons, buttons[0]);
		setContentPane(opPane);
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				/*
				 * Instead of directly closing the window, we're going to change
				 * the JOptionPane's value property.
				 */
				opPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
			}
		});

		// Ensure the text field always gets the first focus.
		addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent ce) {
				textfield.requestFocusInWindow();
			}
		});

		// Register an event handler that puts the text into the option pane.
		textfield.addActionListener(this);

		// Register an event handler that reacts to option pane state changes.
		opPane.addPropertyChangeListener(this);
		
	}

	public String getValidatedText() {
		return company;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		String prop = event.getPropertyName();

		if (isVisible() && (event.getSource() == opPane)
				&& (JOptionPane.VALUE_PROPERTY.equals(prop)
				|| JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) {
			
			Object value = opPane.getValue();

			if (value == JOptionPane.UNINITIALIZED_VALUE) {
				// ignore reset
				return;
			}

			// Reset the JOptionPane's value.
			// If you don't do this, then if the user
			// presses the same button next time, no
			// property change event will be fired.
			opPane.setValue(JOptionPane.UNINITIALIZED_VALUE);

			if (okButton.equals(value)) {
				company = textfield.getText();
			}
			this.setVisible(false);
			((Java2Swing) mainFrame).setAdd();
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		opPane.setValue("ok");
	}
}

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

public class Java2Swing extends JFrame {

	private static Java2Swing frame;
	private JPanel contentPane;
	private JFrame listPopup;
	private JList list;
	private JTextField text;
	CustomDialog customDialog;
	
	
	private JList<String> companies;
	

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
		
		customDialog = new CustomDialog(frame, "geisel", this);
		customDialog.pack();
		
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
	
		scrollPane.setViewportView(list);
		
		JPanel treePanel = new JPanel();
		tabbedPane.addTab("Tree", null, treePanel, null);
		
		JPanel tablePanel = new JPanel();
		tabbedPane.addTab("Table", null, tablePanel, null);
		
		
		
		
		
		
		
	}

	private String[] read() throws FileNotFoundException{
//		File file = new File("companies.txt");
		File file = new File("copy_companies.txt");
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
//		File file = new File("companies.txt");
		File file = new File("copy_companies.txt");
		Scanner scan = new Scanner(file);
		
//		PrintWriter writer = new PrintWriter("companies.txt");
		PrintWriter writer = new PrintWriter("copy_companies.txt");
		
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
	
	
	
	public CustomDialog(Frame aFrame, String aWord, Java2Swing parent) {
		mainFrame = aFrame;
		setTitle("Enter New Company Name");
		
		textfield = new JTextField(10);
		Object[] arr = {"What is the new company? ", textfield};
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
				//textfield.
			}
			this.setVisible(false);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		opPane.setValue("ok");
	}
}

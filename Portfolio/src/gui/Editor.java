package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import data.storage.Document;
import data.storage.UserPass;
import gui.ClientGUI;

public class Editor extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1991783421961899236L;
	private JPanel contentPane;
	private JTextArea textArea;
	
	private Document doc;
	private ObjectOutputStream oos;
	
	private final Document d;
	
	private static final String release = "RELEASE";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, final Document doc, final ObjectOutputStream oos) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Editor frame = new Editor(doc, oos);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Editor(Document doc, final ObjectOutputStream oos) {
		this.doc = doc;
		this.oos = oos;
		
		d = doc;
		
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 100, 500, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		setTitle(doc.getName());
		/**
		 * Button Panel
		 */
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JButton btnClose = new JButton("Close");
		panel.add(btnClose);
		btnClose.addActionListener(new ActionListener(){
			  public void actionPerformed(ActionEvent e){
				  System.out.println("Closing Editor1");
				  releaseDoc();
				  setVisible(false);
//				  System.exit(0);
			  }
		  });
		
		if (!doc.getReadOnly()) {
			JButton btnSync = new JButton("Sync");
			btnSync.addActionListener(new syncListener());
			panel.add(btnSync);
		}
		else {
			JLabel lab = new JLabel("Read Only");
			panel.add(lab);
		}
		
		/**
		 * Text Area Panel
		 */
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		textArea = new JTextArea(txtForPreview());
		scrollPane.setViewportView(textArea);
		
		// disable editing if read only
		if (doc.getReadOnly())
			textArea.setEditable(false);
		
		
		
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	System.out.println("Closing editor2");
		    	releaseDoc();
		    	setVisible(false);
		    }
		});
		
		this.setVisible(true);
		
	}
	
	private String txtForPreview(){
		String txt = "";
		
		for (String s : doc.getDataModel().getArr())
			txt += s + "\r\n";
		
		return txt;
	}
	
	private class syncListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			doc.getDataModel().clear();
			Scanner scan = new Scanner(textArea.getText());
			while(scan.hasNextLine()){
				doc.getDataModel().addElement(scan.nextLine());
			}
			scan.close();
			
			System.out.println("Syncing doc (" + doc.getName() + ") with the server");
			try {
				oos.writeObject(doc);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	public void releaseDoc(){
		System.out.println("Editor: checking if doc is readonly");
		if (!doc.getReadOnly()){
	    	System.out.println("Releasing doc");
	    	UserPass up = new UserPass("$RELEASE$",doc.getName());
	        try {
				oos.writeObject(up);
			} catch (IOException e) {
				e.printStackTrace();
			}    
    	}
	}

}
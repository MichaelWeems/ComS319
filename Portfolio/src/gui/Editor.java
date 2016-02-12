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

public class Editor extends JFrame {

	private JPanel contentPane;
	private JTextArea textArea;
	
	private Document doc;
	private ObjectOutputStream oos;

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
	public Editor(Document doc, ObjectOutputStream oos) {
		this.doc = doc;
		this.oos = oos;
		
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 100, 500, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		/**
		 * Button Panel
		 */
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JButton btnClose = new JButton("Close");
		panel.add(btnClose);
		btnClose.addActionListener(new ActionListener(){
			  public void actionPerformed(ActionEvent e){
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
			
			try {
				oos.writeObject(doc);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
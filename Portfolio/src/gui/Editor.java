package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Editor extends JFrame {

	private JPanel contentPane;
	private JTextArea textArea;
	
	private String fileName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Editor frame = new Editor("File.txt");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Editor(String fileName) {
		this.fileName = fileName;
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
		
		JButton btnSync = new JButton("Sync");
		btnSync.addActionListener(new syncListener());
		panel.add(btnSync);
		
		/**
		 * Text Area Panel
		 */
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		textArea = new JTextArea(txtForPreview());
		scrollPane.setViewportView(textArea);
		
		
		this.setVisible(true);
	}
	
	private String txtForPreview(){
		String txt = "";
		
		try {
			Scanner scan = new Scanner(new File(fileName));
			while(scan.hasNextLine()){
				txt += scan.nextLine() + "\n";
			}
			scan.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("File 'File.txt' Not Found");
			e.printStackTrace();
		}
		
		return txt;
	}
	
	private class syncListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try {
				PrintWriter write = new PrintWriter(new File("File.txt"));
				Scanner scan = new Scanner(textArea.getText());
				while(scan.hasNextLine()){
					write.println(scan.nextLine());
				}
				write.close();
			} catch (FileNotFoundException e1) {
				System.out.println("File Not Found, Unable to Sync");
				e1.printStackTrace();
			}
		}
	}

}
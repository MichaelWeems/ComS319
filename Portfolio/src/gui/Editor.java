package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import data.storage.Document;
import data.storage.UserPass;
import server.communication.Client.Connection;

/*
 * Child class of JFrame. A gui that displays a given document for editing or reading.
 * Will send the contents of the viewing pane to the server when the user save's the document.
 * Conditionally displays the Document depending on the the Document's flags. Read only mode will 
 * remove the "sync" button and make the textarea uneditable. Will forfeit edit rights to a document
 * upon closing an editable Document.
 */
public class Editor extends JFrame {

	private static final long serialVersionUID = -1991783421961899236L;
	
	private JPanel contentPane;			// Holds the content of the gui
	private JTextArea textArea;			// Shows the contents of the given file
	
	private Document doc;				// The Document to be displayed
	private Connection con;				// Connection to the server over which we send the document 
	
	private static final String release = "$RELEASE$";	// variable to send the server when an
														// editable Document is closed by the user

	/*
	 * Launch this gui
	 */
	public static void main(String[] args, final Document doc, final Connection con) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Editor(doc, con);
			}
		});
	}

	/*
	 * Constructs a new Editor gui. Initializes the JFrame
	 */
	public Editor(Document doc, final Connection con) {
		this.doc = doc;
		this.con = con;
		
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
		btnClose.addActionListener(new closeListener());
		
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

		// Listens for the user closing the window from the default close option
		// Will release edit rights if needed.
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	System.out.println("Closing editor: window 'X'");
		    	releaseDoc();
		    	setVisible(false);
		    }
		});
		
		this.setVisible(true);
	}

	/*
	 * Compiles the Document's contents into a String
	 */
	private String txtForPreview(){
		String txt = "";
		
		for (String s : doc.getDataModel().getArr())
			txt += s + "\r\n";
		
		return txt;
	}
	
	/*
	 * Sends a request to release the edit lock on the 
	 * Editor's document to the server.
	 */
	public void releaseDoc(){
		System.out.print("checking if doc is readonly...");
		if (!doc.getReadOnly()){
	    	System.out.println("Document is not readonly, releasing edit rights for " + doc.getName());
	        con.send(new UserPass(release,doc.getName()));
	        return;
    	}
		System.out.println("Document is readonly, closing Editor.");
	}
	
	/*
	 * Listener for the sync button. Will replace the contents
	 * of the Document's data with the contents of the textArea
	 * and send it to the server.
	 */
	private class syncListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			doc.getDataModel().clear();
			Scanner scan = new Scanner(textArea.getText());
			
			while(scan.hasNextLine()){
				doc.getDataModel().addElement(scan.nextLine());
			}
			scan.close();
			
			System.out.println("Syncing doc (" + doc.getName() + ") with the server");
			con.send(doc);
		}
	}
	
	/*
	 * Listener for the sync button. Will replace the contents
	 * of the Document's data with the contents of the textArea
	 * and send it to the server.
	 */
	private class closeListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("Closing Editor: close button");
			  releaseDoc();
			  setVisible(false);
		}
	}
}
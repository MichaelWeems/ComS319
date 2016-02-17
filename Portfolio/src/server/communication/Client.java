package server.communication;

import javax.swing.*;

import data.storage.Document;
import data.storage.FileNode;
import data.storage.UserPass;
import gui.ClientGUI;

import java.io.*;
import java.net.*;
import java.util.*;

/*
 * A Process that can be started multiple times, each time creating a different
 * connection to a server thread. Cannot be started before the Server process.
 * Will boot up a gui to handle user input.
 */
public class Client {
	
	private static ClientGUI ui = null;			// GUI object built on JFrame for the user
	
	private static Object lockObject = null;	// Prevents the Client from setting the value
												// of an object before it is initialized.
	
	/*
	 * Holds all of the connection data between the server and the client,
	 * including the socket information and the output and input streams.
	 * Creates a server handling thread when initialized to receive
	 * server responses.
	 */
	public static class Connection extends Observable {
		
		private Socket socket;				// connection to the server

		private ObjectOutputStream oos;		// Allows the client to send objects to the server
		private ObjectInputStream ois;		// Allows the client to read objects sent from the server
		
        /*
         * Connection object Constructor.
         * Takes the server name as a string and the port to 
         * connect on as an int. Creates a connection to the 
         * server and the input/output streams associated with it.
         * 
         * Creates a thread specific to this client to handle server
         * responses.
         */
        public Connection(String server, int port) throws IOException {
            
        	socket = new Socket(server, port);		// server connection
            
            Thread receivingThread = new Thread() {	// server handling thread
                @Override
                public void run() {
                    try {
                    	
                    	oos = new ObjectOutputStream(socket.getOutputStream());		// Output stream sends objects
                    	oos.flush();												// to the server
                        
                    	ois = new ObjectInputStream(socket.getInputStream());		// Input stream receives objects
                    																// from the server
                    	
                    	try {
                    		
                    		while(true) {	// Loops until the client is killed
                    			
								Object obj = ois.readObject();  // wait for the server to send a response
								
								if (obj instanceof Document){
									Document doc = (Document)obj;
									
									if (doc.preview()){		// preview the document in the preview TextArea
										ui.setPreviewText(doc);
									}
									else {	// create a new Editor window with this document
										System.out.println("Displaying Document..." + doc.getName());
										ui.createEditor(doc);
									}
								}
								else if (obj instanceof Boolean) {
									Boolean acceptedLogin = (Boolean) obj;		 // Process if the server
									ui.getLogin().serverResponse(acceptedLogin); // accepted or declined the login
								}
								else if (obj instanceof JTree) {
									JTree tree = (JTree) obj;
									
									synchronized (lockObject) {	// wait for the client to initialize the gui
										while (ui == null) {
											try{ lockObject.wait(); }
											catch (Exception e) {}
										}
									}
									ui.setTree(tree);	// initialize the gui's tree from the server
								}
								else if (obj instanceof String) {
									String message = (String)obj;	// display an error message from the server
									ui.showErrorDialog(message);
								}
                    		}
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
                    } catch (IOException ex) {
                        notifyObservers(ex);
                    }
                }
            };
            receivingThread.start();
        }
         
        /************************************************************
         * 
         * Send functions
         */
        
         /*
          * Send a line of text to the server
          */
        public void send(String text) {
        	try {
        		oos.writeObject((Object) text);
            } catch (IOException ex) {
                notifyObservers(ex);
            }
        }
        
        /*
         * Send a UserPass object to the server
         */
        public void send(UserPass up) {
        	try {
        		oos.writeObject((Object)up);
            } catch (IOException ex) {
                notifyObservers(ex);
            }
        }
        
        /*
         * Send a Document object to the server
         */
        public void send(Document doc) {
        	try {
        		oos.writeObject((Object)doc);
            } catch (IOException ex) {
                notifyObservers(ex);
            }
        }
        
        /*
         * Send a FileNode object to the server
         */
        public void send(FileNode fn) {
        	try {
        		oos.writeObject((Object)fn);
            } catch (IOException ex) {
                notifyObservers(ex);
            }
        }
        
        /**
         * END Send functions
         * 
         ************************************************************/
        
        /*
         * Close the connection to the server
         */
        public void close() {
            try {
                socket.close();
            } catch (IOException ex) {
                notifyObservers(ex);
            }
        }
        
        /*
         * Return the socket associated 
         * with this connection
         */
        public Socket getSocket() {
        	return socket;
        }
        
        /*
         * Return the OutputStream associated
         * with this connection
         */
        public ObjectOutputStream getOOS(){
        	return oos;
        }
		
        /*
         * Return the InputStream associated
         * with this connection
         */
        public ObjectInputStream getOIS(){
        	return ois;
        }
	}
	
	/*
	 * Launch a new Client Process
	 */
	public static void main(String[] args) {

		String server = "localhost";	// server name
        int port = 4444;				// port to connect with the server
        Connection con = null;
        lockObject = new Object();
        
        try {
            con = new Connection(server, port);		// Connect with the server
        } catch (IOException ex) {
            System.out.println("Cannot connect to " + server + ":" + port);		// Cannot find the server
            ex.printStackTrace();
            System.exit(0);
        }
        
        synchronized  (lockObject) {	// notify those waiting for the gui to be initialized
	        ui = new ClientGUI(con);
	        lockObject.notifyAll();
        }
	}
}
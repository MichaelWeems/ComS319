package server.communication;

import javax.swing.*;

import data.storage.Document;
import data.storage.UserPass;
import gui.ClientGUI;
import gui.Editor;
import gui.Login;

import java.awt.Font;
import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
	
	private static ClientGUI ui = null;
	
	public static class Connection extends Observable {
		private Socket socket;

		private ObjectOutputStream oos;
		private ObjectInputStream ois;

        /** Create socket, and receiving thread */
        public Connection(String server, int port) throws IOException {
            socket = new Socket(server, port);
            
            Thread receivingThread = new Thread() {
                @Override
                public void run() {
                    try {
                    	
                    	oos = new ObjectOutputStream(socket.getOutputStream());
                    	oos.flush();
                        		
                    	ois = new ObjectInputStream(socket.getInputStream());
                    	
                    	try {
                    		while(true) {
								Object obj = ois.readObject();
								
								if (obj instanceof Document){
									Document doc = (Document)obj;
									
									if (doc.preview()){
										String txt = "";
										for ( String s : doc.getDataModel().getArr())
											txt += s;
										
										ui.prevTxt().setText(txt);
						  				ui.prevTxt().setFont(new Font("Arial", Font.PLAIN, 10));
									}
									else {
										System.out.println("Displaying Document..." + doc.getName());
										Editor editFrame = new Editor(doc, oos);
									}
								}
								else if (obj instanceof Boolean) {
									Boolean acceptedLogin = (Boolean) obj;
									
									ui.getLogin().serverResponse(acceptedLogin);
								}
//								else if (obj instanceof Node) {
//									
//								}
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
        

        /** Send a line of text */
        public void send(String text) {
        	try {
        		oos.writeObject((Object) text);
            } catch (IOException ex) {
                notifyObservers(ex);
            }
        }
        
        /** Send a line of text */
        public void send(UserPass up) {
        	try {
        		oos.writeObject((Object)up);
            } catch (IOException ex) {
                notifyObservers(ex);
            }
        }
        
        /** Send a line of text */
        public void send(Document doc) {
        	try {
        		oos.writeObject((Object)doc);
            } catch (IOException ex) {
                notifyObservers(ex);
            }
        }
        
        /** Close the socket */
        public void close() {
            try {
                socket.close();
            } catch (IOException ex) {
                notifyObservers(ex);
            }
        }
        
        public Socket getSocket() {
        	return socket;
        }
        
        public ObjectOutputStream getOOS(){
        	return oos;
        }
		
        public ObjectInputStream getOIS(){
        	return ois;
        }
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		String server = "localhost";
        int port = 4444;
        Connection con = null;
        try {
            con = new Connection(server, port);
        } catch (IOException ex) {
            System.out.println("Cannot connect to " + server + ":" + port);
            ex.printStackTrace();
            System.exit(0);
        }
        
        ui = new ClientGUI(con);
        JFrame frame = ui.getFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 400, 350);
		
	}
}
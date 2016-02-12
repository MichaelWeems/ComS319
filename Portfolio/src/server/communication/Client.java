package server.communication;

import javax.swing.*;

import data.storage.DataModel;
import data.storage.Document;
import data.storage.UserPass;
import gui.ClientGUI;
import gui.Login;
import server.communication.Client.Connection;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
	
	public static class Connection extends Observable {
		private Socket socket;
        //private java.io.OutputStream outputStream;
        //private OutputStreamWriter osw;

		private ObjectOutputStream oos;
		private ObjectInputStream ois;
		private boolean acceptedLogin = false;
		
        

        /** Create socket, and receiving thread */
        public Connection(String server, int port) throws IOException {
            socket = new Socket(server, port);
            
            
            Thread receivingThread = new Thread() {
                @Override
                public void run() {
                    try {
//                    	outputStream = socket.getOutputStream();
//                        osw = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
                    	
                    	oos = new ObjectOutputStream(socket.getOutputStream());
                    	oos.flush();
                        		
                    	ois = new ObjectInputStream(socket.getInputStream());
                    	
//                        BufferedReader reader = new BufferedReader(
//                                new InputStreamReader(socket.getInputStream()));
//                        String line;
//                        while ((line = reader.readLine()) != null);
                    	
                    	
                    	try {
                    		while(true) {
								Object obj = ois.readObject();
								
								if (obj instanceof Document){
									Document doc = (Document)obj;
							    	System.out.println(doc.getName());
									System.out.println("Displaying Document...");
									PrintWriter w = new PrintWriter("output.txt", "UTF-8");
									for ( String s : doc.getDataModel().getArr())
										w.println(s);
									w.close();
								}
//								else if (obj instanceof Boolean) {
//									Boolean accepted = (Boolean) obj;
//									if (accepted == true) {
//										
//									} else {
//										JOptionPane.showMessageDialog(optionPane, "Username or Password is invalid.");
//									}
//								}
//								else if ( obj instanceof Node ) {
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

        private static final String CRLF = "\r\n"; // newline

        /** Send a line of text */
        public void send(String text) {
        	try {
        		oos.writeObject((Object) text);
//                outputStream.write((text + CRLF).getBytes());
//                outputStream.flush();
            } catch (IOException ex) {
                notifyObservers(ex);
            }
        }
        
        /** Send a line of text */
        public void send(UserPass up) {
        	try {
        		oos.writeObject((Object)up);
//                outputStream.write((text + CRLF).getBytes());
//                outputStream.flush();
            } catch (IOException ex) {
                notifyObservers(ex);
            }
        }
        
        /** Send a line of text */
        public void send(Document doc) {
        	try {
        		oos.writeObject((Object)doc);
//                outputStream.write((text + CRLF).getBytes());
//                outputStream.flush();
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
            //(new ServerHandler(new Connection(server,port))).start();
        } catch (IOException ex) {
            System.out.println("Cannot connect to " + server + ":" + port);
            ex.printStackTrace();
            System.exit(0);
        }
        
        ClientGUI ui = new ClientGUI(con);
        JFrame frame = ui.getFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 400, 350);
		
	}

}

//class ServerHandler extends Thread {
//	private DataInputStream is = null;
//	private BufferedReader br = null;
//	private PrintStream os = null;
//	
//	private ObjectInputStream ois= null;
//	private ObjectOutputStream oos= null;
//	
//	private Socket server = null;
//	private Document doc = null;
//
//	ServerHandler(Connection con) {
//	    this.server = con.getSocket();
//	}
//
//	// This is the client handling code
//	public void run() {
//		printSocketInfo(server); // just print some information at the server side about the connection
//		System.out.println("Client: Read thread");
//		
//		 try {
//		    	System.out.println("before");
//		    	oos = new ObjectOutputStream(server.getOutputStream());
//		    	System.out.println("After");
//				ois = new ObjectInputStream(server.getInputStream());
//				System.out.println("input");
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		
//	      // Continuously loop through and check for new queries from the client
//	      while (true) {
//	    	  System.out.println("Client: Before read Object");
//	    	  Document doc;
//	    	  
//			try {
//				doc = (Document)ois.readObject();
//				System.out.println("Displaying Document...");
//				(new DisplayDocument(doc)).start();
//			} catch (ClassNotFoundException | IOException e) {
//				e.printStackTrace();
//			}
//	    	  
//	    	  int count = 0;
//	          if ( count == 10 )
//	        	  break;
//	      }
//         
//	      /*
//	       * Close the output stream, close the input stream, close the socket.
//	       */
//	      try{
//		      is.close();
//		      os.close();
//		      ois.close();
//		      oos.close();
//		      server.close();
//	      } catch (IOException e) {
//	    	  
//	      }
//	}
//
//	void printSocketInfo(Socket s) {
//		System.out.print("Socket on Server " + Thread.currentThread() + " ");
//		System.out.print("Server socket Local Address: " + s.getLocalAddress()
//				+ ":" + s.getLocalPort());
//		System.out.println("  Server socket Remote Address: "
//				+ s.getRemoteSocketAddress());
//	}
//	
//}

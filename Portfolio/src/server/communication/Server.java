package server.communication;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import data.storage.Document;
import data.storage.UserPass;
import data.storage.FileNode;

/*
 * Server Process that handles requests from one or multiple client processes.
 * Connects over sockets and creates a unique thread for each new client connection.
 * Each new thread created will individually handle its own client connection.
 * 
 * Should only be started once.
 */
public class Server {
	static ReadHandler[] rthreadArr = new ReadHandler[15];  // thread pool of client handlers
	static HashMap<String,Boolean> mutex = null;			// Ensures that no file can be edited 
															// by more than one user at a time
	
	static HashMap<String,String> userpass = null;			// Database of user login information
	
	private static ServerSocket server = null;				// Connection handle for other processes to latch onto
	private static Socket client = null;					// A connection to one particular client
	
	static DefaultMutableTreeNode root = null;				// Root of the JTree holding the file tree layout
	static DefaultTreeModel treeModel = null;				// Tree model for the JTree
	static JTree fileTree = null;							// JTree holding the file tree layout
	
	static String rootFolderName = "root";					// Name of the folder holding the file system
	
	public static void main(String[] args) throws UnknownHostException, IOException{
		
		// Build the layout of the JTree based on the filesystem
		makeFileTree();
		
		if (fileTree == null)
			System.out.println("file tree is null");  // for some reason I need this conditional.
													  // without it the tree actually is null. Doesn't make sense
		
		mutex = new HashMap<String,Boolean>();		// Hashmap to ensure no document is opened
													// for editing more than once at a time
		
		// construct the user authentication hashmap
		userpass = new HashMap<String,String>();
		userpass.put("Zach", "wild");
		userpass.put("Mike", "weems");
		userpass.put("user", "pass");
		userpass.put("a", "a");
		
		//Server end
		try {
			
			server = new ServerSocket(4444);
			
			while(true){
				client = server.accept();
				for (int i = 0; i < 15; i++) {
													/* Starts a new client handling thread 
													 * from the new connection          */
		              if (rthreadArr[i] == null) {
		            	  (rthreadArr[i] = new ReadHandler(client, rthreadArr)).start();
		            	  break;
			          }
			    }
			}
		} 
		catch (IOException e) {
//			e.printStackTrace();
			Client.main(args);
		} 
	}
	
	/*
	 * Called when server is booting up.
	 * Builds the jtree layout from the 
	 * server's filesystem.
	 */
	static void makeFileTree(){
		File fileRoot = new File(rootFolderName);
		if (!fileRoot.exists())
			fileRoot.mkdir();
		
		root = new DefaultMutableTreeNode(rootFolderName);
		treeModel = new DefaultTreeModel(root);
		fileTree = new JTree(treeModel);
        fileTree.setShowsRootHandles(true);
        
        findChildren(fileRoot,root);
	}
	
	/*
	 * Helper method for makeFileTree.
	 * Recursively traverses the filesystem
	 * to map it.
	 */
	private static void findChildren(File parent, DefaultMutableTreeNode parentNode) {
        File[] files = parent.listFiles();
        if (files == null) return;

        for (File file : files) {
        	DefaultMutableTreeNode child;

       		child = new DefaultMutableTreeNode(file.getName());
        	
        	if (child.getUserObject() == null)
        		System.out.println(file.getName());
        	
            parentNode.add(child);
            if (file.isDirectory())
                findChildren(file, child);
        }
    }
}

/*
 * Client handling thread. Relays all communication to and from 
 * the Client for the Server. 
 */
class ReadHandler extends Thread {
	
	private ObjectInputStream ois= null;   // receives messages from the client
	private ObjectOutputStream oos= null;  // sends messages to the clientt
	
	private Socket client = null;		   // socket connection to the client
	private final ReadHandler[] threadArr; // pool of client handling threads
	
	private int max;  // size of server's thread pool

	/*
	 * Create a new Readhandler thread.
	 * Started from Server's main method, this thread will
	 * handle all communication with the client.
	 */
	ReadHandler(Socket clientSocket, ReadHandler[] threads) {
	    this.client = clientSocket;
	    this.threadArr = threads;
	    max = threads.length;
	}

	// This is the client handling code
	public void run() {
		
	    try {
    	  /*
	       * Create Object input and output streams for this client.
	       * These will send and receive Document objects
	       */
    	  oos = new ObjectOutputStream(client.getOutputStream());
	      oos.flush();
		    
	      ois = new ObjectInputStream(client.getInputStream());
	      
	      // send the current filetree layout to the client
	      oos.writeObject(Server.fileTree);
	      
	      // Continuously loop through and check for new queries from the client
	      while (true) {
	    	  Object obj = ois.readObject();  // Wait for a request from the client
	    	  
	    	  if ( obj instanceof String ) {
	    		  fileReadRequest((String) obj);
	    	  }
	    	  else if (obj instanceof Document){
	    		  	Document doc = (Document) obj;
	    		  	
	    		  	if (doc.preview()) {
	    		  		previewDoc(doc);
	    		  	}
	    		  	else {
		    			 System.out.println("Writing..." + doc.getName());
			    		 doc.writeServerFile();
		    		 }
	    	  }
	    	  else if (obj instanceof UserPass){
	    		  UserPass up = (UserPass) obj;
	    		  
	    		  if ( up.getUser().equals("$RELEASE$")){    // Using a UserPassword object to assist
	    			  System.out.print("Releasing...");		 // in releasing document locks
	    			  System.out.println(up.getPass());
	    			  Server.mutex.put(up.getPass(),false);
	    		  }
	    		  else {
		    		  loginAuthenticate(up);
	    		  }
	    	  }
	    	  else if (obj instanceof FileNode){
	    		  initFile((FileNode) obj);
	    	  }
	    	  int count = 0;
	          if ( count == threadArr.length )
	        	  break;
	      }
         
	      /*
	       * Clean up. Set the current thread variable to null so that a new client
	       * could be accepted by the server.
	       */
	      synchronized (this) {
	        for (int i = 0; i < max; i++) {
	          if (threadArr[i] == this) {
	        	  threadArr[i] = null;
	          }
	        }
	      }
	      
	      /*
	       * Close the output stream, close the input stream, close the socket.
	       */
	      ois.close();
	      oos.close();
	      client.close();
	    } catch (IOException | ClassNotFoundException e) {
	    }
	}
	
	/*
	 * Accepts a string as a parameter expecting a filepath.
	 * Will read the file and give the user read or write access
	 * depending if the file is already checked out.
	 * Sends a Document object containing the file data back to 
	 * the client.
	 */
	private void fileReadRequest(String line) {
		  
		  System.out.println("Processing request for access to file: " + line);
		  
		  // open the document for the server
	  	  Document doc = new Document(line);
	  	  doc.readServerFile();
	  	  
	  	  System.out.println("Reading document on server...");
	  	  
	  	  // check if the document is already checked out.
	  	  // determine readonly mode or not
	  	  try {
		    	  if ( Server.mutex.get(doc.getName()) == true ) {
		    		  doc.setReadOnly();
		    		  System.out.println("Doc set to read only");
		    	  }
		    	  else {
		    		  Server.mutex.put(doc.getName(),true);
		    		  System.out.println("Doc is now checked out");
		    	  }
	  	  } catch (NullPointerException e) {
	  		  Server.mutex.put(doc.getName(),true);  // the file is not in the mutual exclusion list yet, so add it
	  		  System.out.println("Doc is now checked out");
	  	  }
	  	  
	  	// send document to the client
	  	  try {
			oos.writeObject(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	  	  System.out.println("Sent document to client");
	}
	
	/*
	 * Read in the file designated by the Document object parameter,
	 * populate the Document object, and then send it back to the Client.
	 * the client will check the 'preview' flag in the document and
	 * load it in to preview.
	 */
	private void previewDoc(Document doc) {
		try {
			Scanner scan = new Scanner(new File(doc.getName()));
			while(scan.hasNextLine())
				doc.addLine(scan.nextLine() + "\r\n");
			
			System.out.println("Previewing..." + doc.getName());
			oos.writeObject(doc);
			
		} catch (FileNotFoundException e1) {
			System.out.println("File Not Found, Unable to Preview");
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Checks if the username and password provided are legitimate.
	 * Sends back confirmation to the client.
	 */
	private void loginAuthenticate(UserPass up) {
		System.out.print("Processing Login...");
		try {
		  
			if (Server.userpass.get(up.getUser()).equals(up.getPass())) {
				System.out.println("Login Successful!");
				oos.writeObject((Boolean) true);
			}
			else {
				System.out.println("Login Failed!");
				oos.writeObject((Boolean) false);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			try {
				System.out.println("Login Failed!");
				oos.writeObject((Boolean) false);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	/*
	 * Initializes a file for creating or deleting.
	 * Called after receiving a request to add or
	 * delete from the client.
	 */
	private void initFile(FileNode fn) {
		  String filepath = "";
		  String fnpath[] = fn.getParentPath();
		  
		  int filepathLength = fnpath.length;
		  
		  if ( fn.getDelete() ) // don't include the filename in the delete path
			  filepathLength--;
		  
		  for ( int i = 0; i < filepathLength; i++ )
			  filepath += fnpath[i] + "\\";
		  
		  String fname = filepath + fn.getName();
		  File f = new File(fname);
		  
		  if (!f.exists()){ // add a file/folder to the file tree
			  addFileFolder(f,fname,fn);
		  }
		  else if (fn.getDelete()) { // delete a file/folder from the file tree
			  delFile(f,fname,fn);
		  }
		  
		  // update the server tree layout
		  Server.makeFileTree();
		  
		  // send out an updated copy of the tree layout to all the open clients
		  for (int i = 0; i < Server.rthreadArr.length; i++) {
			  try {
				  try {
					Server.rthreadArr[i].oos.writeObject((JTree) Server.fileTree);
				} catch (IOException e) {
					e.printStackTrace();
				}
			  } catch (NullPointerException e) { // if a client thread is null, skip it
				  continue;
			  }
		  }
	}
	
	/*
	 * Add a file or folder at the given path and name to the server.
	 */
	private void addFileFolder(File f, String fname, FileNode fn) {
		if (fn.isDir()){
  		  System.out.println("Adding folder...." + fn.getName());
			  f.mkdir();
			  System.out.println("Added " + fn.getName());
		  }
		  else {
			  System.out.println("Adding file...." + fn.getName());
			  try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			  System.out.println("Added " + fn.getName());
		  }
	}
	
	/*
	 * Delete a file or folder (and all of its subfiles/folders) from 
	 * the server.
	 */
	private void delFile(File f, String fname, FileNode fn) {
		if ( Server.mutex.containsKey(fname)) {
			if ( Server.mutex.get(fname) == false ) {
				System.out.println("Deleting file...." + fn.getName());
				deleteFiles(f);
				System.out.println("Deleted: " + fn.getName());
			}
			else {
				System.out.println("Sending JDialog Message: " + "File: " + f.getName() + " is open by another user");
				try {
					oos.writeObject("File: " + fname + " is open by another user");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		else {
			System.out.println("Deleting file...." + fn.getName());
			deleteFiles(f);
			System.out.println("Deleted: " + fn.getName());
		}
	}
	
	/*
	 * Recursive helper method for delFile.
	 */
	void deleteFiles(File f) {
		if ( f.listFiles() != null) {
			for (File file : f.listFiles()){
				if (file.isDirectory())
					deleteFiles(file);
				System.out.println(file.getName());
				file.delete();
			}
			f.delete();
		}
		else if (f.isFile()){
			f.delete();
		}
	}
}
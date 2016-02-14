package server.communication;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JTree;
import javax.swing.text.Position;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import data.storage.Document;
import data.storage.UserPass;
import data.storage.FileNode;

public class Server {
	static ArrayList<Socket> clientArr;
	static ReadHandler[] rthreadArr = new ReadHandler[15];
//	static WriteHandler[] wthreadArr = new WriteHandler[15];
	static HashMap<String,Boolean> mutex = null;
	static HashMap<String,String> userpass = null;
	
	private static ServerSocket server = null;
	private static Socket client = null;
	static ArrayList<String> chatting;
	
	static DefaultMutableTreeNode root = null;
	static DefaultTreeModel treeModel = null;
	static JTree fileTree = null;
	
	static String rootFolderName = "root";
	
	public static void main(String[] args) throws UnknownHostException, IOException{
		
		
		makeFileTree();
		
		if (fileTree == null)
			System.out.println("file tree is null");  // for some reason I need this conditional.
													  // without it the tree actually is null. Doesn't make sense
		
		clientArr = new ArrayList<>();
		chatting = new ArrayList<String>();
		mutex = new HashMap<String,Boolean>();
		userpass = new HashMap<String,String>();
		userpass.put("Zach", "wild");
		userpass.put("Mike", "weems");
		userpass.put("user", "pass");
		
		
		//Server end
		try {
			server = new ServerSocket(4444);
			System.out.println(server);
			
			while(true){
				client = server.accept();
				for (int i = 0; i < 15; i++) {
		              if (rthreadArr[i] == null) {
		            	  (rthreadArr[i] = new ReadHandler(client, rthreadArr)).start();
		            	  break;
			          }
			    }
				clientArr.add(client);
			}
			
		} 
//		Turn into client instead and uses same socket num
		catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	// called when server is booting up
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

class ReadHandler extends Thread {
	private DataInputStream is = null;
	private PrintStream os = null;
	
	private ObjectInputStream ois= null;
	private ObjectOutputStream oos= null;
	
	private Socket client = null;
	private final ReadHandler[] threadArr;
	private int max;
	
	private Document doc = null;

	ReadHandler(Socket clientSocket, ReadHandler[] threads) {
	    this.client = clientSocket;
	    this.threadArr = threads;
	    max = threads.length;
	}

	// This is the client handling code
	public void run() {
		printSocketInfo(client); // just print some information at the server side about the connection
		System.out.println("Read thread");
		
	    try {
	    	/*
		       * Create Object input and output streams for this client.
		       * These will send and recieve Document objects
		       */
	    	oos = new ObjectOutputStream(client.getOutputStream());
		    oos.flush();
		    
	      /*
	       * Create input and output streams for this client.
	       * These will check for file name queries
	       */
	      ois = new ObjectInputStream(client.getInputStream());
	      
	      oos.writeObject(Server.fileTree);
	      
	      // Continuously loop through and check for new queries from the client
	      while (true) {
	    	  System.out.println("Before readline");
	    	  Object obj = ois.readObject();
	    	  
	    	  if ( obj instanceof String ) {
	    		  String line = (String) obj;
	    		  System.out.println("Server: " + line);
	    		  
	    		  // open the document for the server
		    	  doc = new Document(line);
		    	  
		    	  System.out.println("Writing document to client");
		    	  
		    	  // check if the document is already checked out.
		    	  // determine readonly mode or not
		    	  try {
			    	  if ( Server.mutex.get(doc.getName()) == true )
			    		  doc.setReadOnly();
			    	  else
			    		  Server.mutex.put(doc.getName(),true);
		    	  } catch (NullPointerException e) {
		    		  Server.mutex.put(doc.getName(),true);  // the file is not in the mutual exclusion list yet, so add it
		    	  }
		    	  
		    	// send document to the client
		    	  oos.writeObject(doc);
		    	  System.out.println("Wrote document to client");
	    	  }
	    	  else if (obj instanceof Document){
	    		  	Document doc = (Document) obj;
	    		  
	    		  	if (doc.preview()) {
		    		  try {
			  				Scanner scan = new Scanner(new File(doc.getName()));
			  				while(scan.hasNextLine())
			  					doc.addLine(scan.nextLine() + "\r\n");
			  				
			  				oos.writeObject(doc);
		  				
			  			} catch (FileNotFoundException e1) {
			  				System.out.println("File Not Found, Unable to Preview");
			  				e1.printStackTrace();
			  			}
	    		  	}
		    		 else {
			    		  doc.writeServerFile();
			    		  Server.mutex.put(doc.getName(),false);
		    		 }
	    	  }
	    	  else if (obj instanceof UserPass){
	    		  UserPass up = (UserPass) obj;
	    		  
	    		  if (Server.userpass.get(up.getUser()).equals(up.getPass()))
	    			  oos.writeObject((Boolean) true);
	    		  else 
	    			  oos.writeObject((Boolean) false);
	    		  
	    	  }
	    	  else if (obj instanceof FileNode){
	    		  FileNode fn = (FileNode) obj;
	    		  
	    		  String filepath = "";
	    		  String fnpath[] = fn.getParentPath();
	    		  
	    		  int filepathLength = fnpath.length;
	    		  if ( fn.getDelete() )
	    			  filepathLength--;
	    		  
	    		  for ( int i = 0; i < filepathLength; i++ )
	    			  filepath += fnpath[i] + "\\";
	    		  
	    		  File f = new File(filepath + fn.getName());
	    		  
	    		  boolean delete = fn.getDelete();
	    		  
	    		  System.out.println(filepath + fn.getName());

	    		  if (!f.exists()){
	    			  if (fn.isDir()){
	    				  f.mkdir();
	    			  }
	    			  else {
	    				  f.createNewFile();
	    			  }
	    		  }
	    		  else if (delete) {
	    			  deleteFiles(f);
	    		  }
	    		  
	    		  Server.makeFileTree();
	    		  
	    		  for (int i = 0; i < Server.rthreadArr.length; i++) {
	    			  try {
	    				  Server.rthreadArr[i].oos.writeObject((JTree) Server.fileTree);
	    			  } catch (NullPointerException e) {
	    				  continue;
	    			  }
	    		  }
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
	      is.close();
	      os.close();
	      ois.close();
	      oos.close();
	      client.close();
	    } catch (IOException | ClassNotFoundException e) {
	    }
		
	}
	
	// recursively deletes all files under a folder
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

	void printSocketInfo(Socket s) {
		System.out.print("Socket on Server " + Thread.currentThread() + " ");
		System.out.print("Server socket Local Address: " + s.getLocalAddress()
				+ ":" + s.getLocalPort());
		System.out.println("  Server socket Remote Address: "
				+ s.getRemoteSocketAddress());
	}
}
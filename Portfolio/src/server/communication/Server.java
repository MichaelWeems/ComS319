package server.communication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import data.storage.Document;

public class Server {
	static ArrayList<Socket> clientArr;
	static ReadHandler[] rthreadArr = new ReadHandler[15];
//	static WriteHandler[] wthreadArr = new WriteHandler[15];
	static HashMap<String,Boolean> mutex = null;
	
	private static ServerSocket server = null;
	private static Socket client = null;
	static ArrayList<String> chatting;
	
	public static void main(String[] args) throws UnknownHostException, IOException{
		
		clientArr = new ArrayList<>();
		chatting = new ArrayList<String>();
		mutex = new HashMap<String,Boolean>();
		
		//Server end
		try {
			server = new ServerSocket(4444);
			System.out.println(server);
			
			while(true){
				client = server.accept();
				for (int i = 0; i < 15; i++) {
		              if (rthreadArr[i] == null /*&& wthreadArr[i] == null*/) {
		            	  (rthreadArr[i] = new ReadHandler(client, rthreadArr)).start();
//		            	  (wthreadArr[i] = new WriteHandler(client, wthreadArr)).start();
//		            	  threadArr[i].run();
		            	  break;
			          }
			    }
				clientArr.add(client);
			}
			
		} 
//		Turn into client instead and uses same socket num
		catch (IOException e) {
			e.printStackTrace();
			//Client.main(args);
		} 
	}
}

class ReadHandler extends Thread {
	private DataInputStream is = null;
	private BufferedReader br = null;
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
		    	  
		    	  // send document to the client
		    	  Server.mutex.put(doc.getName(),true);
		    	  oos.writeObject(doc);
		    	  System.out.println("Wrote document to client");
	    	  }
	    	  else if (obj instanceof Document){
	    		  Document doc = (Document) obj;
	    		  
	    		  if ( Server.mutex.get(doc.getName()) == true ){
		    		  // print out that the file is in use
		    		  continue;
		    	  }
		    		  
		    		  doc.writeServerFile();
		    		  Server.mutex.put(doc.getName(),false);
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

	void printSocketInfo(Socket s) {
		System.out.print("Socket on Server " + Thread.currentThread() + " ");
		System.out.print("Server socket Local Address: " + s.getLocalAddress()
				+ ":" + s.getLocalPort());
		System.out.println("  Server socket Remote Address: "
				+ s.getRemoteSocketAddress());
	}
	
//	public void send(String mess) {
//		
//		
//		for(int i = 0; i < Server.clientArr.size(); i++){
//
//			java.io.OutputStream os;
//			try {
//				os = Server.clientArr.get(i).getOutputStream();
//				OutputStreamWriter osw = new OutputStreamWriter(os);
//	             BufferedWriter bw = new BufferedWriter(osw);
//	             bw.write(mess);
//	             System.out.println("Message sent to the client is "+mess);
//	             bw.flush();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//	} // end add
}

//class WriteHandler extends Thread {
//	private DataInputStream is = null;
//	private PrintStream os = null;
//	
//	private ObjectInputStream ois= null;
//	private ObjectOutputStream oos= null;
//	
//	private Socket client = null;
//	private final WriteHandler[] threadArr;
//	private int max;
//	
//	private Document doc = null;
//
//	WriteHandler(Socket clientSocket, WriteHandler[] threads) {
//	    this.client = clientSocket;
//	    this.threadArr = threads;
//	    max = threads.length;
//	}
//
//	// This is the client handling code
//	public void run() {
//		printSocketInfo(client); // just print some information at the server side about the connection
//		System.out.println("Write thread");
//	    try {
//	      /*
//	       * Create input and output streams for this client.
//	       * These will check for file name queries
//	       */
//	      os = new PrintStream(client.getOutputStream());
//	      
//	      /*
//	       * Create Object input and output streams for this client.
//	       * These will send and recieve Document objects
//	       */
//	      ois = new ObjectInputStream(client.getInputStream());
//	      
//	      
//	      // Continuously loop through and check for new queries and objects from the client
//	      while (true) {
//	    	  doc = (Document) ois.readObject();
//	    	  
//	    	  if ( Server.mutex.get(doc.getName()) == true ){
//	    		  // print out that the file is in use
//	    		  
//	    		  continue;
//	    	  }
//	    		  Server.mutex.put(doc.getName(),true);
//	    		  doc.writeServerFile();
//	    		  Server.mutex.put(doc.getName(),false);
//	    		  
//	        
//	    	  int count = 0;
//
//	    	  if ( count == threadArr.length )
//	        	  break;
//	      }
//         
//
//	      /*
//	       * Clean up. Set the current thread variable to null so that a new client
//	       * could be accepted by the server.
//	       */
//	      synchronized (this) {
//	        for (int i = 0; i < max; i++) {
//	          if (threadArr[i] == this) {
//	        	  threadArr[i] = null;
//	          }
//	        }
//	      }
//	      /*
//	       * Close the output stream, close the input stream, close the socket.
//	       */
////	      is.close();
////	      os.close();
//	      ois.close();
//	      oos.close();
//	      client.close();
//	    } catch (IOException | ClassNotFoundException e) {
//	    }
//		
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
////	public void send(String mess) {
////		
////		
////		for(int i = 0; i < Server.clientArr.size(); i++){
////
////			java.io.OutputStream os;
////			try {
////				os = Server.clientArr.get(i).getOutputStream();
////				OutputStreamWriter osw = new OutputStreamWriter(os);
////	             BufferedWriter bw = new BufferedWriter(osw);
////	             bw.write(mess);
////	             System.out.println("Message sent to the client is "+mess);
////	             bw.flush();
////			} catch (IOException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
////		}
////		
////	} // end add
//}


class Buffer{
//	int sum = 0;
	String mess = null;
	ArrayList<String> chatting;
	
	public void chatBoard(){
		chatting = new ArrayList<>();
		File file = new File("chat.txt");
		Scanner scan = null;
		try {
			scan = new Scanner(file);
			while(scan.hasNextLine()){
				chatting.add(scan.nextLine());
			}
			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	synchronized public void add(String str) {
		
		mess = str;
		
		try { Thread.sleep((int)(Math.random()*100)); } 
		catch (InterruptedException e) { e.printStackTrace(); }
		chatting.add(mess);
		
		PrintWriter write = null;
		try {
			write = new PrintWriter("chat.txt");
			for(int i = 0; i < chatting.size(); i++){
				write.println(chatting.get(i));
			}
			write.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	} // end add
}

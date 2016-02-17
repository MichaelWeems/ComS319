package actual_lab3;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Server {
	static ArrayList<Socket> clientArr;
	static ClientHandler[] threadArr = new ClientHandler[15];
	
	private static ServerSocket server = null;
	private static Socket client = null;
	static ArrayList<String> chatting;
	
	public static void main(String[] args) throws UnknownHostException, IOException{
		
		clientArr = new ArrayList<>();
		chatting = new ArrayList<String>();
		
		//Server end
		try {
			server = new ServerSocket(4444);
			System.out.println(server);
			
			while(true){
				client = server.accept();
				for (int i = 0; i < 15; i++) {
		              if (threadArr[i] == null) {
		            	  (threadArr[i] = new ClientHandler(client, threadArr)).start();
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
		} 
	}
}

class ClientHandler extends Thread {
	private DataInputStream is = null;
	private PrintStream os = null;
	private Socket client = null;
	private final ClientHandler[] threadArr;
	private int max;

	ClientHandler(Socket clientSocket, ClientHandler[] threads) {
	    this.client = clientSocket;
	    this.threadArr = threads;
	    max = threads.length;
	}

	// This is the client handling code
	public void run() {
		printSocketInfo(client); // just print some information at the server side about the connection

	    try {
	      /*
	       * Create input and output streams for this client.
	       */
	      is = new DataInputStream(client.getInputStream());
	      os = new PrintStream(client.getOutputStream());
	      
	      OutputStreamWriter osw = new OutputStreamWriter(os);
          new BufferedWriter(osw);
          

	      
	      /* Start the conversation. */
	      while (true) {
	        @SuppressWarnings("deprecation")
			String line = is.readLine();
	        Server.chatting.add(line);
	        
	        PrintWriter write = null;
			try {
				write = new PrintWriter("chat.txt");
				for(int i = 0; i < Server.chatting.size(); i++){
					write.println(Server.chatting.get(i));
				}
				write.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	        
	        int count = 0;

	          /* The message is public, broadcast it to all other clients. */
	          synchronized (this) {
	            for (int i = 0; i < max; i++) {
	              if (threadArr[i] != null) {
	            	  threadArr[i].os.println(line);
	            	  threadArr[i].os.flush();
	              }
	              else if (threadArr[i] == null) {
	            	  count++;
	              }
	            }
	          }
	          
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
	      client.close();
	    } catch (IOException e) {
	    }
		
	}

	void printSocketInfo(Socket s) {
		System.out.print("Socket on Server " + Thread.currentThread() + " ");
		System.out.print("Server socket Local Address: " + s.getLocalAddress()
				+ ":" + s.getLocalPort());
		System.out.println("  Server socket Remote Address: "
				+ s.getRemoteSocketAddress());
	}
	
	public void send(String mess) {
		
		
		for(int i = 0; i < Server.clientArr.size(); i++){

			java.io.OutputStream os;
			try {
				os = Server.clientArr.get(i).getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os);
	             BufferedWriter bw = new BufferedWriter(osw);
	             bw.write(mess);
	             System.out.println("Message sent to the client is "+mess);
	             bw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	} // end add
}
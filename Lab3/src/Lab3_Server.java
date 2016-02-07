package lab3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class Lab3_Server {
	static ArrayList<Socket> clientArr;
	static ArrayList<Thread> threadArr;
	
	public static void main(String[] args) throws UnknownHostException, IOException{
		ServerSocket server = null;
		
		Buffer buff = new Buffer();
		clientArr = new ArrayList<>();
		threadArr = new ArrayList<>();
		
		//Server end
		try {
			server = new ServerSocket(4444);
			System.out.println(server);
			
			while(true){
				Socket client = null;
				System.out.println("Waiting for friend");
				client = server.accept();
				Thread thread = new Thread(new ClientHandler(client, buff));
				thread.start();
				clientArr.add(client);
				threadArr.add(thread);
				
			}
		} 
//		Turn into client instead and uses same socket num
		catch (IOException e) {
			e.printStackTrace();
		} 
	}
}

class ClientHandler implements Runnable {
	Socket s; // this is socket on the server side that connects to the CLIENT
	int num; // keeps track of its number just for identifying purposes
	private Buffer buff;

	ClientHandler(Socket s, Buffer buffer) {
		this.s = s;
		this.buff = buffer;
	}

	// This is the client handling code
	public void run() {
		printSocketInfo(s); // just print some information at the server side about the connection
		Scanner in;
		buff.chatBoard();
		
		try {
			// 1. USE THE SOCKET TO READ WHAT THE CLIENT IS SENDING
			in = new Scanner(s.getInputStream()); 
			String clientMessage = in.nextLine();
			
			// 2. PRINT WHAT THE CLIENT SENT
			System.out.println("Message from Client" + ":"  + clientMessage);
			buff.add(clientMessage);
			
			send(clientMessage);
			
			
		} catch (IOException e) {
			e.printStackTrace();
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
		
		
		for(int i = 0; i < Lab3_Server.clientArr.size(); i++){

			java.io.OutputStream os;
			try {
				os = Lab3_Server.clientArr.get(i).getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os);
	             BufferedWriter bw = new BufferedWriter(osw);
	             bw.write(mess);
	             System.out.println("Message sent to the client is "+mess);
	             bw.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	} // end add
}


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

package launcher;

import java.io.IOException;
import java.net.UnknownHostException;

public class Main {
	
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException{
		
		try {
			ProcessBuilder server = new ProcessBuilder("java.exe","-cp","bin","server.communication.Server");
			ProcessBuilder client = new ProcessBuilder("java.exe","-cp","bin","server.communication.Client");
			
	        server.start();
	        client.start();
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
package launcher;

import java.io.IOException;
import java.net.UnknownHostException;

import server.communication.*;

public class Main {
	
	private static boolean serverRunning = false;

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException{
		
		// run the server
		if (!serverRunning) {
			Server.main(args);
			Thread.sleep(1000);
			serverRunning = true;
		}
		
		// run a client
		Client.main(args);
	}
	
}

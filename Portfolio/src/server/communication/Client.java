package server.communication;

import javax.swing.*;

import gui.ClientGUI;
import gui.Login;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
	
	public static class Connection extends Observable {
		private Socket socket;
        private java.io.OutputStream outputStream;

        @Override
        public void notifyObservers(Object arg) {
            super.setChanged();
            super.notifyObservers(arg);
        }

        /** Create socket, and receiving thread */
        public Connection(String server, int port) throws IOException {
            socket = new Socket(server, port);
            outputStream = socket.getOutputStream();

            Thread receivingThread = new Thread() {
                @Override
                public void run() {
                    try {
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(socket.getInputStream()));
                        String line;
                        while ((line = reader.readLine()) != null)
                            notifyObservers(line);
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
                outputStream.write((text + CRLF).getBytes());
                outputStream.flush();
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
        
        ClientGUI ui = new ClientGUI(con);
        JFrame frame = ui.getFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 400, 350);
		
		
		Login login = new Login(ui, frame, "Login Page");
		login.setVisible(true);
		login.setLocationRelativeTo(frame);
		
	}

}

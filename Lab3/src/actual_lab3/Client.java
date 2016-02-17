package actual_lab3;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
	
	static class Connection extends Observable {
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
	 * Create the frame.
	 */
	static class UI extends JFrame implements Observer, Serializable {
		
		private static final long serialVersionUID = 1L;

		private Connection con;
        
        private static JFrame frame;
		private static JTextField textField;
		static JList<String> list;
		protected static DataModel listModel;

		private static ArrayList<String> chatting;
		private String name = null;
		static Socket newClient;
		
		public UI(Connection con) {
	            this.con = con;
	            con.addObserver((Observer) this);
	            make();
		 }
		
		public JFrame getFrame(){
			return frame;
		}
		
		private void make() {
	
			frame = new JFrame();
			JPanel contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			frame.setContentPane(contentPane);
			contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
			frame.setTitle("Client Side");
			
			/**
			 * Top Panel
			 */
			JPanel topPan = new JPanel();
			contentPane.add(topPan);
			topPan.setLayout(new BorderLayout(0, 0));
			
			JScrollPane scroll = new JScrollPane();
			topPan.add(scroll, BorderLayout.WEST);
			list = new JList<String>();
			chatting = new ArrayList<String>();
			listModel = new DataModel(chatting);
			list.setModel(listModel);
			scroll.add(list);
			scroll.setViewportView(list);
			scroll.setPreferredSize(new Dimension(200, 50));
			
			
	
			/**
			 * Bottom Panel
			 */
			JPanel botPan = new JPanel();
			contentPane.add(botPan);
			botPan.setLayout(new BorderLayout(0, 0));
			
			JPanel subPan = new JPanel();
			botPan.add(subPan, BorderLayout.NORTH);
			
			JLabel lblMessage = new JLabel("Message:");
			subPan.add(lblMessage);
			
			textField = new JTextField();
			subPan.add(textField);
			textField.setColumns(10);
			
			
			JButton btnSubmit = new JButton("Submit");
			subPan.add(btnSubmit);
			
			
			 // Action for the inputTextField and the goButton
	        ActionListener sendListener = new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                String str = textField.getText();
	                if (str != null && str.trim().length() > 0) {
	                    con.send(name + "> " + str);
	                    System.out.println(str);
	                }
	                textField.selectAll();
	                textField.requestFocus();
	                textField.setText("");
	            }
	        };
	        textField.addActionListener(sendListener);
	        btnSubmit.addActionListener(sendListener);
	        
	        // Ensure the text field always gets the first focus.
			frame.addComponentListener(new ComponentAdapter() {
				public void componentShown(ComponentEvent ce) {
					textField.requestFocusInWindow();
				}
			});
	        
		 }

		 /** Updates the UI depending on the Object argument */
	        public void update(Observable o, Object arg) {
	            final Object finalArg = arg;
	            SwingUtilities.invokeLater(new Runnable() {
	                public void run() {
	                    listModel.addElement(finalArg.toString());
	                    list.updateUI();
	                }
	            });
	        }
	        
	        public Connection getCon() {
	        	return con;
	        }
	        
	        public void setName(String name){
	        	this.name = name;
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
        
        UI ui = new UI(con);
        JFrame frame = ui.getFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 400, 350);
		
		
		Login login = new Login(ui, frame, "Login Page");
		login.setVisible(true);
		login.setLocationRelativeTo(frame);
		
	}

}

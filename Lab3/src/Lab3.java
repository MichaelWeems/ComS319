package lab3;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.*;

import org.omg.CORBA.portable.OutputStream;

import net.miginfocom.swing.MigLayout;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.*;
import java.util.*;

public class Lab3 extends JFrame {
	private static Lab3 frame;
	private static JPanel contentPane;
	private static JTextField textField;
	static JList<String> list;
	protected static DataModel listModel;

	private static ArrayList<String> chatting;
	private String name = null;
	static Socket newClient;

	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Lab3();
					frame.setVisible(true);
					/**
					 * Client
					 */
					//frame.startClient();
					try {
						newClient = new Socket("localhost", 4444);
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("New Client Joined");
					Thread thread = new Thread(new ServerHandler(newClient,frame));
					thread.start();
					
					

				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Lab3() {
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		setTitle("Client Side");
		
		CustomDialog login = new CustomDialog(this, "Login Page");
		login.setVisible(true);
		login.setLocationRelativeTo(frame);
		name = login.getLoginName();
		if(name == null)
			System.exit(ABORT);

		
		/**
		 * Top Panel
		 */
		JPanel topPan = new JPanel();
		contentPane.add(topPan);
		topPan.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scroll = new JScrollPane();
		topPan.add(scroll, BorderLayout.WEST);
		chatting = chatTxt();
		list = new JList<String>();
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
		textField.addActionListener(new messageListener());
		
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new messageListener());
		subPan.add(btnSubmit);
		
		
		
	}
	
	public void clientRecieve() {
		
		System.out.println("here");
		Scanner in = null;
		try {
			in = new Scanner(newClient.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		String clientMessage = in.nextLine();
		
		listModel.addElement(clientMessage);
		System.out.println("Updated in client, why is this not workiunbg then "+clientMessage);
		list.updateUI();
	}
	
	private ArrayList<String> chatTxt(){
		ArrayList<String> arr = new ArrayList<>();
		File file = new File("chat.txt");
		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		}
		while(scan.hasNextLine()){
			arr.add(scan.nextLine());
		}
		scan.close();
		return arr;
	}

	private class messageListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			String mess = name+":> " + textField.getText();
			if(mess.trim().length() > 0){
				PrintWriter write;
				try {
					write = new PrintWriter(newClient.getOutputStream());
					write.println(mess);
					write.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				list.updateUI();
				textField.setText(null);
					
			}
	
		}
	}
	
	
	
	private void startClient() throws UnknownHostException, IOException{
		//newClient = new Socket("localhost", 4444);
		//System.out.println("New Client Joined");
		
		//input = new ObjectInputStream( newClient.getInputStream());
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String message = "";
		
		
		
		
	}
	 
}


class DataModel extends AbstractListModel<String> {
	private static final long serialVersionUID = 1L;
	
	ArrayList<String> aList;
	
	public DataModel(ArrayList <String> l) {
		aList = l;
	}
	
	@Override
	public int getSize() {
		return aList.size();
	}

	@Override
	public String getElementAt(int index) {
		return aList.get(index);
	}
	
	public void addElement (String s) {
		aList.add(s);
	}
	
	public void removeElement (int index) {
		aList.remove(index);
	}
}

class CustomDialog extends JDialog implements ActionListener, PropertyChangeListener {
	private static final long serialVersionUID = 1L;
	private String logName = null;
	private JTextField textField;
	private JOptionPane optionPane;
	private String logBtn = "Login";


	public String getLoginName() {
		return logName;
	}

	public CustomDialog(Lab3 aFrame, String aWord) {
		super(aFrame, true);
		this.setBounds(100, 100, 350, 200);

		setTitle("Login Page");

		JLabel label = new JLabel("Client");
		label.setFont(new Font("HeadLineA", Font.BOLD, 36));
		String mess = "Enter Your Name";
		textField = new JTextField(10);

		Object[] array = { label, mess, textField };
		Object[] options = { logBtn };

		optionPane = new JOptionPane(array, JOptionPane.QUESTION_MESSAGE,
				JOptionPane.OK_OPTION, null, options, options[0]);

		setContentPane(optionPane);
		

		// Ensure the text field always gets the first focus.
		addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent ce) {
				textField.requestFocusInWindow();
			}
		});

		// Register an event handler that reacts to option pane state changes.
		optionPane.addPropertyChangeListener(this);
	}

	/** This method handles events for the text field. */
	public void actionPerformed(ActionEvent e) {
		optionPane.setValue(logBtn);
	}

	/** This method reacts to state changes in the option pane. */
	public void propertyChange(PropertyChangeEvent e) {
		Object value = optionPane.getValue();

		optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);

		if (logBtn.equals(value)) {
			logName = textField.getText().trim();
			if(logName.length() == 0){
				textField.setText(null);
			}
			else{
				textField.setText(null);
				setVisible(false);
				System.out.println("\"" + logName + "\"");
			}
		} 
	}

}

class ServerHandler implements Runnable {
	Socket s; // this is socket on the server side that connects to the CLIENT
	private InputStream input;
	private Lab3 lab;
	ServerHandler(Socket s, Lab3 lab) {
		this.s = s;
		this.lab = lab;
	}

	// This is the server handling code
	public void run() {
		//printSocketInfo(s); // just print some information at the server side about the connection
		
		//connectToServer(); // create a Socket to make connection
        //getStreams(); // get the input and output streams
        //processConnection(); // process connection
        
        
		
		try {
			input = s.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String message = "";
		
		while ( true )
	      { 
	         try // read message and display it
	         {
	            message =  input.readObject(); // read new message
	            Lab3.listModel.addElement( message ); // display message
	         } // end try
	         catch ( ClassNotFoundException classNotFoundException ) 
	         {} // end catch
	         catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	      } 
		
		
		
//		System.out.println("here");
//		Scanner in = null;
//		try {
//			in = new Scanner(s.getInputStream());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//		String clientMessage = in.nextLine();
//		
//		Lab3.listModel.addElement(clientMessage);
//		System.out.println("Updated in client, why is this not workiunbg then "+clientMessage);
//		Lab3.list.updateUI();
		
//		InputStream in;
//		while (lab.open) {
//			System.out.println("here");
//			try {
//				// 1. USE THE SOCKET TO READ WHAT THE CLIENT IS SENDING
//				in = frame.newClient.getInputStream();
//				InputStreamReader reader = new InputStreamReader(in);
//				BufferedReader br = new BufferedReader(reader);
//				String clientMessage = br.readLine();
//				frame.listModel.addElement(clientMessage);
//				System.out.println("Updated in client, why is this not workiunbg then "+clientMessage);
//				frame.list.updateUI();
//				
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
		
		
	}

	void printSocketInfo(Socket s) {
		System.out.print("Socket on Server " + Thread.currentThread() + " ");
		System.out.print("Server socket Local Address: " + s.getLocalAddress()
				+ ":" + s.getLocalPort());
		System.out.println("  Server socket Remote Address: "
				+ s.getRemoteSocketAddress());
	}
}

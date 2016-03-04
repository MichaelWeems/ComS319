package gui;

<<<<<<< Updated upstream
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import data.storage.UserPass;

/*
 * GUI component that is the first thing the user will see. Requests for user input
 * to receive their username and password and queries the server for login authentication.
 * If the server confirms, the Login page will be closed and the application gui will be 
 * presented. If the server denies login, the Login page will be redisplayed for the
 * user to attempt another login.
 */
public class Login extends JFrame implements ActionListener, PropertyChangeListener {
	
	private static final long serialVersionUID = 1L;
	
	public String logName = null;		// User inputted login name
	public boolean accept = false;		// The server's response to the login attempt
	public JTextField userText;			// Where the user inputs their username
	public JTextField passText;			// Where the user inputs their password
	private ClientGUI gui;				// The gui to launch when login is successful
	
	public JOptionPane optionPane;		// Links together input fields and enables dialog boxes
	public String logBtn = "Login";		// The text displayed on the login button

	/*
	 * Constructs a new Login object. Login is a child of JFrame
	 * and so this constructor initializes its gui components.
	 */
	public Login(ClientGUI gui) {
		super();
		this.setBounds(100, 100, 350, 200);
=======
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import server.communication.Client.Connection;

import data.storage.UserPass;

public class Login extends JFrame {

	private JPanel contentPane;
	private ClientGUI gui;
	private JTextField userTxt;
	private JPasswordField passTxt;
	private JButton btn;
	private String logName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Connection connect = new Connection("localhost", 4444);
					ClientGUI gui = new ClientGUI(connect);
					Login frame = new Login(gui);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login(ClientGUI gui) {
		
>>>>>>> Stashed changes
		this.gui = gui;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 320, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JLabel header = new JLabel("Login to Document Database");
		header.setFont(new Font("Ariel", Font.BOLD, 20));
		JPanel top = new JPanel();
		contentPane.add(top);
		top.add(header);
		
		/**
		 * User and Password
		 */
		JPanel cent = new JPanel();
		cent.setLayout(new FlowLayout(FlowLayout.LEFT));
		contentPane.add(cent);
		JLabel user = new JLabel("User Name:");
		userTxt = new JTextField(10);
		cent.add(user);
		cent.add(userTxt);
		
		JPanel bot = new JPanel();
		bot.setLayout(new FlowLayout(FlowLayout.LEFT));
		contentPane.add(bot);
		JLabel pass = new JLabel("  Password:");
		passTxt = new JPasswordField(10);
		bot.add(pass);
		bot.add(passTxt);
		
		btn = new JButton("Login");
		bot.add(btn);
		btn.addActionListener(new loginListener());
		
		this.setVisible(true);
	}
	
	public String getLoginName(){
		return logName;
	}

<<<<<<< Updated upstream
	/** This method reacts to state changes in the option pane. */
	public void propertyChange(PropertyChangeEvent e) {
		Object value = optionPane.getValue();

		optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);
		
		// When the login button is pressed, send the username
		// and password to the server for login authentication
		if (logBtn.equals(value)) {
			String temp = userText.getText();
			gui.getCon().send(new UserPass(temp,passText.getText()));
		} 
	}
	
	/*
	 * Callback method. Called from the server handling thread of the client,
	 * this method will receive the server's response to a login attempt and
	 * either hide the Login and launch the ClientGUI, or it will display a
	 * failure dialog and continue to display the Login gui.
	 */
=======
	private class loginListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
			logName = userTxt.getText();
			System.out.println(passTxt.getPassword());
			gui.getCon().send(new UserPass(logName, passTxt.getText()));
			
			serverResponse(true);
		}
	}
	
	
>>>>>>> Stashed changes
	public void serverResponse(boolean accepted) {
		accept = accepted;
		if(accepted){
<<<<<<< Updated upstream
			logName = userText.getText();
			passText.setText(null);
=======
			passTxt.setText(null);
>>>>>>> Stashed changes
			setVisible(false);
			System.out.println(":::: "+ logName +" Joined ::::");
			gui.getFrame().setName(logName);
			gui.setName(logName);
			gui.getFrame().setVisible(true);
		}
		else{
<<<<<<< Updated upstream
			JOptionPane.showMessageDialog(optionPane, 
					"Username or Password is invalid.\n(Hint: username 'a' password 'a')");
			passText.setText(null);
			userText.setText(null);
			userText.requestFocusInWindow();
		}
	}
	
	/*
	 * Returns the server's response to the login attempt.
	 * Defaults to false if the server hasn't responded.
	 */
	public boolean getAcceptance() {
		return accept;
	}
}
=======
			JOptionPane.showMessageDialog(contentPane, "Username or Password is invalid.");
			passTxt.setText(null);
		}
	}
}
>>>>>>> Stashed changes

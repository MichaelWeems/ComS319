package gui;

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
		this.gui = gui;

		setTitle("Login");

		JLabel username = new JLabel("Username");
		JLabel password = new JLabel("Password:");
		userText = new JTextField(10);
		passText = new JPasswordField(10);

		Object[] array = { username, userText, password, passText};
		Object[] options = {logBtn};

		optionPane = new JOptionPane(array, JOptionPane.QUESTION_MESSAGE,
				JOptionPane.OK_OPTION, null, options, options[0]);

		setContentPane(optionPane);

		// Ensure the text field always gets the first focus.
		addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent ce) {
				userText.requestFocusInWindow();
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
	public void serverResponse(boolean accepted) {
		accept = accepted;
		if(accepted){
			logName = userText.getText();
			passText.setText(null);
			setVisible(false);
			System.out.println(":::: "+ logName +" Joined ::::");
			gui.getFrame().setName(logName);
			gui.setName(logName);
			gui.getFrame().setVisible(true);
		}
		else{
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
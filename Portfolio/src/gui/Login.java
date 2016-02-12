package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import data.storage.Document;
import data.storage.UserPass;

public class Login extends JDialog implements ActionListener, PropertyChangeListener {
	private HashMap hashmap;
	private static final long serialVersionUID = 1L;
	public String logName = null;
	public JTextField userText;
	public JTextField passText;
	private ClientGUI gui;
	
	
	public JOptionPane optionPane;
	public String logBtn = "Login";


	public String getLoginName() {
		return logName;
	}

	public Login(ClientGUI gui) {
		super(gui.getFrame(), true);
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

		if (logBtn.equals(value)) {
			logName = userText.getText();
			gui.getCon().send(new UserPass(logName,passText.getText()));
			serverResponse(true);
		} 
	}
	
	public void serverResponse(boolean accepted) {
		if(accepted){
			passText.setText(null);
			setVisible(false);
			System.out.println(":::: "+ logName +" Joined ::::");
			gui.getFrame().setName(logName);
			gui.setName(logName);
		}
		else{
			JOptionPane.showMessageDialog(optionPane, "Username or Password is invalid.");
			passText.setText(null);
		}
	}
}
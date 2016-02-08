package actual_lab3;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.Socket;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import actual_lab3.Client.UI;

public class Login extends JDialog implements ActionListener, PropertyChangeListener {
	private static final long serialVersionUID = 1L;
	private String logName = null;
	private JTextField textField;
	private JOptionPane optionPane;
	private String logBtn = "Login";
	private JFrame aFrame;
	private UI ui;

	public String getLoginName() {
		return logName;
	}

	public Login(UI ui, JFrame aFrame, String aWord) {
		super();
		this.setBounds(100, 100, 350, 200);
		
		this.aFrame = aFrame;
		this.ui = ui;

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
				aFrame.setVisible(true);
				aFrame.setName(logName);
				ui.setName(logName);
				System.out.println("\"" + logName + "\"");
			}
		} 
	}


	void printSocketInfo(Socket s) {
		System.out.print("Socket on Server " + Thread.currentThread() + " ");
		System.out.print("Server socket Local Address: " + s.getLocalAddress()
				+ ":" + s.getLocalPort());
		System.out.println("  Server socket Remote Address: "
				+ s.getRemoteSocketAddress());
	}
}
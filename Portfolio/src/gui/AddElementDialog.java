package gui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class AddElementDialog extends JDialog implements ActionListener, PropertyChangeListener {
	
	JTextField textfield;
	JOptionPane opPane;
	String filename;
	String okButton = "Ok";
	String cancelButton = "Cancel";
	String text = null;
	Frame mainFrame;
	ClientGUI gui = null;
	
	
	public AddElementDialog(Frame aFrame, String aWord, String title, String question, ClientGUI parent) {
		mainFrame = aFrame;
		gui = parent;
		setTitle(title);
		
		textfield = new JTextField(10);
		
		Object[] arr = {question, textfield};
		Object[] buttons = {cancelButton, okButton};
		
		opPane = new JOptionPane();
		opPane = new JOptionPane(arr, JOptionPane.QUESTION_MESSAGE,
				JOptionPane.YES_NO_OPTION, null, buttons, buttons[0]);
		setContentPane(opPane);
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				/*
				 * Instead of directly closing the window, we're going to change
				 * the JOptionPane's value property.
				 */
				opPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
			}
		});

		// Ensure the text field always gets the first focus.
		addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent ce) {
				textfield.requestFocusInWindow();
			}
		});

		// Register an event handler that puts the text into the option pane.
		textfield.addActionListener(this);

		// Register an event handler that reacts to option pane state changes.
		opPane.addPropertyChangeListener(this);
		
	}

	public String getValidatedText() {
		return filename;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		String prop = event.getPropertyName();

		if (isVisible() && (event.getSource() == opPane)
				&& (JOptionPane.VALUE_PROPERTY.equals(prop)
				|| JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) {
			
			Object value = opPane.getValue();

			if (value == JOptionPane.UNINITIALIZED_VALUE) {
				// ignore reset
				return;
			}

			// Reset the JOptionPane's value.
			// If you don't do this, then if the user
			// presses the same button next time, no
			// property change event will be fired.
			opPane.setValue(JOptionPane.UNINITIALIZED_VALUE);

			if (okButton.equals(value)) {
				filename = textfield.getText();
			}
			gui.accessDoc(textfield.getText());
			this.setVisible(false);
			//gui.setAdd();
			
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		opPane.setValue("ok");
	}
}
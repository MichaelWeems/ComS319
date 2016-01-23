package cs319;

import java.awt.EventQueue;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.*;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class Lab2Swing extends JFrame {
	
	private JList<ArrayList<String>> list;
	private static DataModel dm;
	private static ArrayList<String> arr;

	private JPanel contentPane;
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
	private JTabbedPane tabbedPane_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					dm = new DataModel();
					
					Lab2Swing frame = new Lab2Swing();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				// Populate the list tab
				
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Lab2Swing() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		tabbedPane.setBounds(0, 0, 434, 236);
		contentPane.add(tabbedPane);
		
		JScrollPane scrollPane = new JScrollPane();
		tabbedPane.addTab("List", null, scrollPane, null);
		try{
			arr = dm.read();
		}
		catch(FileNotFoundException e){
			System.out.println("File Not Found!");
		}
		String temp[];
		for ( int i = 0; i < arr.size(); i++) {
			temp[i] = arr.get(i);
		}
		
		list = new JList<String>(arr);
		scrollPane.add(list);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		tabbedPane.addTab("New tab", null, scrollPane_1, null);
		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{tabbedPane}));
	}
}

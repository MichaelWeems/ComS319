package data.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.AbstractListModel;

/*
 * Object containing a way to store information. Tailored for 
 * file reading, this class can save the contents of a file into
 * its storage
 */
public class DataModel extends AbstractListModel<String>{
	
	/*
	 * Allows this object to be sent through the socket
	 */
	private static final long serialVersionUID = 6367652622816740091L;
	
	private ArrayList<String> arr;		// Holds the data for this DataModel
	
	/*
	 * Constructs a new DataModel object.
	 * The DataModel uses an ArrayList as
	 * its storage
	 */
	public DataModel() {
		arr = new ArrayList<String>();
	}
	
	/*
	 * Constructs a copy of an existing DataModel object
	 */
	public DataModel(DataModel dm) {
		arr = new ArrayList<String>();
		for ( String s : dm.getArr() )
			arr.add(s);
	}
	
	/*
	 * Constructs a new DataModel with a shallow copy of an ArrayList
	 */
	public DataModel(ArrayList <String> l) {
		arr = l;
	}
	
	/*
	 * Reads in a file from the server and adds each line to the
	 * DataModel. Returns the ArrayList
	 */
	public ArrayList<String> readInFile(String filename){
		File f = new File(filename);

		Scanner scan = null;
		try {
			scan = new Scanner(f);
			
			while(scan.hasNext()){
				arr.add(scan.nextLine());
			}
			
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return arr;
	}

	@Override
	public int getSize() {
		return arr.size();
	}

	public String getElementAt(int index) {
		return arr.get(index);
	}
	
	/*
	 * Adds an element to this DataModel
	 */
	public void addElement (String s) {
		arr.add(s);
	}
	
	/*
	 * Removes an element from this DataModel at
	 * the specified index.
	 */
	public void removeElement (int index) {
		arr.remove(index);
	}
	
	/*
	 * Returns the ArrayList used for this DataModel
	 */
	public ArrayList<String> getArr(){
		return arr;
	}
	
	/*
	 * Clears this DataModel of data
	 */
	public void clear(){
		arr = new ArrayList<String>();
	}
}
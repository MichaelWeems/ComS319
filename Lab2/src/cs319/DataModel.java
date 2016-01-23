package cs319;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings({ "serial", "rawtypes" })
public class DataModel extends javax.swing.AbstractListModel{
	
	private ArrayList<String> arr;
	
	public DataModel() {
		arr = new ArrayList<String>();
	}
	
	public ArrayList<String> read() throws FileNotFoundException{
		File f = new File("companies.txt");
		Scanner scan = new Scanner(f);
		while(scan.hasNext()){
			arr.add(scan.nextLine());
		}
		return arr;
	}

	@Override
	public int getSize() {
		return arr.size();
	}

	@Override
	public Object getElementAt(int index) {
		return (Object) arr.get(index);
	}

}

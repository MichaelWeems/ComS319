package data.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.AbstractListModel;

@SuppressWarnings({ "serial", "rawtypes" })
public class DataModel extends AbstractListModel<String>{
	
	private ArrayList<String> arr;
	
	// new arr constructor
	public DataModel() {
		arr = new ArrayList<String>();
	}
	
	// shallow copy constructor
	public DataModel(ArrayList <String> l) {
		arr = l;
	}
	
	public ArrayList<String> readInFile(String filename){
		File f = new File(filename);
		System.out.println(filename);
		Scanner scan = null;
		try {
			scan = new Scanner(f);
			while(scan.hasNext()){
				arr.add(scan.nextLine());
			}
			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
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
	
	public void addElement (String s) {
		arr.add(s);
	}
	
	public void removeElement (int index) {
		arr.remove(index);
	}
	
	public ArrayList<String> getArr(){
		return arr;
	}
	
	public void clear(){
		arr = new ArrayList<String>();
	}
	
}
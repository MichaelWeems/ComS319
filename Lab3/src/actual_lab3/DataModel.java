package actual_lab3;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

public class DataModel extends AbstractListModel<String> {
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

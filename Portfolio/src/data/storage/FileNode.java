package data.storage;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

public class FileNode {
	
	public String fileName;
	private boolean isDir;
	private String parentPath[];
	
	public FileNode(String name, boolean isDirectory, String path[]){
		fileName = name;
		isDir = isDirectory;
		parentPath = path;
	}
	
	public String getName(){
		return fileName;
	}
	
	public boolean isDir(){
		return isDir;
	}
	
	public String[] getParentPath(){
		return parentPath;
	}
	
	public String toString() {
	    return this.fileName;
	}
	
}

package data.storage;

import java.io.Serializable;

public class FileNode implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6442266292878872160L;
	public String fileName;
	private boolean isDir;
	private String parentPath[];
	private boolean delete;
	
	public FileNode(String name, boolean isDirectory, String path[]){
		fileName = name;
		isDir = isDirectory;
		parentPath = path;
		delete = false;
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
	
	public void setDelete(boolean del){
		delete = del;
	}
	
	public boolean getDelete(){
		return delete;
	}
	
}

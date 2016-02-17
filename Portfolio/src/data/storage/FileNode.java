package data.storage;

import java.io.Serializable;

/*
 * Object containing general file information.
 * Assists the client in sending file
 * data to the server for processing.
 */
public class FileNode implements Serializable {
	
	/*
	 * Allows this to be sent through the socket 
	 */
	private static final long serialVersionUID = 1L;

	public String fileName;			// The file's name
	private boolean isDir;			// File is or isn't a directory
	private String parentPath[];	// Holds the path from the root folder
									// to this file.
	private boolean delete;			// Whether or not to delete this file
	
	/*
	 * Construct a new FileNode object from the given
	 * filename. Determine if it is a directory, and set
	 * the path from the root to this file.
	 */
	public FileNode(String name, boolean isDirectory, String path[]){
		fileName = name;
		isDir = isDirectory;
		parentPath = path;
		delete = false;
	}
	
	/*
	 * Return the name of the file
	 */
	public String getName(){
		return fileName;
	}
	
	/*
	 * Is this file a directory?
	 */
	public boolean isDir(){
		return isDir;
	}
	
	/*
	 * Get the path from the root to this
	 * file as an array of Strings.
	 */
	public String[] getParentPath(){
		return parentPath;
	}
	
	// print the filename
	public String toString() {
	    return this.fileName;
	}
	
	/*
	 * set whether or not to delete this file
	 */
	public void setDelete(boolean del){
		delete = del;
	}
	
	/*
	 * get whether or not to delete this file
	 */
	public boolean getDelete(){
		return delete;
	}
}

package data.storage;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/*
 * Object containing the contents of a file, the file's name,
 * and whether or not to preview, set to read only, or release
 * the lock on this file in the server
 */
public class Document implements Serializable{
	
	/*
	 * Allows this object to be sent through the socket
	 */
	private static final long serialVersionUID = -2666440411391895074L;

	private DataModel dataModel;	// Holds all the file contents
	private String filename = "";	// Name of the file in question
	private boolean preview;		// Set to preview?
	private boolean readonly;		// Set to read only?
	public boolean release;			// Is the user done editing this document?
	
	/*
	 * Constructs a Document object.
	 * sets all flags to false, creates an empty datamodel,
	 * and gives the document a temporary name.
	 */
	public Document() {
		dataModel = new DataModel();
		filename = "temp.txt";
		preview = false;
		readonly = false;
		release = false;
	}
	
	/*
	 * Copy Constructor for a Document object.
	 */
	public Document(Document d) {
		dataModel = new DataModel(d.getDataModel());
		filename = d.getName();
		preview = d.preview;
		readonly = d.preview;
		release = d.release;
	}
	
	/*
	 * Constructs a new Document with the given filename
	 */
	public Document(String filename) {
		dataModel = new DataModel();
		this.filename = filename;
		preview = false;
		readonly = false;
		release = false;
	}
	
	/*
	 * Constructs a new Document with the given filename
	 * and preview flag.
	 */
	public Document(String filename, boolean preview) {
		dataModel = new DataModel();
		this.filename = filename;
		this.preview = preview;
		readonly = false;
		release = false;
	}
	
	/*
	 * Will read a document from the server into
	 * the datamodel
	 */
	public void readServerFile(){
		dataModel.readInFile(filename);
	}
	
	/*
	 * Writes what is in the datamodel into the file 
	 * on the server. Uses the filename to get the 
	 * complete path.
	 */
	public void writeServerFile(){
		
		List<String> lines = dataModel.getArr();
		Path file = Paths.get(filename);
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Add an element to the datamodel
	 */
	public void addLine(String line){
		dataModel.addElement(line);
	}
	
	/*
	 * Returns the filename for this Document
	 */
	public String getName(){
		return filename;
	}
	
	/*
	 * Set the datamodel for this Document
	 * to another existing datamodel
	 */
	public void setDataModel(DataModel dm){
		dataModel = dm;
	}
	
	/*
	 * Returns the datamodel for this Document
	 */
	public DataModel getDataModel(){
		return dataModel;
	}
	
	/*
	 * Set this document to preview
	 */
	public void setToPreview(){
		preview = true;
	}
	
	/*
	 * Returns if this Document is set to preview or not
	 */
	public boolean preview(){
		return preview;
	}
	
	/*
	 * Set this Document to read only
	 */
	public void setReadOnly(){
		readonly = true;
	}
	
	/*
	 * Returns if this Document is set to read only or not
	 */
	public boolean getReadOnly(){
		return readonly;
	}
	
	/*
	 * 
	 */
	public void setRelease(){
		release = true;
	}
	
	/*
	 * Returns if this Document's edit lock will be released
	 * for others to use
	 */
	public boolean getRelease(){
		return release;
	}
}

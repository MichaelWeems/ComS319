package data.storage;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Document implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2666440411391895074L;

	private DataModel dataModel;
	
	private String filename = "";
	
	private boolean preview;
	private boolean readonly;
	public boolean release;
	
	// Empty Constructor
	public Document() {
		
		dataModel = new DataModel();
		filename = "temp.txt";
		preview = false;
		readonly = false;
		release = false;
		
//		try {
//		     doc = new File(filename);
//		      if (doc.createNewFile()){
//		        System.out.println("File is created!");
//		      }else{
//		        System.out.println("File already exists.");
//		      }
//	    	} catch (IOException e) {
//		      e.printStackTrace();
//		}
	}
	
	// copy Constructor
	public Document(Document d) {
		
		dataModel = new DataModel(d.getDataModel());
		filename = d.getName();
		preview = d.preview;
		readonly = d.preview;
		release = d.release;
		
//		try {
//		     doc = new File(filename);
//		      if (doc.createNewFile()){
//		        System.out.println("File is created!");
//		      }else{
//		        System.out.println("File already exists.");
//		      }
//	    	} catch (IOException e) {
//		      e.printStackTrace();
//		}
	}
	
	// Don't include file extension in the file name
	public Document(String filename) {
		
		dataModel = new DataModel();
		this.filename = filename;
		preview = false;
		readonly = false;
		release = false;
		
//		try {
//		      doc = new File(filename);
//		      if (!doc.exists()) {
//		    	  
//		      //if (doc.createNewFile()){
//		    	doc.createNewFile();
//		        System.out.println("File is created!");
//		        
//		        // display the new document
//		      }
//		      //}else{
//		        //System.out.println("File already exists.");
//		      else {
//		        // read in file as arraylist
//		        readServerFile();
//		        System.out.println("File is being read!");
//		      }
//	    	} catch (IOException e) {
//		      //e.printStackTrace();
//	    		 System.out.println("File already exists.");
//			        
//			        // read in file as arraylist
//			        readServerFile();
//		}
	}
	
	// Preview Constructor
	public Document(String filename, boolean preview) {
		dataModel = new DataModel();
		this.filename = filename;
		this.preview = preview;
		readonly = false;
		release = false;
	}
	
	// copy the file into our datamodel
	public void readServerFile(){
		dataModel.readInFile(filename);
	}
	
	public void writeServerFile(){
		
		List<String> lines = dataModel.getArr();
		Path file = Paths.get(filename);
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// copy the file into our datamodel
	public void readClientFile(/* TextArea ta */){
		
		// read in the string stored in the TextArea
		
		// copy contents of string into arraylist
		// each line of the input is a new arraylist element
	}
	
	public void writeClientFile(/* TextArea ta */){
		
		// copy datamodel into the text editing area
		// each arraylist element is a new line in the document
	}
	
	public void addLine(String line){
		dataModel.addElement(line);
	}
	
	public String getName(){
		return filename;
	}
	
	public void setDataModel(DataModel dm){
		dataModel = dm;
	}
	
	public DataModel getDataModel(){
		return dataModel;
	}
	
	public void setToPreview(){
		preview = true;
	}
	
	public boolean preview(){
		return preview;
	}
	
	public void setReadOnly(){
		readonly = true;
	}
	
	public boolean getReadOnly(){
		return readonly;
	}
	
	public void setRelease(){
		release = true;
	}
	
	public boolean getRelease(){
		return release;
	}
	
}

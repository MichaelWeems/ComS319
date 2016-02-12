package data.storage;

public class UserPass {
	
	private String user;
	private String pass;
	
	public UserPass(String user, String pass){
		this.user = user;
		this.pass = pass;
	}
	
	public String getUser(){
		return user;
	}
	
	public String getPass(){
		return pass;
	}
}

package data.storage;

import java.io.Serializable;

public class UserPass implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4116596012680909966L;
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

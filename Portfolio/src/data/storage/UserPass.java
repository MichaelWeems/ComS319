package data.storage;

import java.io.Serializable;

/*
 * Object holding two Strings.
 * Used to send username and password 
 * input from the user to the server.
 */
public class UserPass implements Serializable {
	
	/*
	 * Allows this to be sent through sockets
	 */
	private static final long serialVersionUID = 4116596012680909966L;
	
	private String user;	// Username field
	private String pass;	// password field
	
	/*
	 * Constructs a new UserPass Object from
	 * the given username and password
	 */
	public UserPass(String user, String pass){
		this.user = user;
		this.pass = pass;
	}
	
	/*
	 * Returns the username
	 */
	public String getUser(){
		return user;
	}
	
	/*
	 * Returns the password
	 */
	public String getPass(){
		return pass;
	}
	
	public String toString() {
		return "Username: " + user + " / Password: " + pass;
	}
}

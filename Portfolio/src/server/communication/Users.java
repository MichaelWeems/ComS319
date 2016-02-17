package server.communication;

import java.util.HashMap;

/*
 * "Database" holding the login information for the application.
 */
public class Users {
	
	private HashMap<String,String> users;	// holds the <Username,Password> pairs
	
	/*
	 * Construct a new "Database"
	 */
	public Users(){
		users = new HashMap<String,String>();
		users.put("Zach", "wild");
		users.put("Mike", "weems");
		users.put("a", "a");
	}
	
	/*
	 * return the database
	 */
	public HashMap<String,String> getUsers(){
		return users;
	}
	
	/*
	 * Get the password for the given user
	 */
	public String get(String user) {
		return users.get(user);
	}
	
}

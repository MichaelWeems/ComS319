package server.communication;

import java.util.HashMap;

public class Users {
	
	private HashMap<String,String> users;
	
	public Users(){
		users = new HashMap<String,String>();
		users.put("Zach", "wild");
		users.put("Mike", "weems");
		users.put("a", "a");
	}
	
	public HashMap<String,String> getUsers(){
		return users;
	}
	
}

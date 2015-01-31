package connection;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import usersManagement.User;

public class KeyDistributionClient extends ClientConnection {
	private static final String GETPKEY = "GET";
	private static final String GETLEVEL = "GETLEVEL";
	private static final String GETUSERSBYLEVEL = "GETUSERSBYLEVEL";
	private static final String GETALLUSERS = "GETALLUSERS";
	
	public String getUserPublicKey(String userId) throws IOException {
		return super.sendCommand(GETPKEY + " " + userId);
	}
	
	public String getLevelKey(int level) throws IOException {
		return super.sendCommand(GETLEVEL + " " + level);
	}
	
	public List<User> getUsersByLevel(int level)  throws IOException {
		String usrList = super.sendCommand(GETUSERSBYLEVEL + " " + level);
		List<User> users= new LinkedList<User>();
		//TODO: from JSON to List di USERS
		
		return users;
	}

	public List<User> getAllUsers()  throws IOException {
		String usrList = super.sendCommand(GETALLUSERS);
		List<User> users= new LinkedList<User>();
		//TODO: from JSON to List di USERS
		
		return users;
	}
}

package connection;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import usersManagement.User;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
	
	private List<User> parseJson(String json) {
		List<User> users= new LinkedList<User>();
		JsonParser parser = new JsonParser();
		JsonObject object = parser.parse(json).getAsJsonObject();
		
		if(object.has("type"))
			return users;
		
		JsonArray usrArray = object.getAsJsonArray("users");
		
		for(JsonElement obj : usrArray) {
			User u = new User();
			u.setName(obj.getAsJsonObject().get("name").getAsString());
			u.setSurname(obj.getAsJsonObject().get("surname").getAsString());
			u.setPublicKey(obj.getAsJsonObject().get("pkey").getAsString());
			u.setTrustLevel(obj.getAsJsonObject().get("trustLevel").getAsInt());
			users.add(new User());
		}
		return users;
	}
	
	public List<User> getUsersByLevel(int level)  throws IOException {
		String usrList = super.sendCommand(GETUSERSBYLEVEL + " " + level);
		
		return parseJson(usrList);
	}

	public List<User> getAllUsers()  throws IOException {
		String usrList = super.sendCommand(GETALLUSERS);

		return parseJson(usrList);
	}
}

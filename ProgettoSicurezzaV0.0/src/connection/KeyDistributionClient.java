package connection;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import usersManagement.User;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import exceptions.ClientUnAuthorizedException;
import exceptions.ServerGenericErrorException;

public class KeyDistributionClient extends ClientConnection {
	private static final String GETPKEY = "GET";
	private static final String GETLEVEL = "GETLEVEL";
	private static final String GETUSERSBYLEVEL = "GETUSERSBYLEVEL";
	private static final String GETALLUSERS = "GETALLUSERS";
	
	public String getUserPublicKey(String userId) throws IOException, ServerGenericErrorException {
		String response = super.sendCommand(GETPKEY + " " + userId);
		if(response.startsWith("ERROR:")) {
			throw new ServerGenericErrorException(response.replace("ERROR:", ""));//errore
		}
		return response;
	}
	
	public String getLevelKey(int level) throws IOException, ClientUnAuthorizedException, ServerGenericErrorException {
		String response = super.sendCommand(GETLEVEL + " " + level);
		if(response.startsWith("ERROR:")) {
			response = response.replace("ERROR:", "");
			if(response.startsWith("403"))
				throw new ClientUnAuthorizedException();
			else
				throw new ServerGenericErrorException(response);
		}
		return response;
	}
	
	private List<User> parseJson(String json) throws ServerGenericErrorException {
		List<User> users= new LinkedList<User>();
		JsonParser parser = new JsonParser();
		JsonObject object = parser.parse(json).getAsJsonObject();
		
		//errore
		if(object.has("type")) {
			throw new ServerGenericErrorException(object.get("description").getAsString());
		}
		
		JsonArray usrArray = object.getAsJsonArray("users");
		
		for(JsonElement obj : usrArray) {
			User u = new User();
			u.setName(obj.getAsJsonObject().get("name").getAsString());
			u.setSurname(obj.getAsJsonObject().get("surname").getAsString());
			u.setPublicKey(obj.getAsJsonObject().get("pkey").getAsString());
			u.setTrustLevel(obj.getAsJsonObject().get("trustLevel").getAsInt());
			users.add(u);
		}
		return users;
	}
	
	public List<User> getUsersByLevel(int level)  throws IOException, ServerGenericErrorException {
		String usrList = super.sendCommand(GETUSERSBYLEVEL + " " + level);
		return parseJson(usrList);
	}

	public List<User> getAllUsers()  throws IOException, ServerGenericErrorException {
		String usrList = super.sendCommand(GETALLUSERS);
		return parseJson(usrList);
	}
}

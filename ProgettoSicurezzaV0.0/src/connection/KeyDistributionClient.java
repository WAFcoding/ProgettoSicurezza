package connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import usersManagement.User;
import util.CryptoUtility;

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
	private static final String GETALLLEVELSKEY = "GETALLLEVEL";
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
		return new String(CryptoUtility.fromBase64(response));
	}
	
	public Map<Integer, String> getAllAuthorizedLevelKey() throws Exception {
		String response = super.sendCommand(GETALLLEVELSKEY);
		
		System.out.println(response);
		
		JsonParser parser = new JsonParser();
		JsonObject object = parser.parse(response).getAsJsonObject();
		
		//errore
		if(object.has("type")) {
			throw new ServerGenericErrorException(object.get("description").getAsString());
		}
		
		Map<Integer, String> levelKeys = new HashMap<Integer, String>();
		JsonArray keysArray = object.getAsJsonArray("keys");
		
		for(JsonElement obj : keysArray) {
			JsonObject element = obj.getAsJsonObject();
			
			levelKeys.put(Integer.valueOf(element.get("level").getAsInt()), new String(CryptoUtility.fromBase64(element.get("key").getAsString())));
		}
		return levelKeys;
		
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

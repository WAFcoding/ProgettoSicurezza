package connection;

import java.io.IOException;

import usersManagement.User;
import util.CryptoUtility;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import entities.RegistrationBean;

public class RegistrationClient extends ClientConnection {
	public String submitRegistration(User u) throws IOException {
		JsonObject usr = new JsonObject();
		usr.addProperty("type", "SUBMIT");
		usr.addProperty("name", u.getName());
		usr.addProperty("surname", u.getSurname());
		usr.addProperty("country", u.getCountry());
		usr.addProperty("country_code", u.getCountry_code());
		usr.addProperty("city", u.getCity());
		usr.addProperty("organization", u.getOrganization());
		usr.addProperty("public_key", u.getPublicKey());
		
		System.out.println("waiting-->:"+usr.toString());
		String response = super.sendCommand(usr.toString());
		System.out.println(response);
		JsonParser parser = new JsonParser();
		JsonObject object = parser.parse(response).getAsJsonObject();
		
		String type=object.get("type").getAsString();
		if(type.compareTo("submit-ok")==0)
			return object.get("id").getAsString(); //sec identifier
		return "";
	}
	
	public RegistrationBean retrieveRegistrationDetails(String secId) throws IOException{
		RegistrationBean bean = new RegistrationBean();
		
		JsonObject req = new JsonObject();
		req.addProperty("type", "RETRIEVE");
		req.addProperty("id", secId);
		
		System.out.println("waiting-->:"+req.toString());
		String response = super.sendCommand(req.toString());
		JsonParser parser = new JsonParser();
		JsonObject object = parser.parse(response).getAsJsonObject();
		
		if(object.has("type"))
			return bean;
		
		bean.setTrustLevel(object.get("trustLevel").getAsInt());
		bean.setCertificateData(CryptoUtility.fromBase64(object.get("cert").getAsString()));

		return bean;
	}
}

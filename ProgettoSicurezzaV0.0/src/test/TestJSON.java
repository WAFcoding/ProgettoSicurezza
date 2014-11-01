package test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class TestJSON {

	public static void main(String[] args) {

		String json = "{name:\"giovanni\", surname:\"rossi\", id:12312}";

		JsonParser parser = new JsonParser();
		JsonObject obj = parser.parse(json).getAsJsonObject();
		System.out.println(obj.get("name").getAsString() + "---" + obj.get("surname").getAsString() + "--" + (obj.get("id").getAsInt() + 2));
		
		JsonObject nobj = new JsonObject();
		nobj.addProperty("name", "pasquale");
		nobj.addProperty("surname", "verlotta");
		nobj.addProperty("anno", 2014);
		System.out.println(nobj.toString());
		
	}

}

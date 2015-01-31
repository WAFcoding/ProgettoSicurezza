package request_man;

public class ResultInvalidJson extends Result {

	private String description;
	
	public ResultInvalidJson(String description) {
		this.description = description;
	}
	
	@Override
	public String toSendFormat() {
		return "{\"type\":\"error\",\"description\":\""+this.description+"\"}";
	}

}

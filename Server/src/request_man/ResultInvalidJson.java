package request_man;

public class ResultInvalidJson extends Result {

	@Override
	public String toSendFormat() {
		return "{type:\"error\",description:\"Malformed Request\"}";
	}

}

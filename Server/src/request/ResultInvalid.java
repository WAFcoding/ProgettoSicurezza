package request;

public class ResultInvalid extends Result {

	@Override
	public String toSendFormat() {
		return "Invalid Request";
	}

}

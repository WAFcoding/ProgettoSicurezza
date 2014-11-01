package request_man;

public class ResultInvalid extends Result {

	@Override
	public String toSendFormat() {
		return "Error during request processing";
	}

}

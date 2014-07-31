package request;

public class ResultGetPublicKey extends Result {

	private String result;
	public ResultGetPublicKey(String result) {
		this.result = result;
	}
	
	@Override
	public String toSendFormat() {
		return this.result;
	}

}

package request_man;

public class ResultGetPublicKey extends Result {

	private String result;
	public ResultGetPublicKey(String result) {
		this.result = result.replace("\n", "");
	}
	
	@Override
	public String toSendFormat() {
		return this.result;
	}

}

package request_man;

public class ResultGetLevelX extends Result {

	private String result;
	public ResultGetLevelX(String result) {
		this.result = result;
	}

	@Override
	public String toSendFormat() {
		return result;
	}
}
package request_man;

public class ResultInvalid extends Result {

	private String message=null;
	
	public ResultInvalid() {
	}
	
	public ResultInvalid(String message) {
		this.message = message;
	}

	@Override
	public String toSendFormat() {
		return message==null ? "ERROR:Error during request processing" : "ERROR:"+this.message;
	}

}

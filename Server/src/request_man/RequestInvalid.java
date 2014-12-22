package request_man;

public class RequestInvalid extends Request {
	
	private String message=null;
	public RequestInvalid() {	}
	
	public RequestInvalid(String string) {
		this.message = string;
	}

	@Override
	public Result doAndGetResult() {
		if(message==null)
			return new ResultInvalid();
		else
			return new ResultInvalid(message);
	}
}

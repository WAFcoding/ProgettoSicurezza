package request_man;

public class RequestInvalidJson extends Request {

	private String description;
	
	public RequestInvalidJson(String description) {
		this.description = description;
	}
	
	@Override
	public Result doAndGetResult() {
		return new ResultInvalidJson(this.description);
	}

}

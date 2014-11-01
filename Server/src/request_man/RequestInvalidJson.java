package request_man;

public class RequestInvalidJson extends Request {

	@Override
	public Result doAndGetResult() {
		return new ResultInvalidJson();
	}

}

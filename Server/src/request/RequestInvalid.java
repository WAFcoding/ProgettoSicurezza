package request;

public class RequestInvalid extends Request {
	@Override
	public Result doAndGetResult() {
		return new ResultInvalid();
	}
}

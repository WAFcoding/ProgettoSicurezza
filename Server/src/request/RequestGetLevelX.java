package request;

public class RequestGetLevelX extends Request {

	private int level;
	private String trustedUser;
	
	public RequestGetLevelX(int level, String trustedUser) {
		this.level = level;
		this.trustedUser = trustedUser;
	}

	@Override
	public Result doAndGetResult() {
		return null;
	}

}

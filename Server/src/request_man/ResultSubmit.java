package request_man;

public class ResultSubmit extends Result {

	private String id;
	public ResultSubmit(String id) {
		this.id = id;
	}

	@Override
	public String toSendFormat() {
		return "{type:\"submit-ok\", id:"+id +"}";
	}

}

package request_man;

public class ResultSubmit extends Result {

	private int id;
	public ResultSubmit(int id) {
		this.id = id;
	}

	@Override
	public String toSendFormat() {
		return "{type:\"submit-ok\", id:"+id +"}";
	}

}

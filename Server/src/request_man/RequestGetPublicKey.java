package request_man;

import bean.User;
import db.dao.UserDAO;
import db.dao.UserDaoImpl;

public class RequestGetPublicKey extends Request {

	private String user;

	public RequestGetPublicKey(String user) {
		this.user = user;
	}

	@Override
	public Result doAndGetResult() {
		UserDAO dao = new UserDaoImpl();
		User user = dao.findUserByUsername(this.user);

		if(user==null)
			return new ResultInvalid("404 - PK not found for " + this.user);
		
		System.out.println(user.toString());
		return new ResultGetPublicKey(user.getPublicKey());
	}

}

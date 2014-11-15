package request_man;

import bean.LevelKey;
import bean.User;
import db.dao.LevelKeyDAO;
import db.dao.LevelKeyDaoImpl;
import db.dao.UserDAO;
import db.dao.UserDaoImpl;

public class RequestGetLevelX extends Request {

	private int level;
	private String trustedUser;
	
	public RequestGetLevelX(int level, String trustedUser) {
		this.level = level;
		this.trustedUser = trustedUser;
	}

	@Override
	public Result doAndGetResult() {
		UserDAO daoU = new UserDaoImpl();
		User user = daoU.findUserByUsername(trustedUser);

		if(user==null)
			return new ResultGetLevelX("404 - User " + trustedUser + " not found");
		if(user.getTrustLevel() < level)
			return new ResultGetLevelX("403 - Unauthorized");
		

		LevelKeyDAO daoL = new LevelKeyDaoImpl();
		LevelKey lkey = daoL.findKeyByLevel(level);
		
		if(lkey==null)
			return new ResultGetLevelX("404 - Key not found for level " + this.level);
		
		try {
			return new ResultGetLevelX(lkey.getClearKey());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultGetLevelX("500 - Security Exception");
		}
	}

}

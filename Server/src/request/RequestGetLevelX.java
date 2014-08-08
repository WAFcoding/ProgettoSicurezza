package request;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import bean.LevelKey;
import bean.User;
import db.DbHibernateUtils;

public class RequestGetLevelX extends Request {

	private int level;
	private String trustedUser;
	
	public RequestGetLevelX(int level, String trustedUser) {
		this.level = level;
		this.trustedUser = trustedUser;
	}

	@Override
	public Result doAndGetResult() {
		Session sessionUser = DbHibernateUtils.getTrustedUserDbSession();
		Transaction txUser = sessionUser.beginTransaction();
		
		String queryStringUser = "from User where username = :username";
		Query queryUser = sessionUser.createQuery(queryStringUser);
		queryUser.setString("username", trustedUser);
		
		Object queryResultUser = queryUser.uniqueResult();
		User user = (User) queryResultUser;
		txUser.commit();		

		if(user==null)
			return new ResultGetPublicKey("404 - User " + trustedUser + " not found");
		if(user.getTrustLevel() < level)
			return new ResultGetPublicKey("403 - Unauthorized");
		
		Session sessionKey = DbHibernateUtils.getKeyLevelDbSession();
		Transaction txKey = sessionKey.beginTransaction();
		
		String queryStringKey = "from LevelKey where level = :level";
		Query queryKey = sessionKey.createQuery(queryStringKey);
		queryKey.setInteger("level", this.level);

		
		Object queryResultKey = queryKey.uniqueResult();
		LevelKey lkey = (LevelKey) queryResultKey;
		txKey.commit();		
		
		if(lkey==null)
			return new ResultGetPublicKey("404 - Key not found for level " + this.level);
		
		try {
			return new ResultGetPublicKey(lkey.getClearKey());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultGetPublicKey("500 - Security Exception");
		}
	}

}

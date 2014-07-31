package request;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import bean.User;
import db.DbHibernateUtils;

public class RequestGetPublicKey extends Request {

	private String user;

	public RequestGetPublicKey(String user) {
		this.user = user;
	}

	@Override
	public Result doAndGetResult() {
		Session session = DbHibernateUtils.getHibernateSession();
		Transaction tx = session.beginTransaction();
		
		String queryString = "from User where username = :username";
		Query query = session.createQuery(queryString);
		query.setString("username", this.user);
		
		Object queryResult = query.uniqueResult();
		User user = (User) queryResult;
		tx.commit();		

		System.out.println(user.toString());
		return new ResultGetPublicKey(user.getPublicKey());
	}

}

package db;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import bean.User;

public class UserDaoImpl implements UserDAO{

	public UserDaoImpl() {
	}
	
	protected Session getSession() {
		return DbHibernateUtils.getTrustedUserDbSession();
	}

	@Override
	public void saveUser(User u) {
		Session s = getSession();
		Transaction tx = s.beginTransaction();
		s.save(u);
		tx.commit();
	}

	@Override
	public void updateUser(User u) {
		Session s = getSession();
		Transaction tx = s.beginTransaction();
		s.update(u);
		tx.commit();		
	}

	@Override
	public void deleteUser(User u) {
		Session s = getSession();
		Transaction tx = s.beginTransaction();
		s.delete(u);
		tx.commit();		
	}

	@Override
	public User findUserByUsername(String username) {
		Session s = getSession();
		Transaction tx = s.beginTransaction();

		String queryString = "from User where username = :username";
		Query query = s.createQuery(queryString);
		query.setString("username", username);
		
		Object queryResult = query.uniqueResult();
		User user = (User) queryResult;	
		tx.commit();
		return user;
	}

	@Override
	public List<User> findUserByTrustLevel(int l) {
		Session s = getSession();
		Transaction tx = s.beginTransaction();

		String queryString = "from User where trustLevel = :level";
		Query query = s.createQuery(queryString);
		query.setInteger("level", l);
		
		List<?> queryResult = query.list();
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) queryResult;	
		tx.commit();
		return users;
	}

	@Override
	public List<User> findUserByMinTrustLevel(int l) {
		Session s = getSession();
		Transaction tx = s.beginTransaction();

		String queryString = "from User where trustLevel >= :level";
		Query query = s.createQuery(queryString);
		query.setInteger("level", l);
		
		List<?> queryResult = query.list();
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) queryResult;	
		tx.commit();
		return users;
	}

}

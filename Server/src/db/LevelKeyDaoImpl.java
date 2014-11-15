package db;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import bean.LevelKey;

public class LevelKeyDaoImpl implements LevelKeyDAO {

	public LevelKeyDaoImpl() {
	}
	
	protected Session getSession() {
		return DbHibernateUtils.getKeyLevelDbSession();
	}

	@Override
	public void saveKey(LevelKey lkey) {
		Session s = getSession();
		Transaction tx = s.beginTransaction();
		s.save(lkey);
		tx.commit();
	}

	@Override
	public void updateKey(LevelKey lkey) {
		Session s = getSession();
		Transaction tx = s.beginTransaction();
		s.update(lkey);
		tx.commit();
	}

	@Override
	public void deleteKey(LevelKey lkey) {
		Session s = getSession();
		Transaction tx = s.beginTransaction();
		s.delete(lkey);
		tx.commit();
	}

	@Override
	public LevelKey findKeyByLevel(int level) {
		Session s = getSession();
		Transaction tx = s.beginTransaction();

		String queryString = "from LevelKey where level = :level";
		Query query = s.createQuery(queryString);
		query.setInteger("level", level);
		
		Object queryResult = query.uniqueResult();
		LevelKey lkey = (LevelKey) queryResult;	
		tx.commit();
		return lkey;
	}

}

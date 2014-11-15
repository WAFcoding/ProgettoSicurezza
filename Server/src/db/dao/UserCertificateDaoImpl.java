package db.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import db.DbHibernateUtils;
import util.CryptoUtility;
import util.CryptoUtility.HASH_ALGO;
import bean.UserCertificateBean;

public class UserCertificateDaoImpl implements UserCertificateDAO {

	public UserCertificateDaoImpl() {
	}
	
	protected Session getSession() {
		return DbHibernateUtils.getTrustedUserDbSession();
	}

	@Override
	public String saveUserCertificate(UserCertificateBean bean) {
		Session session = DbHibernateUtils.getTrustedUserDbSession();

		Transaction tx = session.beginTransaction();
		bean.setSecIdentifier("temp");
		session.save(bean);
		
		String secid;
		try {
			secid=CryptoUtility.hash(HASH_ALGO.MD5, bean.getId()+System.currentTimeMillis()+"");
			String secid_hash=CryptoUtility.hash(HASH_ALGO.SHA1, secid);
			bean.setSecIdentifier(secid_hash);
		} catch (Exception e) {
			System.out.println("Error in UserCertificateDAO - save");
			e.printStackTrace();
			tx.rollback();
			session.close();
			return null;
		}
		
		session.saveOrUpdate(bean);
		tx.commit();
		return secid;
	}

	@Override
	public void updateUserCertificate(UserCertificateBean bean) {
		Session s = getSession();
		Transaction tx = s.beginTransaction();
		s.update(bean);
		tx.commit();
	}

	@Override
	public void deleteUserCertificate(UserCertificateBean bean) {
		Session s = getSession();
		Transaction tx = s.beginTransaction();
		s.delete(bean);
		tx.commit();	
	}

	@Override
	public UserCertificateBean findBySecureId(String secId) {
		Session s = getSession();
		Transaction tx = s.beginTransaction();

		String queryString = "from UserCertificateBean where secIdentifier = :sec";
		Query query = s.createQuery(queryString);
		try {
			query.setString("sec", CryptoUtility.hash(HASH_ALGO.SHA1, secId));
		} catch (Exception e) {
			System.err.println("Error in UserCertificateDAO - find");
			e.printStackTrace();
			tx.rollback();
			s.close();
		}
		
		Object queryResult = query.uniqueResult();
		UserCertificateBean user = (UserCertificateBean) queryResult;	
		tx.commit();
		return user;
	}

	@Override
	public List<UserCertificateBean> findByStatus(int status) {
		Session s = getSession();
		Transaction tx = s.beginTransaction();

		String queryString = "from UserCertificateBean where status = :status";
		Query query = s.createQuery(queryString);
		query.setInteger("status", status);
		
		List<?> queryResult = query.list();
		@SuppressWarnings("unchecked")
		List<UserCertificateBean> beans = (List<UserCertificateBean>) queryResult;	
		tx.commit();
		return beans;
	}

}

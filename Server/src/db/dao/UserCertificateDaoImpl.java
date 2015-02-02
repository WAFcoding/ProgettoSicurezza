package db.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import request_man.RequestStatus;
import util.CryptoUtility;
import util.CryptoUtility.HASH_ALGO;
import bean.User;
import bean.UserCertificateBean;
import db.DbHibernateUtils;

public class UserCertificateDaoImpl implements UserCertificateDAO {

	public UserCertificateDaoImpl() {
	}
	
	protected Session getSession() {
		return DbHibernateUtils.getTrustedUserDbSession();
	}

	@Override
	public String saveUserCertificate(UserCertificateBean bean) {
		if(bean==null)
			return null;
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
		if(bean==null)
			return;
		Session s = getSession();
		Transaction tx = s.beginTransaction();
		s.update(bean);
		tx.commit();
	}

	@Override
	public void deleteUserCertificate(UserCertificateBean bean) {
		if(bean==null)
			return;
		Session s = getSession();
		Transaction tx = s.beginTransaction();
		s.delete(bean);
		tx.commit();	
	}

	@Override
	public UserCertificateBean findBySecureId(String secId) {
		if(secId==null)
			return null;
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
		
		if(user==null)
			return null;
		
		UserDAO udao = new UserDaoImpl();
		User u = udao.findUserByUsername(user.getUID());
		if (u != null)
			user.setTrustLevel(u.getTrustLevel());
		
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
		
		if (status == RequestStatus.ACCEPTED) {
			UserDAO udao = new UserDaoImpl();
			for (UserCertificateBean b : beans) {
				User u = udao.findUserByUsername(b.getUID());
				if (u != null)
					b.setTrustLevel(u.getTrustLevel());
			}
		}
		return beans;
	}

}

package request_man;

import org.hibernate.Session;
import org.hibernate.Transaction;

import util.CryptoUtility;
import util.CryptoUtility.HASH_ALGO;
import bean.UserCertificateBean;
import db.DbHibernateUtils;

public class RequestSubmit extends Request {

	private UserCertificateBean bean;

	public RequestSubmit(UserCertificateBean bean) {
		this.bean = bean;
	}

	@Override
	public Result doAndGetResult() {
		Session session = DbHibernateUtils.getTrustedUserDbSession();

		Transaction tx = session.beginTransaction();
		session.save(bean);
		
		try {
			bean.setSecIdentifier(CryptoUtility.hash(HASH_ALGO.MD5, bean.getId()+System.currentTimeMillis()+""));
		} catch (Exception e) {
			tx.rollback();
			session.close();
			return new ResultInvalidJson();
		}
		
		session.saveOrUpdate(bean);
		tx.commit();
		session.close();

		return new ResultSubmit(bean.getId());
	}

}

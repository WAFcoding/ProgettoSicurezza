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
		bean.setSecIdentifier("temp");
		session.save(bean);
		
		String secid;
		try {
			secid=CryptoUtility.hash(HASH_ALGO.MD5, bean.getId()+System.currentTimeMillis()+"");
			String secid_hash=CryptoUtility.hash(HASH_ALGO.SHA1, secid);
			bean.setSecIdentifier(secid_hash);
		} catch (Exception e) {
			tx.rollback();
			session.close();
			return new ResultInvalidJson("Aborted transaction");
		}
		
		session.saveOrUpdate(bean);
		tx.commit();

		return new ResultSubmit(secid);
	}

}

package usermanagement.controller;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import request_man.RequestStatus;
import bean.UserCertificateBean;
import db.DbHibernateUtils;

public class RequestController {

	public RequestController() {
		
	}
	
	public static String[][] retrieveRequests() {
		
		Session session = DbHibernateUtils.getTrustedUserDbSession();
		Transaction tx = session.beginTransaction();
		
		String queryString = "from UserCertificateBean where status = :status";
		Query query = session.createQuery(queryString);
		query.setInteger("status", RequestStatus.PENDING);
		
		@SuppressWarnings("unchecked")
		List<UserCertificateBean> queryResult = query.list();
		tx.commit();		
		
		String[][] dataMatrix = new String[queryResult.size()][5];
		int i=0;
		for(UserCertificateBean b : queryResult) {
			dataMatrix[i] = new String[] {b.getName(), b.getSurname(), b.getCountry(), b.getCountryCode(), b.getOrganization()};
			i++;
		}
		
		return dataMatrix;
	}

}

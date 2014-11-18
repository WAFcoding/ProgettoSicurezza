package usermanagement.controller;

import java.util.List;

import request_man.RequestStatus;
import bean.UserCertificateBean;
import db.dao.UserCertificateDAO;
import db.dao.UserCertificateDaoImpl;

public class AdminController {

	public AdminController() {
	}
	
	protected static String[][] retrieveData(int status) {
		
		UserCertificateDAO dao = new UserCertificateDaoImpl();
		
		List<UserCertificateBean> queryResult = dao.findByStatus(status);		
		
		String[][] dataMatrix = new String[queryResult.size()][5];
		int i=0;
		for(UserCertificateBean b : queryResult) {
			dataMatrix[i] = new String[] {b.getName(), b.getSurname(), b.getCountry(), b.getCountryCode(), b.getOrganization()};
			i++;
		}
		
		return dataMatrix;
	}
	
	public static String[][] retrieveRequests() {
		return retrieveData(RequestStatus.PENDING);
	}

	public static String[][] retrieveAccepted() {
		return retrieveData(RequestStatus.ACCEPTED);
	}

	public static String[][] retrieveBlocked() {
		return retrieveData(RequestStatus.REJECTED);
	}
	
	public static void acceptUser(UserCertificateBean user, int trustLevel) {
		
	}
	
	public static void blockUser(UserCertificateBean user, int trustLevel) {
		
	}

}

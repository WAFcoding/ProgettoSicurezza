package usermanagement.controller;

import java.util.List;

import request_man.RequestStatus;
import bean.UserCertificateBean;
import db.dao.UserCertificateDAO;
import db.dao.UserCertificateDaoImpl;

public class RequestController {

	public RequestController() {
	}
	
	public static String[][] retrieveRequests() {
		
		UserCertificateDAO dao = new UserCertificateDaoImpl();
		
		List<UserCertificateBean> queryResult = dao.findByStatus(RequestStatus.PENDING);		
		
		String[][] dataMatrix = new String[queryResult.size()][5];
		int i=0;
		for(UserCertificateBean b : queryResult) {
			dataMatrix[i] = new String[] {b.getName(), b.getSurname(), b.getCountry(), b.getCountryCode(), b.getOrganization()};
			i++;
		}
		
		return dataMatrix;
	}

}

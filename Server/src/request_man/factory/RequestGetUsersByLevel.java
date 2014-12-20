package request_man.factory;

import java.util.LinkedList;
import java.util.List;

import request_man.Request;
import request_man.RequestStatus;
import request_man.Result;
import bean.UserCertificateBean;
import db.dao.UserCertificateDAO;
import db.dao.UserCertificateDaoImpl;

public class RequestGetUsersByLevel extends Request {

	private int level;
	public RequestGetUsersByLevel(int value) {
		this.level = value;
	}

	@Override
	public Result doAndGetResult() {
		UserCertificateDAO dao = new UserCertificateDaoImpl();
		
		List<UserCertificateBean> users = dao.findByStatus(RequestStatus.ACCEPTED);
		List<UserCertificateBean> filtered = new LinkedList<UserCertificateBean>();
		
		for(UserCertificateBean u : users) {
			if(u.getTrustLevel() == this.level)
				filtered.add(u);
		}
		
		return new ResultListUser(filtered);
	}

}

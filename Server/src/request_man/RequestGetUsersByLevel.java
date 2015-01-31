package request_man;

import java.util.LinkedList;
import java.util.List;

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

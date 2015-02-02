package request_man;

import java.util.List;

import bean.UserCertificateBean;
import db.dao.UserCertificateDAO;
import db.dao.UserCertificateDaoImpl;

public class RequestGetAllUsers extends Request {

	@Override
	public Result doAndGetResult() {
		UserCertificateDAO dao = new UserCertificateDaoImpl();
		
		List<UserCertificateBean> users = dao.findByStatus(RequestStatus.ACCEPTED);
		return new ResultListUser(users);
	}

}

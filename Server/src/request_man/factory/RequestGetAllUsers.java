package request_man.factory;

import java.util.List;

import request_man.Request;
import request_man.RequestStatus;
import request_man.Result;
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

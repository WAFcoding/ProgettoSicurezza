package request_man;

import bean.UserCertificateBean;
import db.UserCertificateDAO;
import db.UserCertificateDaoImpl;

public class RequestSubmit extends Request {

	private UserCertificateBean bean;

	public RequestSubmit(UserCertificateBean bean) {
		this.bean = bean;
	}

	@Override
	public Result doAndGetResult() {
		UserCertificateDAO dao = new UserCertificateDaoImpl();
		String secid = dao.saveUserCertificate(bean);

		if(secid==null)
			return new ResultInvalidJson("Error generating secure identifier");
		
		return new ResultSubmit(secid);
	}

}

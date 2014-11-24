package request_man;

import bean.UserCertificateBean;
import db.dao.UserCertificateDAO;
import db.dao.UserCertificateDaoImpl;

public class RequestRetrieve extends Request {

	private String secId;
	
	public RequestRetrieve(String id) {
		this.secId = id;
	}

	@Override
	public Result doAndGetResult() {
		
		UserCertificateDAO udao = new UserCertificateDaoImpl();
		UserCertificateBean user = udao.findBySecureId(secId);
		
		if(user==null)
			return new ResultInvalidJson("User not found");
		
		if(user.getStatus()==RequestStatus.PENDING)
			return new ResultInvalidJson("Just Working...");
		
		if(user.getStatus() == RequestStatus.REJECTED)
			return new ResultInvalidJson("Unauthorized");
		
		return new CertificateReadyResult(user);
	}

}

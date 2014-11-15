package db;

import java.util.List;

import bean.UserCertificateBean;

public interface UserCertificateDAO {
	
	public String saveUserCertificate(UserCertificateBean bean);
	public void updateUserCertificate(UserCertificateBean bean);
	public void deleteUserCertificate(UserCertificateBean bean);
	public UserCertificateBean findBySecureId(String secId);
	public List<UserCertificateBean> findByStatus(int status);	
}

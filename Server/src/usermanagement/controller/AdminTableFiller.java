package usermanagement.controller;

import java.util.List;

import request_man.RequestStatus;
import db.dao.UserCertificateDAO;
import db.dao.UserCertificateDaoImpl;
import bean.UserCertificateBean;

public class AdminTableFiller {

	private String[][] data;
	private List<UserCertificateBean> list;
	private int fillingType;
	
	public static final String[] fields = {"Name", "Surname", "Country", "Country Code", "Organization"};

	public AdminTableFiller(int fillingType) {
		this.fillingType = fillingType;
		retrieveData(fillingType);
	}

	protected void fillMatrix(List<UserCertificateBean> list) {
		data = new String[list.size()][5];
		int i = 0;
		for (UserCertificateBean b : list) {
			data[i] = new String[] { b.getName(), b.getSurname(), b.getCountry(), b.getCountryCode(), b.getOrganization() };
			i++;
		}
	}
	
	protected void retrieveData(int status) {

		UserCertificateDAO dao = new UserCertificateDaoImpl();

		list = dao.findByStatus(status);
		
		fillMatrix(list);
	}

	public String[][] getData() {
		return data;
	}

	public List<UserCertificateBean> getList() {
		return list;
	}

	public int getFillingType() {
		return fillingType;
	}
	
	public void update(int fillingType) {
		this.fillingType = fillingType;
		retrieveData(fillingType);
	}
	
	public void update() {
		retrieveData(this.fillingType);
	}
	
	public void add(UserCertificateBean bean) {
		list.add(bean);
		fillMatrix(list);
	}
	
	public void remove(int index) {
		list.remove(index);
		fillMatrix(list);
	}

}

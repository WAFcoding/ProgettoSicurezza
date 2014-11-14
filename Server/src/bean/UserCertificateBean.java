package bean;

import java.io.Serializable;

public class UserCertificateBean implements Serializable{

	private static final long serialVersionUID = 6596891953434367473L;
	private String name;
	private String surname;
	private String country;
	private String countryCode;
	private String city;
	private String organization;
	private String secIdentifier;
	private String publicKey;
	private int status;
	
	private int id;
	
	public UserCertificateBean() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSecIdentifier() {
		return secIdentifier;
	}

	public void setSecIdentifier(String secIdentifier) {
		this.secIdentifier = secIdentifier;
	}

	public void setStatus(int i) {
		this.status = i;		
	}

	public int getStatus() {
		return status;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	@Override
	public String toString() {
		return "[name=" + name + ", surname=" + surname
				+ ", country=" + country + ", countryCode=" + countryCode
				+ ", city=" + city + ", organization=" + organization + "]";
	}
	
	
}

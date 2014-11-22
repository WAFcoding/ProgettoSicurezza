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
	private int trustLevel;
	private int status;
	
	private int id;
	
	public UserCertificateBean() {
		this.trustLevel = -1;
	}

	public String getUID() {
		return getName() + "_" + getId();
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

	public int getTrustLevel() {
		return trustLevel;
	}

	public void setTrustLevel(int trustLevel) {
		this.trustLevel = trustLevel;
	}

	@Override
	public String toString() {
		return "[name=" + name + ", surname=" + surname
				+ ", country=" + country + ", countryCode=" + countryCode
				+ ", city=" + city + ", organization=" + organization + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 37;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result
				+ ((countryCode == null) ? 0 : countryCode.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((organization == null) ? 0 : organization.hashCode());
		result = prime * result
				+ ((publicKey == null) ? 0 : publicKey.hashCode());
		result = prime * result
				+ ((secIdentifier == null) ? 0 : secIdentifier.hashCode());
		result = prime * result + status;
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserCertificateBean other = (UserCertificateBean) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (countryCode == null) {
			if (other.countryCode != null)
				return false;
		} else if (!countryCode.equals(other.countryCode))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (organization == null) {
			if (other.organization != null)
				return false;
		} else if (!organization.equals(other.organization))
			return false;
		if (publicKey == null) {
			if (other.publicKey != null)
				return false;
		} else if (!publicKey.equals(other.publicKey))
			return false;
		if (secIdentifier == null) {
			if (other.secIdentifier != null)
				return false;
		} else if (!secIdentifier.equals(other.secIdentifier))
			return false;
		if (status != other.status)
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		return true;
	}
	
	
	
	
}

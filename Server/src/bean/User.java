package bean;

import java.io.Serializable;

import util.CryptoUtility;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1687530967441079737L;
	private String publicKey;
	private String username;
	private int trustLevel;
	
	public User() {
	}
	
	public User(String username, byte[] publicKey, int trustLevel) {
		this.username = username;
		this.publicKey = CryptoUtility.toBase64(publicKey);
		this.trustLevel = trustLevel;
	}

	public String getPublicKey() {
		return this.publicKey;
	}
	
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getTrustLevel() {
		return trustLevel;
	}

	public void setTrustLevel(int trustLevel) {
		this.trustLevel = trustLevel;
	}

	@Override
	public String toString() {
		return "User [publicKey=" + publicKey + ", username=" + username
				+ ", trustLevel=" + trustLevel + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + trustLevel;
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (trustLevel != other.trustLevel)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}

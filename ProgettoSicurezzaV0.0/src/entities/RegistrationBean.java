package entities;

public class RegistrationBean {
	private int trustLevel;
	private byte[] certificateData;
	public int getTrustLevel() {
		return trustLevel;
	}
	public void setTrustLevel(int trustLevel) {
		this.trustLevel = trustLevel;
	}
	public byte[] getCertificateData() {
		return certificateData;
	}
	public void setCertificateData(byte[] certificateData) {
		this.certificateData = certificateData;
	}
	
	
}

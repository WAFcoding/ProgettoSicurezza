package util;

import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.StringTokenizer;

public class CertData {
	
	private String issuerDN;
	private String subjectDN;
	private String signatureAlgo;
	
	public enum TYPE {
		Name, SURNAME, C, O, L, ST, E, UID
	};
	
	/**
	 * Riempie i campi di questo oggetto con i dati del certificato.
	 * 
	 * @param cert	Il certificato da cui estrarre i dettagli.
	 * 
	 * @throws IllegalArgumentException Se il certificato passato è null.
	 */
	public CertData(X509Certificate cert) throws IllegalArgumentException{
		if(cert==null)
			throw new IllegalArgumentException("Null certificate passed");
		
		this.issuerDN = cert.getIssuerDN().getName();
		this.subjectDN = cert.getSubjectDN().getName();
		this.signatureAlgo = cert.getSigAlgName();		
	}
	
	public String getIssuerDN() {
		return issuerDN;
	}

	public String getSubjectDN() {
		return subjectDN;
	}

	public String getSignatureAlgo() {
		return signatureAlgo;
	}
	
	private HashMap<String, String> getParams(String str) {
		if(str==null)
			return new HashMap<String,String>();
		
		StringTokenizer tok = new StringTokenizer(str, ",");
		HashMap<String, String> params = new HashMap<String, String>();
		while(tok.hasMoreTokens()) {
			String prm = tok.nextToken();
			String[] prm_array = prm.split("=");
			params.put(prm_array[0], prm_array[1]);
		}
		
		return params;
	}
	
	public HashMap<String, String> getIssuerParams(){
		return getParams(this.issuerDN);
	}
	
	public HashMap<String, String> getSubjectParams(){
		return getParams(this.subjectDN);
	}
	
	public String getIssuerParameter(TYPE t) {
		return getIssuerParams().get(t.toString());
	}
	
	public String getSubjectParameter(TYPE t) {
		return getIssuerParams().get(t.toString());
	}
	
	public static String getParameter(TYPE t, HashMap<String, String> params) {
		if(params==null)
			return null;
		return params.get(t.toString());
	}
	

}

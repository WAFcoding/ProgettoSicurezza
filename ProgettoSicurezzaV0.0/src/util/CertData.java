package util;

import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class CertData {
	
	private String issuerDN;
	private String subjectDN;
	private String signatureAlgo;
	private HashMap<String, String> dataIssuer;
	private HashMap<String, String> dataSubject;
	
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
		this.dataIssuer = getParams(this.issuerDN);
		this.dataSubject = getParams(this.subjectDN);
	}

	public CertData(javax.security.cert.X509Certificate cert) throws IllegalArgumentException {
		if(cert==null)
			throw new IllegalArgumentException("Null certificate passed");
		
		this.issuerDN = cert.getIssuerDN().getName();
		this.subjectDN = cert.getSubjectDN().getName();
		this.signatureAlgo = cert.getSigAlgName();	
		this.dataIssuer = getParams(this.issuerDN);
		this.dataSubject = getParams(this.subjectDN);
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
	
	private static HashMap<String, String> getParams(String str) {
		if(str==null)
			return new HashMap<String,String>();
		
		
		StringTokenizer tok = new StringTokenizer(str, ",");
		HashMap<String, String> params = new HashMap<String, String>();
		while(tok.hasMoreTokens()) {
			String prm = tok.nextToken();
			String[] prm_array = prm.split("=");
			//System.out.println(Arrays.toString(prm_array));
			params.put(prm_array[0].trim(), prm_array[1].trim());
		}
		
		return params;
	}
	
	public String getIssuerParameter(TYPE t) {
		return getParameter(t, dataIssuer);
	}
	
	public String getSubjectParameter(TYPE t) {
		return getParameter(t, this.dataSubject);
	}
	
	private static String getParameter(TYPE t, HashMap<String, String> params) {
		//System.out.println("entered " + t.toString());
		if(params==null || t==null)
			return null;

		if(t.compareTo(TYPE.Name)==0) 
			return params.get("OID.2.5.4.41");
		
		return params.get(t.toString());
	}
	

}

package request;

import java.util.StringTokenizer;

import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;

public abstract class RequestFactory {
	
	private static final String GETPUBLIC = "GET";
	private static final String GETLEVELX = "GETLEVEL";
	
	
	public static Request generateRequest(String request, SSLSession session) throws SSLPeerUnverifiedException {
		String trustedUser = session.getPeerPrincipal().getName();
		trustedUser = parseCN(trustedUser);
		
		StringTokenizer tok = new StringTokenizer(request, " ");
		int tokCount = tok.countTokens();
		
		if(tokCount < 2)
			return new RequestInvalid();
		if(tokCount == 2) {
			String reqType = tok.nextToken();
			String body = tok.nextToken();
			
			if(reqType.equals(GETPUBLIC))
				return new RequestGetPublicKey(body);
			if(reqType.equals(GETLEVELX))
				return new RequestGetLevelX(Integer.valueOf(body).intValue(), trustedUser);
		}
		
		return new RequestInvalid();
	}


	private static String parseCN(String trustedUser) {
		String[] infos = trustedUser.split(",");
		return infos[0].split("=")[1];
	}
}
package request;

import java.util.StringTokenizer;

import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;

/**
 * Classe che permette di parsare la richiesta in ingresso e di eseguire 
 * il lavoro giusto.
 * 
 * @author Giovanni Rossi
 */
public abstract class AuthRequestFactory {
	
	/**
	 * Definisce il comando per l'operazione di tipo GET della chiave pubblica di un utente.
	 */
	private static final String SUBMIT_DATA = "SUBMIT";
	
	/**
	 * Definisce il comando per recuperare la chiave di livello necessaria per la cifratura di un
	 * documento.
	 */
	private static final String RETRIEVE_CERT = "RETRIEVE";
	
	/**
	 * Permette di generare il giusto tipo di richiesta a seconda della stringa inviata al server.
	 * @param request	La stringa inviata al server.
	 * @param session	La sessione SSL corrente.
	 * 
	 * @return La richiesta correttamente incapsulata (se la sintassi è corretta).
	 * @throws SSLPeerUnverifiedException	Se il peer connesso non ha fornito autenticazione.
	 */
	public static Request generateRequest(String request, SSLSession session) throws SSLPeerUnverifiedException {
		//String trustedUser = session.getPeerPrincipal().getName();
		
		StringTokenizer tok = new StringTokenizer(request, " ");
		int tokCount = tok.countTokens();
		
		if(tokCount < 2)
			return new RequestInvalid();
		if(tokCount == 2) {
			String reqType = tok.nextToken();
			String body = tok.nextToken();
			/*
			if(reqType.equalsIgnoreCase(SUBMIT_DATA))
				return new RequestGetPublicKey(body);
			if(reqType.equalsIgnoreCase(RETRIEVE_CERT))
				return new RequestGetLevelX(Integer.valueOf(body).intValue(), trustedUser);
			*/
		}
		
		return new RequestInvalid();
	}
}
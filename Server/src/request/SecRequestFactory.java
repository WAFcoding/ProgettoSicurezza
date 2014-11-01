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
public abstract class SecRequestFactory {
	
	/**
	 * Definisce il comando per l'operazione di tipo GET della chiave pubblica di un utente.
	 */
	private static final String GETPUBLIC = "GET";
	
	/**
	 * Definisce il comando per recuperare la chiave di livello necessaria per la cifratura di un
	 * documento.
	 */
	private static final String GETLEVELX = "GETLEVEL";
	
	/**
	 * Permette di generare il giusto tipo di richiesta a seconda della stringa inviata al server.
	 * @param request	La stringa inviata al server.
	 * @param session	La sessione SSL corrente.
	 * 
	 * @return La richiesta correttamente incapsulata (se la sintassi è corretta).
	 * @throws SSLPeerUnverifiedException	Se il peer connesso non ha fornito autenticazione.
	 */
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
			
			if(reqType.equalsIgnoreCase(GETPUBLIC))
				return new RequestGetPublicKey(body);
			if(reqType.equalsIgnoreCase(GETLEVELX))
				return new RequestGetLevelX(Integer.valueOf(body).intValue(), trustedUser);
		}
		
		return new RequestInvalid();
	}

	/**
	 * Parsa il nome utente dalla sessione SSL corrente.
	 * @param trustedUser	La stringa rappresentante le informazioni identificative dell'utente connesso.
	 * 
	 * @return Il nome dell'utente specificato nel campo CN del certificato (usato come ID univoco).
	 */
	private static String parseCN(String trustedUser) {
		String[] infos = trustedUser.split(",");
		return infos[0].split("=")[1];
	}
}
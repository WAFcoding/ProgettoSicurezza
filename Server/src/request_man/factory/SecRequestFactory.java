package request_man.factory;

import java.util.StringTokenizer;

import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;

import request_man.Request;
import request_man.RequestGetLevelX;
import request_man.RequestGetPublicKey;
import request_man.RequestInvalid;
import util.CertData;

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
	 * Definisce il comando per recuperare una lista di utenti di un certo livello di fiducia.
	 */
	private static final String GETUSERSBYLEVEL = "GETUSERSBYLEVEL";
	
	/**
	 * Definisce il comando per recuperare l'intera lista di utenti.
	 */
	private static final String GETALLUSERS = "GETALLUSERS";
	
	/**
	 * Permette di generare il giusto tipo di richiesta a seconda della stringa inviata al server.
	 * @param request	La stringa inviata al server.
	 * @param session	La sessione SSL corrente.
	 * 
	 * @return La richiesta correttamente incapsulata (se la sintassi è corretta).
	 * @throws SSLPeerUnverifiedException	Se il peer connesso non ha fornito autenticazione.
	 */
	public static Request generateRequest(String request, SSLSession session) throws SSLPeerUnverifiedException {
		
		//autenticazione TODO:completa autenticazione
		
		try { 
			CertData data = new CertData(session.getPeerCertificateChain()[0]);
			System.out.println(data.getIssuerDN() + " " + data.getSubjectDN() + " " + data.getSignatureAlgo());
			//solo test
		
		} catch(Exception e) {
			return new RequestInvalid();
		}
		
		//vecchio sistema TODO:rimuovi
		String trustedUser = session.getPeerPrincipal().getName();
		trustedUser = parseCN(trustedUser);
		
		//<--fine autenticazione
		
		StringTokenizer tok = new StringTokenizer(request, " ");
		int tokCount = tok.countTokens();
		
		if(tokCount < 2) {
			if(request.equalsIgnoreCase(GETALLUSERS))
				return new RequestGetAllUsers();
		}
		if(tokCount == 2) {
			String reqType = tok.nextToken();
			String body = tok.nextToken();
			
			if(reqType.equalsIgnoreCase(GETPUBLIC))
				return new RequestGetPublicKey(body);
			if(reqType.equalsIgnoreCase(GETLEVELX))
				return new RequestGetLevelX(Integer.valueOf(body).intValue(), trustedUser);
			if(reqType.equalsIgnoreCase(GETUSERSBYLEVEL))
				return new RequestGetUsersByLevel(Integer.valueOf(body).intValue());
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
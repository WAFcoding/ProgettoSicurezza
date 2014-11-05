package request_man.factory;

import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;

import request_man.Request;
import request_man.RequestInvalidJson;
import request_man.RequestStatus;
import request_man.RequestSubmit;
import bean.UserCertificateBean;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Classe che permette di parsare la richiesta in ingresso e di eseguire 
 * il lavoro giusto.
 * 
 * @author Giovanni Rossi
 */
public abstract class AuthRequestFactory {
	
	/**
	 * Tipologia di richieste accettate.
	 * 
	 * @author Giovanni Rossi
	 */
	private enum TYPE {SUBMIT, RETRIEVE};
	
	//tutti i campi possibili nel JSON
	private static final String _type="type";
	private static final String _name="name";
	private static final String _surname="surname";
	private static final String _country="country";
	private static final String _countryCode="country_code";
	private static final String _city="city";
	private static final String _organization="organization";
	private static final String _id="id";
	
	/**
	 * Permette di generare il giusto tipo di richiesta a seconda della stringa inviata al server.
	 * Le stringhe in input devono soddisfare il formato JSON composto dei seguenti campi:
	 * <br>
	 * <ul>
	 * <li>
	 * TIPO "SUBMIT"<br>
	 * <code>
	 * {<br>
	 * 		type:"SUBMIT",<br>
	 * 		name:"Abc",<br>
	 * 		surname:"Bcd",<br>
	 * 		country:"Italy",<br>
	 * 		country_code:"IT",<br>
	 * 		city:"Rome",<br>
	 * 		organization:"Ve.Ro."<br>
	 * }
	 * </code>
	 * </li>
	 * <li>
	 * TIPO "RETRIEVE"<br>
	 * <code>
	 * {<br>
	 *     type:"RETRIEVE",<br>
	 *     id:012345<br>
	 * }
	 * </code>
	 * </li>
	 * </ul>
	 * @param request	La stringa inviata al server.
	 * @param session	La sessione SSL corrente.
	 * 
	 * @return La richiesta correttamente incapsulata (se la sintassi è corretta).
	 * @throws SSLPeerUnverifiedException	Se il peer connesso non ha fornito autenticazione.
	 */
	public static Request generateRequest(String request, SSLSession session) throws SSLPeerUnverifiedException {
		//parsing della richiesta in JSON

		try {
			JsonObject req = new JsonParser().parse(request).getAsJsonObject();
			String reqType = req.get(_type).getAsString();
			
			if(reqType.equalsIgnoreCase(TYPE.SUBMIT.toString())) {
				//controlla presenza altri campi
				UserCertificateBean userData = new UserCertificateBean();
				userData.setName(req.get(_name).getAsString());
				userData.setSurname(req.get(_surname).getAsString());
				userData.setCountry(req.get(_country).getAsString());
				userData.setCountryCode(req.get(_countryCode).getAsString());
				userData.setCity(req.get(_city).getAsString());
				userData.setOrganization(req.get(_organization).getAsString());
				userData.setId(-1); 	//non ancora definito (gestito da DB)
				userData.setStatus(RequestStatus.PENDING);	//<--pending
				
				return new RequestSubmit(userData);
				
			} else if(reqType.equalsIgnoreCase(TYPE.RETRIEVE.toString())) {
				//controlla presenza campo ID
				String id = req.get(_id).getAsString();
				//TODO: completa gestione recupero certificato
				
				
			} else {
				return new RequestInvalidJson("Unknown type");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new RequestInvalidJson("Malformed request");
		}
		return new RequestInvalidJson("Invalid operation");
	}
}
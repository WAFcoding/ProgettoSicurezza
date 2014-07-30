package request;

/**
 * Classe astratta per generalizzare le varie tipologie di richiesta 
 * che possono essere sottoposte al server.
 * 
 * @author Giovanni Rossi
 */
public abstract class Request {
	/**
	 * Esegue le operazioni necessarie e ritorna un risultato generico.
	 * 
	 * @return	Il risultato dell'operazione.
	 */
	public abstract Result doAndGetResult();
}

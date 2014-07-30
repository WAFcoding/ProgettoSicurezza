package request;

/**
 * Classe astratta che definisce un risultato per un operazione 
 * eseguita su un server.
 * 
 * @author Giovanni Rossi
 */
public abstract class Result {
	
	/**
	 * Converte in una forma inviabile in rete il risultato dell'operazione
	 * eseguita dal server.
	 * 
	 * @return La rappresentazione a stringa del risultato.
	 */
	public abstract String toSendFormat();
}

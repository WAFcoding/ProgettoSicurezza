package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.PublicKey;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Classe per la gestione delle chiavi pubbliche degli utenti.
 * 
 * @author Giovanni Rossi
 */
public class PublicKeyRing {

	/**
	 * La mappa che mantiene le chiavi pubbliche degli utenti come coppia <utente, chiave>
	 */
	private ConcurrentHashMap<String, PublicKey> pKeyMap;
	
	/**
	 * Istanza di questa classe.
	 */
	private static PublicKeyRing _instance = null;
	
	/**
	 * Nome del file di default del database.
	 */
	private static String PRingFileName = "publicKey.store";
	
	/**
	 * Permette di determinare se il file su disco è aggiornato.
	 */
	private AtomicBoolean updated = new AtomicBoolean(false);
	
	/**
	 * Costruttore di default privato
	 */
	private PublicKeyRing() {
		recoverPublicKeyRing();
		if(this.pKeyMap==null)
			this.pKeyMap = new ConcurrentHashMap<String, PublicKey>();
		updated.set(true);
	}

	/**
	 * Ottiene un istanza di questa classe. Se non esiste la crea e la restituisce.
	 * 
	 * @return Un istanza di questa classe.
	 */
	public synchronized static PublicKeyRing getInstance() {
		if (_instance == null)
			_instance = new PublicKeyRing();
		return _instance;
	}
	
	/**
	 * Salva una chiave pubblica per l'utente.
	 * @param user	L'utente possessore della chiave.
	 * @param pkey	La chiave pubblica.
	 */
	public void savePublicKey(String user, PublicKey pkey) {
		if(user==null || pkey == null) {
			return;
		}
		this.pKeyMap.put(user, pkey);
		this.updated.set(false);
	}
	
	/**
	 * Recupera la chiave pubblica dell'utente.
	 * @param user	L'utente possessore della chiave.
	 * 
	 * @return La chiave pubblica o null se non esiste nessuna chiave associata all'utente.
	 */
	public PublicKey getPublicKey(String user) {
		return this.pKeyMap.get(user);
	}
	
	/**
	 * Salva su file il database delle chiavi pubbliche.
	 */
	public synchronized void saveToFile() {
		if(this.updated.get())
			return;
		
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(PRingFileName));
			oos.writeObject(this.pKeyMap);
			oos.flush();
			oos.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Legge da file il database delle chiavi pubbliche.
	 */
	@SuppressWarnings("unchecked")
	private void recoverPublicKeyRing() {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(PRingFileName));
			Object obj = ois.readObject();
			this.pKeyMap = (ConcurrentHashMap<String, PublicKey>)obj;
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

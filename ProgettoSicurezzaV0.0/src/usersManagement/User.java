/**
 * 
 */
package usersManagement;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author "Pasquale Verlotta - pasquale.verlotta@gmail.com"
 *
 */
public class User {
	//chiave privata 
	//le chiavi di livello
	//le chiavi pubbliche
	
	private String privateKey;
	ArrayList<String> levelKeys;
	HashMap<String, String > publicKeys;//la chiave e' l'user, il valore e' la chiave pubblica
	
	public User(String paramPvtKey){
		//this.privateKey= paramPrivKey;
		setPrivateKey(paramPvtKey);
		this.levelKeys= new ArrayList<String>();
		this.publicKeys= new HashMap<String, String>();
	}
	/**
	 * Aggiunge una chiave all'insieme delle chiavi di livello
	 * @param paramLvlKey
	 */
	public void addLevelKey(String paramLvlKey){
		this.levelKeys.add(paramLvlKey);
	}
	/**
	 * Aggiunge o modifica una chiave di livello in base al parametro level
	 * @param paramLvlKey
	 * @param level
	 */
	public void addLevelKey(String paramLvlKey, int level){
		if(this.levelKeys.size() < level){
			addLevelKey(paramLvlKey);
		}
		else{
			this.levelKeys.set(level -1, paramLvlKey);
			//in posizione 0 si trova il livello 1, quindi se il parametro level e' pari a 4
			//la posizione da sostituire e' la 3
		}
	}
	/**
	 * Inserisce l'utente e la sua chiave pubblica, se l'utente e' presente
	 * viene sovrascritto il precedente valore
	 * @param user
	 * @param paramPblKey
	 */
	public void addPublicKey(String user, String paramPblKey){

		this.publicKeys.put(user, paramPblKey);
	}
	
	/**
	 * @return the publicKeys
	 */
	public HashMap<String, String> getPublicKeys() {
		return publicKeys;
	}
	/**
	 * @param publicKeys the publicKeys to set
	 */
	public void setPublicKeys(HashMap<String, String> publicKeys) {
		this.publicKeys = publicKeys;
	}
	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String paramPvtKey){
		if(paramPvtKey == null) paramPvtKey= "";
		this.privateKey = paramPvtKey;
	}
	/**
	 * @return the levelKeys
	 */
	public ArrayList<String> getLevelKeys() {
		return levelKeys;
	}
	/**
	 * @param levelKeys the levelKeys to set
	 */
	public void setLevelKeys(ArrayList<String> levelKeys) {
		this.levelKeys = levelKeys;
	}
}

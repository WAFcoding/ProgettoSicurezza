package bean;

import java.io.Serializable;

import servermain.ServerMasterKey;
import util.CryptoUtility;
import util.CryptoUtility.CRYPTO_ALGO;

public class LevelKey implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6612072472881322290L;
	private String key; //base64 del decriptato
	private int level;
	
	public LevelKey() {
	}

	public String getKey() throws Exception {
		byte[] fb64 = CryptoUtility.fromBase64(key);
		byte[] enc = CryptoUtility.encrypt(CRYPTO_ALGO.AES, fb64, new String(ServerMasterKey.passphrase));
		return CryptoUtility.toBase64(enc);
	}

	public void setKey(String key) throws Exception {
		byte[] fb64 = CryptoUtility.fromBase64(key);
		String dec = CryptoUtility.decrypt(CRYPTO_ALGO.AES, fb64, new String(ServerMasterKey.passphrase));
		this.key = CryptoUtility.toBase64(dec.getBytes());
	}
	
	public int getLevel() {
		return level;
	}
	
	public String getClearKey() {
		return this.key;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}

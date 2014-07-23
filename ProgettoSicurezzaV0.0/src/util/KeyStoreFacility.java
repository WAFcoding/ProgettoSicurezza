package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Facilita l'accesso alla classe <code>KeyStore</code> di Java.
 * Tutti i metodi di questa classe sono thread-safe.
 * 
 * @see {@link java.security.KeyStore}
 * @author Giovanni Rossi
 *
 */
public class KeyStoreFacility {

	/**
	 * Istanza di questa classe.
	 */
	private static KeyStoreFacility _instance = null;
	
	/**
	 * Nome del file del keystore predefinito.
	 */
	private static String keyStoreFileName = "keydb.store";
	
	/**
	 * Password del keystore.
	 * TODO: da gestire meglio.
	 */
	private static String keyStorePassword = "pasqualino";// FIXME: :-)

	private KeyStore keymap;
	private boolean changes;

	/**
	 * Costruttore privato di questa classe.
	 * 
	 * @throws KeyStoreException
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private KeyStoreFacility() throws KeyStoreException,
			NoSuchProviderException, NoSuchAlgorithmException,
			CertificateException, FileNotFoundException, IOException {
		this.keymap = KeyStore.getInstance("JCEKS");

		FileInputStream fis = null;
		try {
			fis = new FileInputStream(keyStoreFileName);
			this.keymap.load(fis, keyStorePassword.toCharArray());

		} catch (FileNotFoundException fexc) {
			this.keymap.load(null, keyStorePassword.toCharArray());
		} finally {
			if (fis != null)
				fis.close();
		}

		this.changes = false;
	}

	/**
	 * Ottiene un istanza di questa classe e se necessario ne crea una.
	 * 
	 * @return Un istanza di questa classe.
	 * 
	 * @throws Exception
	 */
	public static synchronized KeyStoreFacility getInstance() throws Exception {
		if (_instance == null)
			_instance = new KeyStoreFacility();
		return _instance;
	}

	/**
	 * Salva una chiave segreta nel keystore.
	 * @param user	L'utente a cui si riferisce la chiave.
	 * @param key	La chiave da salvare.
	 * 
	 * @throws KeyStoreException
	 */
	public synchronized void saveKey(String user, String key)
			throws KeyStoreException {
		SecretKey mySecretKey = new SecretKeySpec(key.getBytes(), "");
		KeyStore.SecretKeyEntry skEntry = new KeyStore.SecretKeyEntry(mySecretKey);
		this.keymap.setEntry(user, skEntry,  new KeyStore.PasswordProtection(keyStorePassword.toCharArray()));
		this.changes = true;
	}

	/**
	 * FIXME
	 * @param user
	 * @param privateKey
	 * @throws KeyStoreException
	 */
	public synchronized void savePrivateKey(String user, PrivateKey privateKey) throws KeyStoreException {
		KeyStore.PrivateKeyEntry pkEntry = new KeyStore.PrivateKeyEntry(privateKey, null);
		this.keymap.setEntry(user, pkEntry, new KeyStore.PasswordProtection(keyStorePassword.toCharArray()));
		this.changes = true;
	}

	/**
	 * Recupera la chiave segreta (simmetrica) dell'utente.
	 * @param user	L'utente possessore della chiave.
	 * 
	 * @return La chiave segreta (simmetrica) dell'utente.
	 * 
	 * @throws UnrecoverableKeyException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 */
	public synchronized String getKey(String user)
			throws UnrecoverableKeyException, KeyStoreException,
			NoSuchAlgorithmException {
		return new String(this.keymap.getKey(user,keyStorePassword.toCharArray()).getEncoded());
	}
	
	/**
	 * FIXME
	 * @param user
	 * @return
	 * @throws UnrecoverableKeyException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 */
	public synchronized PrivateKey getPrivateKey(String user)
			throws UnrecoverableKeyException, KeyStoreException,
			NoSuchAlgorithmException {
		return (PrivateKey) this.keymap.getKey(user, keyStorePassword.toCharArray());
	}

	/**
	 * Salva su file cifrato il keystore.
	 * 
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public synchronized void saveChanges() throws KeyStoreException,
			NoSuchAlgorithmException, CertificateException,
			FileNotFoundException, IOException {
		if (!this.changes)
			return;
		FileOutputStream fout = new FileOutputStream(keyStoreFileName);
		try {
			this.keymap.store(fout, keyStorePassword.toCharArray());
		} finally {
			fout.close();
		}
		this.changes = false;
	}

}

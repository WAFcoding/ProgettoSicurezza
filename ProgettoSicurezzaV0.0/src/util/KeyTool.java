package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

/**
 * Questa classe definisce una serie di funzioni di utilità 
 * per gestire il tool KeyTool da codice e memorizzare quindi 
 * le chiavi private e i certificati degli altri attori del sistema.
 * 
 * @author Giovanni Rossi
 *
 */
public class KeyTool {
	/**
	 * Il Provider di default per accedere al KeyStore.
	 */
	private static final String provider = "JKS";
	
	/**
	 * L'estensione del file KeyStore.
	 */
	public static final String keystore_ext = ".jks";
	
	/**
	 * Costruttore privato.
	 */
	private KeyTool() {}
	
	/**
	 * Carica il KeyStore in memoria.
	 * 
	 * @param path		Il percorso del KeyStore.
	 * @param password	La password del KeyStore.
	 * @return	Un'istanza del KeyStore esistente o uno nuovo se non esiste o <i>null</i> in caso di errore.
	 * 
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 */
	public static KeyStore loadKeystore(String path, char[] password) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		if(path == null || password == null || path.isEmpty() || password.length==0)
			return null;
		
		KeyStore ks = KeyStore.getInstance(KeyTool.provider);

		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
			ks.load(fis, password);
		} catch (FileNotFoundException fnfe) {
			ks.load(null, password); //crea nuovo keystore
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		return ks;
	}
	
	/**
	 * Guarda {@link KeyTool#loadKeystore(String, char[])}.
	 * 
	 * @param path		Il percorso del KeyStore.
	 * @param password	La password del KeyStore.
	 * @return	Un'istanza del KeyStore esistente o uno nuovo se non esiste o <i>null</i> in caso di errore.
	 * 
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 */
	public static KeyStore loadKeystore(String path, String password) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		if(path == null || password == null || path.isEmpty() || password.isEmpty())
			return null;
		return KeyTool.loadKeystore(path, password.toCharArray());
	}
	
	/**
	 * Ottiene il certificato relativo all'alias specificato.
	 * @param ks	Il KeyStore in cui cercare.
	 * @param alias	L'alias relativo al certificato.
	 * @return	Il certificato relativo all'alias o <i>null</i> in caso di errore o se non viene trovato.
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws UnrecoverableEntryException
	 * @throws KeyStoreException
	 */
	public static Certificate getCertificate(KeyStore ks,  String alias) throws NoSuchAlgorithmException, UnrecoverableEntryException, KeyStoreException {
		if(ks==null || alias==null || alias.isEmpty())
			return null;
		
		Certificate cert = ks.getCertificate(alias);
		return cert;
	}
	
	/**
	 * Aggiorna la copia su file system del KeyStore esistente oppure ne crea uno nuovo se non esiste.
	 * 
	 * @param ks		Il KeyStore da salvare/aggiornare.
	 * @param name		Il percorso del file su cui salvare il KeyStore.
	 * @param password	La password con la quale proteggere il KeyStore.
	 * 
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 */
	public static void storeKeystore(KeyStore ks, String name, char[] password) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		if(ks==null || password==null || password.length==0 || name==null || name.isEmpty())
			return;
	    FileOutputStream fos = null;
	    try {
	        fos = new FileOutputStream(name);
	        ks.store(fos, password);
	    } finally {
	        if (fos != null) {
	            fos.close();
	        }
	    }
	}
	
	/**
	 * Guarda {@link KeyTool#storeKeystore(KeyStore, String, char[])}.
	 * @param ks		Il KeyStore da salvare/aggiornare.
	 * @param name		Il percorso del file su cui salvare il KeyStore.
	 * @param password	La password con la quale proteggere il KeyStore.
	 * 
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 */
	public static void storeKeystore(KeyStore ks, String name, String password) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		if(ks==null || password==null || password.isEmpty() || name==null || name.isEmpty())
			return;
		KeyTool.storeKeystore(ks, name, password.toCharArray());
	}
	
	/**
	 * Recupera la chiave privata relativa all'alias dell'utente.
	 * 
	 * @param ks		Il KeyStore da cui recuperare la chiave privata.
	 * @param alias		L'alias dell'utente.
	 * @param password	La password con la quale è protetta la chiave private del KeyStore.
	 * @return	La chiave private relativa all'utente cercato (alias) oppure <i>null</i> in caso di errore o se non viene trovata.
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws UnrecoverableEntryException
	 * @throws KeyStoreException
	 */
	public static PrivateKey getPrivateKey(KeyStore ks, String alias, char[] password) throws NoSuchAlgorithmException, UnrecoverableEntryException, KeyStoreException {
		if (ks == null || password == null || password.length==0 || alias == null || alias.isEmpty())
			return null;
		KeyStore.ProtectionParameter protParam = new KeyStore.PasswordProtection(password);

		KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(alias, protParam);
		return pkEntry.getPrivateKey();
	}
	
	/**
	 * Guarda {@link KeyTool#getPrivateKey(KeyStore, String, char[])}.
	 * @param ks		Il KeyStore da cui recuperare la chiave privata.
	 * @param alias		L'alias dell'utente.
	 * @param password	La password con la quale è protetta la chiave private del KeyStore.
	 * @return	La chiave private relativa all'utente cercato (alias) oppure <i>null</i> in caso di errore o se non viene trovata.
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws UnrecoverableEntryException
	 * @throws KeyStoreException
	 */
	public static PrivateKey getPrivateKey(KeyStore ks, String alias, String password) throws NoSuchAlgorithmException, UnrecoverableEntryException, KeyStoreException {
		if (ks == null || password == null || password.isEmpty() || alias == null || alias.isEmpty())
			return null;
		return KeyTool.getPrivateKey(ks, alias, password.toCharArray());
	}
	
	/**
	 * Aggiunge il certificato dato al KeyStore.
	 * 
	 * @param ks	Il KeyStore in cui salvare il certificato.
	 * @param cert	Il certificato.
	 * @param alias	L'alias da associare al certificato.
	 * 
	 * @throws KeyStoreException
	 */
	public static void addCertificate(KeyStore ks, Certificate cert, String alias) throws KeyStoreException {
		if(ks==null || cert==null || alias == null || alias.isEmpty()) 
			return;
		ks.setCertificateEntry(alias, cert);
	}
	
	/**
	 * Aggiunge una chiave privata al KeyStore.
	 * 
	 * @param ks		Il KeyStore in cui salvare la chiave privata.
	 * @param pk		La chiave privata.
	 * @param alias		L'alias da associare alla chiave privata.
	 * @param password	La password con cui proteggere la chiave privata.
	 * @throws KeyStoreException
	 */
	public static void addPrivateKey(KeyStore ks, PrivateKey pk, String alias, char[] password) throws KeyStoreException {
		if(ks==null || pk==null || alias == null  || password==null || alias.isEmpty() || password.length==0) 
			return;
		KeyStore.ProtectionParameter protParam = new KeyStore.PasswordProtection(password);
		KeyStore.PrivateKeyEntry keyEntry = new KeyStore.PrivateKeyEntry(pk, ks.getCertificateChain(alias));
		ks.setEntry(alias, keyEntry , protParam);
	}
	
	/**
	 * Guarda {@link KeyTool#addPrivateKey(KeyStore, PrivateKey, String, char[])}.
	 * 
	 * @param ks		Il KeyStore in cui salvare la chiave privata.
	 * @param pk		La chiave privata.
	 * @param alias		L'alias da associare alla chiave privata.
	 * @param password	La password con cui proteggere la chiave privata.
	 * @throws KeyStoreException
	 */
	public static void addPrivateKey(KeyStore ks, PrivateKey pk, String alias, String password) throws KeyStoreException {
		if(ks==null || pk==null || alias == null  || password==null || alias.isEmpty() || password.isEmpty()) 
			return;
		KeyTool.addPrivateKey(ks, pk, alias, password.toCharArray());
	}
	
	public static CertData getCertificateData(Certificate cert) {
		CertData certInfo = new CertData();
		if(cert==null) 
			return certInfo;
		
		
		
		
		return certInfo;
	}

}

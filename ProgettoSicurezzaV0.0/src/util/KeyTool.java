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

public class KeyTool {
	private static final String provider = "JKS";
	
	private KeyTool() {}
	
	public static KeyStore loadKeystore(String path, String password) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		if(path == null || password == null || path.isEmpty() || password.isEmpty())
			return null;
		
		KeyStore ks = KeyStore.getInstance(KeyTool.provider);
	    char[] pwd = password.toCharArray();

		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
			ks.load(fis, pwd);
		} catch (FileNotFoundException fnfe) {
			ks.load(null, pwd); //crea nuovo keystore
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		return ks;
	}
	
	public static Certificate getCertificate(KeyStore ks,  String alias) throws NoSuchAlgorithmException, UnrecoverableEntryException, KeyStoreException {
		if(ks==null || alias==null || alias.isEmpty())
			return null;
		
		Certificate cert = ks.getCertificate(alias);
		return cert;
	}
	
	public static void storeKeystore(KeyStore ks, String name, String password) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		if(ks==null || password==null || password.isEmpty() || name==null || name.isEmpty())
			return;
	    FileOutputStream fos = null;
	    try {
	        fos = new FileOutputStream(name);
	        ks.store(fos, password.toCharArray());
	    } finally {
	        if (fos != null) {
	            fos.close();
	        }
	    }
	}
	
	public static PrivateKey getPrivateKey(KeyStore ks, String alias, String password) throws NoSuchAlgorithmException, UnrecoverableEntryException, KeyStoreException {
		if (ks == null || password == null || password.isEmpty() || alias == null || alias.isEmpty())
			return null;
		KeyStore.ProtectionParameter protParam = new KeyStore.PasswordProtection(password.toCharArray());

		KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(alias, protParam);
		return pkEntry.getPrivateKey();
	}
	
	public static void addCertificate(KeyStore ks, Certificate cert, String alias) throws KeyStoreException {
		if(ks==null || cert==null || alias == null || alias.isEmpty()) 
			return;
		ks.setCertificateEntry(alias, cert);
	}
	
	public static void addPrivateKey(KeyStore ks, PrivateKey pk, String alias, String password) throws KeyStoreException {
		if(ks==null || pk==null || alias == null  || password==null || alias.isEmpty() || password.isEmpty()) 
			return;
		KeyStore.ProtectionParameter protParam = new KeyStore.PasswordProtection(password.toCharArray());
		KeyStore.PrivateKeyEntry keyEntry = new KeyStore.PrivateKeyEntry(pk, ks.getCertificateChain(alias));
		ks.setEntry(alias, keyEntry , protParam);
	}

}

package test;

import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.Date;

import util.CryptoUtility;
import util.KeyTool;

public class TestKeyTool {

	public static void main(String[] args) throws Exception {
		String name = "/home/giovanni/Dropbox/SII/workspaceSII/ProgettoSicurezza/Server/srv_keystore.jks";
		KeyStore ks = KeyTool.loadKeystore(/*"/home/giovanni/Scrivania/test_ks.jks"*/name, "pasqualino");
		Certificate cert = KeyTool.getCertificate(ks, "client1_key");
		System.out.println(cert.toString() + cert.getClass());
		PrivateKey pk = KeyTool.getPrivateKey(ks, "server_key", "pasqualino");
		System.out.println(pk);
		
		Date nb = new Date();
		Date na = new Date();
		nb.setTime(nb.getTime() + 1000*60*60*24*30);//+30 giorni
		na.setTime(na.getTime() - 1000*60*60*24*30);//-30 giorni
		System.out.println(nb.toString() + na.toString());
		KeyPair pair = CryptoUtility.genKeyPairRSA();
		Certificate certgen = CryptoUtility.createX509Certificate(pair, "Giovanni", "Rossi", "IT", "VeRo", "Rome", "IT","gio@email.test", nb, na);
		System.out.println(certgen);
		
		KeyTool.addCertificate(ks, certgen, "giovanni_key", "pasqualino");
		KeyTool.storeKeystore(ks, name , "pasqualino");
	}

}

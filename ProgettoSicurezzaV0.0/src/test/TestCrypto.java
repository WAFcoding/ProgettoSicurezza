package test;

import java.security.KeyPair;

import util.CryptoUtility;
import util.CryptoUtility.ASYMMCRYPTO_ALGO;
import util.KeyStoreFacility;


public class TestCrypto {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		String testData = "tanto era tanto antico";
		String key = "pasqualino";//lol
		
		System.out.println("===TEST Crypto-Utility===\nDati:" + testData + "\nChiave:" + key);
		
		System.out.println("\nAES-TEST");
		byte[] enc = CryptoUtility.encrypt(CryptoUtility.CRYPTO_ALGO.AES, testData, key);
		System.out.println(new String(enc));
		System.out.println(CryptoUtility.decrypt(CryptoUtility.CRYPTO_ALGO.AES,enc, key));
		
		System.out.println("\nDES-TEST");
		enc = CryptoUtility.encrypt(CryptoUtility.CRYPTO_ALGO.DES, testData, key);
		System.out.println(new String(enc));
		System.out.println(CryptoUtility.decrypt(CryptoUtility.CRYPTO_ALGO.DES,enc, key));		
		
		System.out.println("\nHASH-TEST");
		System.out.println(CryptoUtility.hash(CryptoUtility.HASH_ALGO.MD5, testData));
		System.out.println(CryptoUtility.hash(CryptoUtility.HASH_ALGO.SHA1, testData));
		System.out.println(CryptoUtility.hash(CryptoUtility.HASH_ALGO.SHA256, testData));
		System.out.println(CryptoUtility.hash(CryptoUtility.HASH_ALGO.SHA512, testData));
		
		System.out.println("\nGENERAZIONE COPPIA CHIAVI(DSA)");
		KeyPair keys = CryptoUtility.genDSAKeyPair();
		byte[] signature = CryptoUtility.signDSA(keys.getPrivate(), testData);
		System.out.println("Verifica DSA Firma TESTO CORRETTO:" + CryptoUtility.verifyDSA(keys.getPublic(), signature, testData));
		System.out.println("Verifica DSA Firma TESTO SABOTATO:" + CryptoUtility.verifyDSA(keys.getPublic(), signature, testData + "x"));
		
		System.out.println("\nGENERAZIONE COPPIA CHIAVI(RSA)");
		KeyPair keyps = CryptoUtility.genKeyPairRSA();
		byte[] signaturersa = CryptoUtility.signRSA(keyps.getPrivate(), testData);
		System.out.println("Verifica RSA Firma TESTO CORRETTO:" + CryptoUtility.verifyRSA(keyps.getPublic(), signaturersa, testData));
		System.out.println("Verifica RSA Firma TESTO SABOTATO:" + CryptoUtility.verifyRSA(keyps.getPublic(), signaturersa, testData + "x"));
		
		System.out.println("\nASYMM-TEST");
		System.out.println("RSA");
		enc = CryptoUtility.asymm_encrypt(ASYMMCRYPTO_ALGO.RSA, testData.getBytes(), keyps.getPublic());
		System.out.println("cifrato:" + new String(CryptoUtility.toBase64(enc)));
		System.out.println(new String(CryptoUtility.asymm_decrypt(ASYMMCRYPTO_ALGO.RSA, enc, keyps.getPrivate())));
		
		System.out.println("\nTEST-KEYSTORE");
		KeyStoreFacility keystore = KeyStoreFacility.getInstance();
		keystore.saveKey("giovanni", key);
		System.out.println(keystore.getKey("giovanni"));
		keystore.saveChanges();
		
		keystore.savePrivateKey(keyps.getPrivate(), CryptoUtility.createX509Certificate(keyps, "Giovanni", "Rossi", "IT", "VeRo Co.", "Rome", "Italy", "gio@mail.com"));
		System.out.println(CryptoUtility.toBase64(keystore.getPrivateKey().getEncoded()));
		System.out.println(keystore.getKey("giovanni"));
		keystore.saveChanges();
		
	}

}

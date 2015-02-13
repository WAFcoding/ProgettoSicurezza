package test;

import java.security.KeyPair;

import util.CryptoUtility;
import util.CryptoUtility.ASYMMCRYPTO_ALGO;


public class TestCrypto {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		String testData = "tanto era tanto antico";
		String key = "pasqualino";//lol
		
		System.out.println("===TEST Crypto- Utility===\nDati:" + testData + "\nChiave:" + key);
		
		System.out.println("\nAES-TEST");
		byte[] enc = CryptoUtility.encrypt(CryptoUtility.CRYPTO_ALGO.AES, testData, key);
		System.out.println(new String(enc));
		System.out.println(CryptoUtility.decrypt(CryptoUtility.CRYPTO_ALGO.AES,enc, key));
		
		System.out.println("\nDES-TEST");
		enc = CryptoUtility.encrypt(CryptoUtility.CRYPTO_ALGO.DES, testData, key);
		System.out.println(new String(enc));
		System.out.println(CryptoUtility.decrypt(CryptoUtility.CRYPTO_ALGO.DES,enc, key));	
		
		System.out.println("\nIDEA");
		enc = CryptoUtility.encrypt(CryptoUtility.CRYPTO_ALGO.IDEA, testData, key);
		System.out.println(new String(enc));
		System.out.println(CryptoUtility.decrypt(CryptoUtility.CRYPTO_ALGO.IDEA,enc, key));	
		
		System.out.println("\nTWOFISH");
		enc = CryptoUtility.encrypt(CryptoUtility.CRYPTO_ALGO.TWOFISH_256, testData, key);
		System.out.println(new String(enc));
		System.out.println(CryptoUtility.decrypt(CryptoUtility.CRYPTO_ALGO.TWOFISH_256,enc, key));	
		
		System.out.println("\nRIJNDAEL");
		enc = CryptoUtility.encrypt(CryptoUtility.CRYPTO_ALGO.RIJNDAEL, testData, key);
		System.out.println(new String(enc));
		System.out.println(CryptoUtility.decrypt(CryptoUtility.CRYPTO_ALGO.RIJNDAEL,enc, key));	
		
		System.out.println("\nRC6");
		enc = CryptoUtility.encrypt(CryptoUtility.CRYPTO_ALGO.RC6, testData, key);
		System.out.println(new String(enc));
		System.out.println(CryptoUtility.decrypt(CryptoUtility.CRYPTO_ALGO.RC6,enc, key));	
		
		System.out.println("\nHASH-TEST");
		System.out.println(CryptoUtility.hash(CryptoUtility.HASH_ALGO.MD5, testData));
		System.out.println(CryptoUtility.hash(CryptoUtility.HASH_ALGO.SHA1, testData));
		System.out.println(CryptoUtility.hash(CryptoUtility.HASH_ALGO.SHA256, testData));
		System.out.println(CryptoUtility.hash(CryptoUtility.HASH_ALGO.SHA512, testData));
		
		System.out.println("\nGENERAZIONE COPPIA CHIAVI(DSA)");
		KeyPair keys = CryptoUtility.genKeyPairDSA();
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
		System.out.println("Chiave pubblica:"+CryptoUtility.toBase64(keyps.getPublic().getEncoded()));
		enc = CryptoUtility.asymm_encrypt(ASYMMCRYPTO_ALGO.RSA, testData.getBytes(), keyps.getPublic());

		String cifrato= new String(CryptoUtility.toBase64(enc));
		System.out.println("cifrato:" + cifrato);
		byte[] dec= CryptoUtility.fromBase64(cifrato);
		KeyPair keys2 = CryptoUtility.genKeyPairRSA();
		System.out.println(new String(CryptoUtility.asymm_decrypt(ASYMMCRYPTO_ALGO.RSA, dec, keys2.getPrivate())));		
	}

}

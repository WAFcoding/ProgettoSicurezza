package test;

import java.math.BigInteger;
import java.security.KeyPair;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;

import util.CryptoUtility;
import util.KeyStoreFacility;


public class TestCrypto {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		String testData = "tanto era tanto antico";
		String key = "pasqualino";
		
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
		AsymmetricCipherKeyPair keys = CryptoUtility.genDSAKeyPair();
		BigInteger[] signature = CryptoUtility.signDSA(keys.getPrivate(), testData);
		System.out.println("Verifica DSA Firma TESTO CORRETTO:" + CryptoUtility.verifyDSA(keys.getPublic(), signature, testData));
		System.out.println("Verifica DSA Firma TESTO SABOTATO:" + CryptoUtility.verifyDSA(keys.getPublic(), signature, testData + "x"));
		
		System.out.println("\nGENERAZIONE COPPIA CHIAVI(RSA)");
		KeyPair keyps = CryptoUtility.genKeyPairRSA();
		byte[] signaturersa = CryptoUtility.signRSA(keyps.getPrivate(), testData);
		System.out.println("Verifica RSA Firma TESTO CORRETTO:" + CryptoUtility.verifyRSA(keyps.getPublic(), signaturersa, testData));
		System.out.println("Verifica RSA Firma TESTO SABOTATO:" + CryptoUtility.verifyRSA(keyps.getPublic(), signaturersa, testData + "x"));
		
		System.out.println("\nTEST-KEYSTORE");
		KeyStoreFacility keystore = KeyStoreFacility.getInstance();
		keystore.saveKey("giovanni", key);
		System.out.println(keystore.getKey("giovanni"));
		keystore.saveChanges();
		
		//FIXME
		keystore.savePrivateKey("giovanni", keyps.getPrivate());
		System.out.println(keystore.getPrivateKey("giovanni").toString());
		keystore.saveChanges();
		
	}

}

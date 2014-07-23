package test;

import java.math.BigInteger;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;

import util.CryptoUtility;


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
		
		System.out.println("\nGENERAZIONE COPPIA CHIAVI");
		AsymmetricCipherKeyPair keys = CryptoUtility.getKeyPair();
		BigInteger[] signature = CryptoUtility.sign(keys.getPrivate(), testData);
		System.out.println("Verifica Firma TESTO CORRETTO:" + CryptoUtility.verify(keys.getPublic(), signature, testData));
		System.out.println("Verifica Firma TESTO SABOTATO:" + CryptoUtility.verify(keys.getPublic(), signature, testData + "x"));
		
	}

}

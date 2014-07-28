package test;

import java.security.KeyPair;

import util.CryptoUtility;
import util.PublicKeyRing;

public class TestKeyRing {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		System.out.println("Generazione chiavi RSA");
		KeyPair k1 = CryptoUtility.genKeyPairRSA();
		KeyPair k2 = CryptoUtility.genKeyPairRSA();
		KeyPair k3 = CryptoUtility.genKeyPairRSA();
		KeyPair k4 = CryptoUtility.genKeyPairRSA();
		KeyPair k5 = CryptoUtility.genKeyPairRSA();
		
		System.out.println("Salvataggio chiavi nel key ring");
		PublicKeyRing keyRing = PublicKeyRing.getInstance();
		keyRing.savePublicKey("giovanni", k1.getPublic());
		keyRing.savePublicKey("pasquale", k2.getPublic());
		keyRing.savePublicKey("marco", k3.getPublic());
		keyRing.savePublicKey("stefano", k4.getPublic());
		keyRing.savePublicKey("luca", k5.getPublic());
		
		System.out.println("Salvataggio file");
		keyRing.saveToFile();
		
		System.out.println("Lettura");
		System.out.println("giovanni:\t" + CryptoUtility.toBase64(keyRing.getPublicKey("giovanni").getEncoded()) + "\n===");
		System.out.println("pasquale:\t" + CryptoUtility.toBase64(keyRing.getPublicKey("pasquale").getEncoded()) + "\n===");
		System.out.println("marco:\t" + CryptoUtility.toBase64(keyRing.getPublicKey("marco").getEncoded()) + "\n===");
		System.out.println("stefano:\t" + CryptoUtility.toBase64(keyRing.getPublicKey("stefano").getEncoded()) + "\n===");
		System.out.println("luca:\t" + CryptoUtility.toBase64(keyRing.getPublicKey("luca").getEncoded()) + "\n===");
		
	}

}

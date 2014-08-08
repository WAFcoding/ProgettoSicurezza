package test;

import util.CryptoUtility;
import util.CryptoUtility.CRYPTO_ALGO;
import bean.LevelKey;
import servermain.*;

public class TestLevelKey {

	public static void main(String[] args) throws Exception {
		byte[] key = "pasqualino".getBytes();
		ServerMasterKey.passphrase = "pasqualino".toCharArray(); 
		
		String k1 = "pasqualino";
		String k2 = "giovannino";
		String k3 = "chomskynormalform";
		String k4 = "provaprovaprovaprova";
		
		byte[] ek1 = CryptoUtility.encrypt(CRYPTO_ALGO.AES, k1.getBytes(), key);
		byte[] ek2 = CryptoUtility.encrypt(CRYPTO_ALGO.AES, k2.getBytes(), key);
		byte[] ek3 = CryptoUtility.encrypt(CRYPTO_ALGO.AES, k3.getBytes(), key);
		byte[] ek4 = CryptoUtility.encrypt(CRYPTO_ALGO.AES, k4.getBytes(), key);
		
		String bk1 = CryptoUtility.toBase64(ek1);
		String bk2 = CryptoUtility.toBase64(ek2);
		String bk3 = CryptoUtility.toBase64(ek3);
		String bk4 = CryptoUtility.toBase64(ek4);
		
		System.out.println(bk1 + "\n" + bk2 + "\n" + bk3 + "\n" + bk4);
		System.out.println("======================");
		
		byte[] fbk1 = CryptoUtility.fromBase64(bk1);
		byte[] fbk2 = CryptoUtility.fromBase64(bk2);
		byte[] fbk3 = CryptoUtility.fromBase64(bk3);
		byte[] fbk4 = CryptoUtility.fromBase64(bk4);
		
		byte[] dk1 = CryptoUtility.decrypt(CRYPTO_ALGO.AES, fbk1, key);
		byte[] dk2 = CryptoUtility.decrypt(CRYPTO_ALGO.AES, fbk2, key);
		byte[] dk3 = CryptoUtility.decrypt(CRYPTO_ALGO.AES, fbk3, key);
		byte[] dk4 = CryptoUtility.decrypt(CRYPTO_ALGO.AES, fbk4, key);
		System.out.println(new String(dk1) + "\n" + new String(dk2) + "\n" + new String(dk3) + "\n" + new String(dk4));
		System.out.println("======================");
		
		LevelKey lk1 = new LevelKey();
		LevelKey lk2 = new LevelKey();
		LevelKey lk3 = new LevelKey();
		LevelKey lk4 = new LevelKey();
		
		lk1.setKey(bk1);
		lk2.setKey(bk2);
		lk3.setKey(bk3);
		lk4.setKey(bk4);
		
		String bkg1 = lk1.getClearKey();
		String bkg2 = lk2.getClearKey();
		String bkg3 = lk3.getClearKey();
		String bkg4 = lk4.getClearKey();
		
		System.out.println(bkg1 + "\n" + bkg2 + "\n" + bkg3 + "\n" + bkg4);
		System.out.println("======================");
		
		byte[] fbkg1 = CryptoUtility.fromBase64(bkg1);
		byte[] fbkg2 = CryptoUtility.fromBase64(bkg2);
		byte[] fbkg3 = CryptoUtility.fromBase64(bkg3);
		byte[] fbkg4 = CryptoUtility.fromBase64(bkg4);
		
		System.out.println(new String(fbkg1) + "\n" + new String(fbkg2) + "\n" + new String(fbkg3) + "\n" + new String(fbkg4));
		System.out.println("======================");
	}
}

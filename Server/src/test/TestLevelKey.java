package test;

import servermain.ServerMasterData;
import util.CryptoUtility;
import util.CryptoUtility.CRYPTO_ALGO;
import bean.LevelKey;
import db.dao.LevelKeyDAO;
import db.dao.LevelKeyDaoImpl;

public class TestLevelKey {

	public static void main(String[] args) throws Exception {
		LevelKeyDAO dao = new LevelKeyDaoImpl();
		
		byte[] key = "progettoSII".getBytes();
		ServerMasterData.passphrase = "progettoSII".toCharArray(); 
		
		String k1 = "CNJNERFNERUIW4858T5Y8E9WAPF9A9CQR0CKXEM3784X2XR45N37578X432X";
		String k2 = "SDFARFBCYGFARGSUIOGSCGIO8ASCG85SC5O9SC58M5O5SCMTA55C8A5MXZTE";
		String k3 = "HXCRN25NGWTIUIW96TXU0345Y6X3480X34U8FAEGE04893FVR9Q34M8FQTU3";
		String k4 = "VECXIRUNJGFUIWREGFUINRVNARFAUG98U98TU88849123134CNERUI38ACDG";
		String k5 = "SDC4NYRH4YR731X0TCY1MT7Y89TCKVTY33KCP2IY5JQPC85QUYCT58PXYT8T";
		String k6 = "URH45C78Y578230XCXCU8425U842UXZS589UC842U6X5X2390X9XTJIO35JC";
		String k7 = "SDHC471R48XXC4N8717X4890X48XZ491XITGRNHYUUWERIOPP598595V9U89";
		
		
		byte[] ek1 = CryptoUtility.encrypt(CRYPTO_ALGO.AES, k1.getBytes(), key);
		byte[] ek2 = CryptoUtility.encrypt(CRYPTO_ALGO.AES, k2.getBytes(), key);
		byte[] ek3 = CryptoUtility.encrypt(CRYPTO_ALGO.AES, k3.getBytes(), key);
		byte[] ek4 = CryptoUtility.encrypt(CRYPTO_ALGO.AES, k4.getBytes(), key);
		byte[] ek5 = CryptoUtility.encrypt(CRYPTO_ALGO.AES, k5.getBytes(), key);
		byte[] ek6 = CryptoUtility.encrypt(CRYPTO_ALGO.AES, k6.getBytes(), key);
		byte[] ek7 = CryptoUtility.encrypt(CRYPTO_ALGO.AES, k7.getBytes(), key);
		
		String bk1 = CryptoUtility.toBase64(ek1);
		String bk2 = CryptoUtility.toBase64(ek2);
		String bk3 = CryptoUtility.toBase64(ek3);
		String bk4 = CryptoUtility.toBase64(ek4);
		String bk5 = CryptoUtility.toBase64(ek5);
		String bk6 = CryptoUtility.toBase64(ek6);
		String bk7 = CryptoUtility.toBase64(ek7);
		
		dao.updateKey(new LevelKey(1, bk1));
		dao.updateKey(new LevelKey(2, bk2));
		dao.updateKey(new LevelKey(3, bk3));
		dao.updateKey(new LevelKey(4, bk4));
		dao.updateKey(new LevelKey(5, bk5));
		dao.updateKey(new LevelKey(6, bk6));
		dao.updateKey(new LevelKey(7, bk7));
		
		System.out.println(bk1 + "\n" + bk2 + "\n" + bk3 + "\n" + bk4+ "\n" + bk5+ "\n" + bk6+ "\n" + bk7);
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

/**
 * Pasquale Verlotta - pasquale.verlotta@gmail.com
 * ProgettoSicurezzaV0.0 - 09/feb/2015
 */
package test;

import org.hibernate.Session;

import util.CryptoUtility;
import util.HibernateUtil;
import util.CryptoUtility.HASH_ALGO;

/**
 * @author pasquale
 *
 */
public class TestDecryptDb {
	
	public static void main(String[] arg){
		if(!HibernateUtil.isCreated()){
			HibernateUtil.setDbPath("/home/pasquale/ProgettoSicurezza/pupparo/");
			//HibernateUtil.createSession();
		}
		
		//Session session= HibernateUtil.getSessionFactory().openSession();

		String hash_password= "";
		try {
			hash_password = CryptoUtility.hash(HASH_ALGO.SHA1, "pupparo");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//HibernateUtil.decryptDb(hash_password);
		HibernateUtil.encryptDb(hash_password);
	}
}

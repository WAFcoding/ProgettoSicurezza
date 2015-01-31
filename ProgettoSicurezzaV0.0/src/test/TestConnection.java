package test;

import java.io.ByteArrayInputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;

import usersManagement.User;
import util.CryptoUtility;
import util.KeyTool;
import connection.ClientConfig;
import connection.ConnectionFactory;
import connection.KeyDistributionClient;
import connection.RegistrationClient;
import entities.RegistrationBean;

public class TestConnection {

	/*
	 * {type:"submit", 
	 * name:"Giorgio", 
	 * surname:"Bianchi",
	 * city:"Rome", 
	 * country:"Italy", 
	 * country_code:"IT", 
	 * organization:"Ve.Ro.", 
	 * public_key:"blablabla"}
	 */

	
	public static void testSignup() throws Exception{
		KeyPair pair = CryptoUtility.genKeyPairRSA();
		
		User user = new User();
		user.setName("Giorgio");
		user.setSurname("Bianchi");
		user.setCity("Rome");
		user.setCountry("Italy");
		user.setCountry_code("IT");
		user.setOrganization("blablabla");
		user.setPublicKey(CryptoUtility.toBase64(pair.getPublic().getEncoded()));
		
		System.out.println("getting connection...");
		RegistrationClient reg = ConnectionFactory.getRegistrationServerConnection("localhost", "progettoSII");
		System.out.println("ok-->");
		String secid = reg.submitRegistration(user);
		
		System.out.println("secureIdentifier:" + secid);
		
		System.out.println("privKey:" + CryptoUtility.toBase64(pair.getPrivate().getEncoded()));
		System.out.println("publKey:" + CryptoUtility.toBase64(pair.getPublic().getEncoded()));
		
		reg.close();
	}
	
	public static void testRegistration() throws Exception{
		
		//PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(CryptoUtility.fromBase64("")));
		PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(CryptoUtility.fromBase64("MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKj/oXxC2Nxvev6Yjb8PaiYeDGIeRUjMWFl2nco3Ocil3tktG4M7ot6HKO67tIV1YSkOiYPEms8/l29sgso9NnxgBcMLYQc0BokpHIuAjxnbJc7G/3W7Dt2Fc9FVPX0MwUWNuYk0rS4nPa5MVaQFvGksTIzfpQ9zPer7GQXhrq1XAgMBAAECgYBwSnrocBpcTg6xgHgezYVAkpKz110+A9loiHJL3OoeMmiicWBokIWlyrUd+8b1LotClpA057vdnWln9ffpnUbHwgQ4blOXwdTUFSUKrgG/Uf8xwQ4NN1JPY940A34qAbiQYBMwisPnaqJ58HES2goHsgpjMbmx/48xS7wjPb5oIQJBAPIRmq4YtlZKVeB5DZ2O/vt3Uky1IbODqzsyCnhEu3RjXkeGS0M9bugDMtuDozauCSmyOgbuZK9Xl6TjfTCzre8CQQCyuXv1UigVBzD6ucMHCQJuWKUWVuVGmlwTaOUzJIh0Gk7VBfWiNev1Uf6Ci4Ic/h2gOc8u8Gwi7hejNqgt2V8ZAkBIk3Cd2/jnHDhtKb4kTBg0ysyZBQGKseJnyBQNhQXy2kwNA72S9ltHIpZf7DLGFR3YK4BsTZNQYuGrZS1GqchvAkEAkDoc7WjiTa3i+cJkv02m95NzjeaL2YEBouw4YiAuObttOH05rzBifTMw9vpMAseS8tUT++Yq7blDvAOEjpNnKQJASaAHXMFzIKlHeTb11QTjnxz4+ZfC99RMhJw4M2X8QsV7nN0/FEnbgwqWLd+Q2KSYubhnAq1N5ufrnMSfgjdl/A==")));
		
		RegistrationClient reg = ConnectionFactory.getRegistrationServerConnection("localhost", "progettoSII");
		RegistrationBean bean = reg.retrieveRegistrationDetails("AFC038E15EEF2151759741B4531BEEEB");
		
		System.out.println("trustLevel:" + bean.getTrustLevel());
		CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
		X509Certificate cert = (X509Certificate)certFactory.generateCertificate(new ByteArrayInputStream(bean.getCertificateData()));
		
		KeyStore ks = KeyTool.loadKeystore(ClientConfig.getInstance().getProperty(ClientConfig.KEYSTORE_PATH), "progettoSII");
		KeyTool.addNewPrivateKey(ks, privateKey, cert, "giorgio_1", "progettoSII".toCharArray());
		
		KeyTool.storeKeystore(ks, ClientConfig.getInstance().getProperty(ClientConfig.KEYSTORE_PATH), "progettoSII");
		
		reg.close();
	}
	
	public static void testUsage() throws Exception{
		KeyDistributionClient cli = ConnectionFactory.getKeyDistributionServerConnection("localhost", "giorgio_1", "progettoSII");
		String key = cli.getLevelKey(1);
		System.out.println("key: "+key);
		
		cli.close();
	}
	
	public static void main(String[] args) throws Exception {
		//testSignup();
		//testRegistration();
		
		/**
		 * TODO: fino a qui ok. Bisogna solo vedere perché non considera l'identità trusted sul server (forse problema con l'UID - da vedere)
		 * FIXME: bug su server in findBySecureId . Se si cerca SID non esistente da nullpoiterexception
		 */
		testUsage();
	}

}

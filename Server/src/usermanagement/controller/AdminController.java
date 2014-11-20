package usermanagement.controller;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.List;

import org.bouncycastle.operator.OperatorCreationException;

import request_man.RequestStatus;
import servermain.ServerMasterData;
import util.CryptoUtility;
import util.CryptoUtility.CERT_SIGALGO;
import util.KeyTool;
import bean.User;
import bean.UserCertificateBean;
import db.dao.UserCertificateDAO;
import db.dao.UserCertificateDaoImpl;
import db.dao.UserDAO;
import db.dao.UserDaoImpl;

public class AdminController {

	public AdminController() {
	}
	
	private static String buildUserAlias(UserCertificateBean user) {
		return user.getName() + "_" + user.getId();
	}
	
	
	
	public static void acceptUser(UserCertificateBean user, int trustLevel) {
		if(trustLevel<0)
			return;
		
		X509Certificate certUser;
		KeyStore ks;
		try {
			//chiave privata server
			ks = KeyTool.loadKeystore(ServerMasterData.keyStorePath, ServerMasterData.passphrase);
			PrivateKey serverPrivateKey = KeyTool.getPrivateKey(ks, "server_key", ServerMasterData.passphrase) ; 
			
			//chiave pubblica utente
			PublicKey clientPublicKey;
			KeyFactory factory = KeyFactory.getInstance("RSA"); 
		    clientPublicKey =  factory.generatePublic( new X509EncodedKeySpec(CryptoUtility.fromBase64(user.getPublicKey()))); 
			
		    //certificato
			Date notAfter = new Date();
			notAfter.setTime(notAfter.getTime() + 1000*60*60*24*30*12*5); //5 anni (troppo?)
			
			certUser = CryptoUtility.createX509Certificate2(
					CERT_SIGALGO.SHA256withRSA, 
					clientPublicKey, 
					serverPrivateKey,
					user.getId()+"",
					user.getName(),
					user.getSurname(),
					user.getCountryCode(),
					user.getOrganization(), 
					user.getCity(), 
					user.getCountry(), 
					"", 
					notAfter, 
					new Date());
			
			//aggiunta al keystore del server
			KeyTool.addCertificate(ks, certUser, buildUserAlias(user));
			KeyTool.storeKeystore(ks, ServerMasterData.keyStorePath , ServerMasterData.passphrase);
			
			//aggiornamento DB
			UserCertificateDAO bdao = new UserCertificateDaoImpl();
			user.setStatus(RequestStatus.ACCEPTED);
			bdao.updateUserCertificate(user);
			
			UserDAO udao = new UserDaoImpl();
			User u = new User();
			u.setUsername(buildUserAlias(user));
			u.setTrustLevel(trustLevel);
			u.setPublicKey(user.getPublicKey());
			
			udao.saveUser(u);
			
		} catch (KeyStoreException | NoSuchAlgorithmException
				| CertificateException | IOException | UnrecoverableEntryException | InvalidKeyException | SecurityException | SignatureException | NoSuchProviderException | OperatorCreationException | InvalidKeySpecException e) {
			e.printStackTrace();
		}		
	}
	
	public static void changeUserTrustLevel(User user, int newTrustLevel) {
		if(newTrustLevel < 0)
			return;
		
		UserDAO udao = new UserDaoImpl();
		user.setTrustLevel(newTrustLevel);
		
		udao.updateUser(user);
	}
	
	public static void blockUser(UserCertificateBean user) {
		UserCertificateDAO bdao = new UserCertificateDaoImpl();
		user.setStatus(RequestStatus.REJECTED);
		bdao.updateUserCertificate(user);
		
		KeyStore ks;
		try {
			ks = KeyTool.loadKeystore(ServerMasterData.keyStorePath, ServerMasterData.passphrase);
			if(ks!=null && ks.containsAlias(buildUserAlias(user))) {
				KeyTool.removeEntry(ks, buildUserAlias(user));
				KeyTool.storeKeystore(ks, ServerMasterData.keyStorePath, ServerMasterData.passphrase);
			}
		} catch (KeyStoreException | NoSuchAlgorithmException
				| CertificateException | IOException e) {
			e.printStackTrace();
		}
		
	}

}

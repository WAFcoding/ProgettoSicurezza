package request_man;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.cert.Certificate;

import servermain.ServerMasterData;
import util.CryptoUtility;
import util.KeyTool;
import bean.UserCertificateBean;

public class CertificateReadyResult extends Result {

	private UserCertificateBean user;
	
	public CertificateReadyResult(UserCertificateBean user) {
		this.user = user;
	}

	@Override
	public String toSendFormat() {

		KeyStore ks;
		Certificate cert;
		String certData;
		try {
			ks = KeyTool.loadKeystore(ServerMasterData.keyStorePath, ServerMasterData.passphrase);
			cert = KeyTool.getCertificate(ks, user.getUID());
			certData = CryptoUtility.toBase64(cert.getEncoded());
			certData = certData.replace("\n", "");
		} catch (KeyStoreException | NoSuchAlgorithmException
				| CertificateException | IOException | UnrecoverableEntryException e) {
			e.printStackTrace();
			return "Internal Error";
		}
		
		//System.err.println(certData);
		
		return "{trustLevel:" + user.getTrustLevel() + ", cert:\""+ certData + "\"}";
	}

}

package servermain.auth;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

import servermain.ServerController;

public class AuthServerController extends ServerController {
	
	public static final int SERVER_PORT = 8889;
	
	/**
	 * Costruttore principale del controllore.
	 * @param password	La password del keystore.
	 * 
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws UnrecoverableKeyException	Se la password non è corretta.
	 * @throws KeyManagementException
	 */
	public AuthServerController() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, UnrecoverableKeyException, KeyManagementException {
				
		//creo socket per server
		SSLServerSocketFactory ssf = super.init();
		
		SSLServerSocket s = (SSLServerSocket) ssf.createServerSocket(SERVER_PORT);		
		s.setNeedClientAuth(false);

		_mainServerThread = new AuthServerThread(s);
	}
}

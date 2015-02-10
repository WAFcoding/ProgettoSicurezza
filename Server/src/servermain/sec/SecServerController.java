package servermain.sec;

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

/**
 * Controllore per il thread principale del server.
 * 
 * @author Giovanni Rossi
 */
public class SecServerController extends ServerController {
	
	public static final int SERVER_PORT = 8888;
	
	private SSLServerSocketFactory ssf = null;
	private SSLServerSocket s = null;
	
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
	public SecServerController() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, UnrecoverableKeyException, KeyManagementException {
		startOrRestartServer();
	}
	
	public void startOrRestartServer() throws IOException, UnrecoverableKeyException, KeyManagementException, KeyStoreException, NoSuchAlgorithmException, CertificateException {
		if(s!=null)
			s.close();
		ssf = super.init();
		
		this.s = (SSLServerSocket) ssf.createServerSocket(SERVER_PORT);		
		s.setNeedClientAuth(true);

		if(_mainServerThread==null) {
			_mainServerThread = new SecServerThread(s);
			return;
		}
		
		stopServer();
		_mainServerThread = new SecServerThread(s);
		startServer();
		
	}
}

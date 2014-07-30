import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

/**
 * Controllore per il thread principale del server.
 * 
 * @author Giovanni Rossi
 */
public class ServerController {

	/**
	 * La porta di default del server.
	 */
	public static final int SERVER_PORT = 8888;
	
	/**
	 * Il percorso del keystore da usare per la connessione cifrata e l'autenticazione.
	 */
	private static final String SERVER_KEYSTORE = "/home/giovanni/Dropbox/SII/workspaceSII/ProgettoSicurezza/Server/srv_keystore.jks";
	
	/**
	 * La password del keystore.
	 * TODO:rimuovi
	 */
	private String serverPassword = "pasqualino";//FIXME:rimuovi password
	
	/**
	 * Il thread principale del server.
	 */
	private ServerThread _mainServerThread = null;
	
	/**
	 * Flag che consente di stabilire se il server è attualmente in esecuzione o se si sta avviando.
	 */
	private static AtomicBoolean running = new AtomicBoolean(false);
	
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
	public ServerController(String password) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, UnrecoverableKeyException, KeyManagementException {
		
		this.serverPassword = (password==null || password.isEmpty()) ? this.serverPassword : password;
		
		System.setProperty("javax.net.ssl.trustStore", SERVER_KEYSTORE);
		System.setProperty("javax.net.ssl.trustStorePassword", serverPassword);
		
		//recupero keystore
		KeyStore ks = KeyStore.getInstance("JKS");
		ks.load(new FileInputStream(SERVER_KEYSTORE), serverPassword.toCharArray());
		
		KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
		kmf.init(ks, serverPassword.toCharArray());
		
		//inizializzo contesto SSL
		SSLContext sc = SSLContext.getInstance("TLS");
		sc.init(kmf.getKeyManagers(), null, null);
		
		//creo socket per server
		SSLServerSocketFactory ssf = sc.getServerSocketFactory();
		
		SSLServerSocket s = (SSLServerSocket) ssf.createServerSocket(8888);		
		s.setNeedClientAuth(true);

		_mainServerThread = new ServerThread(s);
	}
	
	/**
	 * Avvia il server se non è già in esecuzione.
	 */
	public void startServer() {
		if(!running.get()) {
			_mainServerThread.start();
			running.set(true);
		}
	}
	
	/**
	 * Ferma il server in modo soft (tramite <code>interrupt()</code>).
	 * @see {@link Thread#interrupt()}
	 */
	public void stopServer() {
		if(running.get()) {
			_mainServerThread.interrupt();
			running.set(false);
		}
	}
}

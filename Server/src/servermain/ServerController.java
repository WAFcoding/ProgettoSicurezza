package servermain;

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
import javax.net.ssl.SSLServerSocketFactory;

/**
 * Controllore astratto per il Server
 * 
 * @author Giovanni Rossi
 */
public abstract class ServerController {

	/**
	 * La porta di default del server.
	 */
	protected static int DEFAULT_SERVER_PORT = 8888;

	/**
	 * Il percorso del keystore da usare per la connessione cifrata e
	 * l'autenticazione. FIXME: passare percorso keystore diversamente.
	 */
	protected static final String SERVER_KEYSTORE = "/home/giovanni/Dropbox/SII/workspaceSII/ProgettoSicurezza/Server/srv_keystore.jks";

	/**
	 * La password del keystore. FIXME:rimuovi
	 */
	protected String serverPassword = "pasqualino";

	/**
	 * Il thread principale del server.
	 */
	protected ServerThread _mainServerThread = null;

	/**
	 * Flag che consente di stabilire se il server è attualmente in esecuzione o
	 * se si sta avviando.
	 */
	protected AtomicBoolean running = new AtomicBoolean(false);

	protected SSLServerSocketFactory init() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, UnrecoverableKeyException, KeyManagementException {
		// TODO: rimuovi su versione release
		if (ServerMasterKey.passphrase == null) {
			ServerMasterKey.passphrase = this.serverPassword.toCharArray();
		}

		System.setProperty("javax.net.ssl.trustStore", SERVER_KEYSTORE);
		System.setProperty("javax.net.ssl.trustStorePassword", serverPassword);

		// recupero keystore
		KeyStore ks = KeyStore.getInstance("JKS");
		ks.load(new FileInputStream(SERVER_KEYSTORE),
				ServerMasterKey.passphrase);

		KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
		kmf.init(ks, ServerMasterKey.passphrase);

		// inizializzo contesto SSL
		SSLContext sc = SSLContext.getInstance("TLS");
		sc.init(kmf.getKeyManagers(), null, null);

		// creo socket per server
		return sc.getServerSocketFactory();

	}

	/**
	 * Avvia il server se non è già in esecuzione.
	 */
	public void startServer() {
		if (!running.get()) {
			_mainServerThread.start();
			running.set(true);
		}
	}

	/**
	 * Ferma il server in modo soft (tramite <code>interrupt()</code>).
	 * 
	 * @see {@link Thread#interrupt()}
	 */
	public void stopServer() {
		if (running.get()) {
			_mainServerThread.interrupt();
			running.set(false);
		}
	}
}

import java.io.Console;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

/**
 * Classe principale per il server SSL.
 * 
 * @author Giovanni Rossi
 */
public class ServerMain {

	/**
	 * Avvia il server e richiede le credenziali del keystore.
	 * @param args	Non usati.
	 */
	public static void main(String[] args) {

		//solo da console (quando si rilascia il JAR)
		Console cs = System.console();
		char[] pwd = new char[] {};
		if (cs != null) {
			cs.printf(
					"%s\n%s",
					"SSL Server - Progetto SII (Giovanni Rossi, Pasquale Verlotta)",
					"Password:");
			cs.flush();
			pwd = cs.readPassword();
		}
		
		try {
			new ServerController(new String(pwd)).startServer();
		} catch (UnrecoverableKeyException | KeyManagementException
				| KeyStoreException | NoSuchAlgorithmException
				| CertificateException | IOException e) {
			e.printStackTrace();
		}
	}

}

package servermain;
import java.io.Console;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Scanner;

import servermain.auth.AuthServerController;
import servermain.sec.SecServerController;
import usermanagement.controller.LayoutController;

/**
 * Classe principale per il server SSL.
 * 
 * @author Giovanni Rossi
 */
public class ServerMain {

	/**
	 * Comando per entrare nel pannello di configurazione.
	 */
	private static final String admin_access = "adminconf";
	
	/**
	 * Comando per chiudere questo server.
	 */
	private static final String close_server = "quit";

	/**
	 * Comando per stampare su console i comandi supportati.
	 */
	private static final String help = "?";
	
	/**
	 * Codice per continuare esecuzione del server.
	 */
	private static final int CONTINUE_CODE = 0;
	
	/**
	 * Codice che provoca la chiusura del server.
	 */
	private static final int EXIT_CODE = 1;	
	
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
			ServerMasterData.passphrase = pwd;
			ServerMasterData.keyStorePath = "/home/giovanni/workspaceSII/ProgettoSicurezza/Server/srv_keystore.jks";
		}
		
		try {
			new SecServerController().startServer();
			new AuthServerController().startServer();
		} catch (UnrecoverableKeyException | KeyManagementException
				| KeyStoreException | NoSuchAlgorithmException
				| CertificateException | IOException e) {
			e.printStackTrace();
		}
		
		new LayoutController();
		//openConsoleSession();
	}

	/**
	 * Permette all'amministratore di interagire con il server per gestire i certificati degli utenti.
	 */
	private static void openConsoleSession() {
		System.out.println("Waiting for command (? for help):\n");
		Scanner scanner = new Scanner(System.in);
		boolean quit=false;
		while(!quit) {
			if(scanner.hasNextLine()) {
				String command = scanner.nextLine();
				if(commandDispatcher(command) == EXIT_CODE)
					quit=true;
			}
		}
		scanner.close();
		System.exit(0);
	}

	/**
	 * Esegue l'operazione corrispondente al comando impartito
	 * @param command	Il comando impartito dall'amministratore.
	 * 
	 * @return Un codice che permette di decidere cosa fare successivamente all'esecuzione del comando:
	 * 			<ul>
	 * 			<li>0 = continua esecuzione ({@link ServerMain#CONTINUE_CODE}});</li>
	 * 			<li>1 = chiudi il server ({@link ServerMain#EXIT_CODE}).</li>
	 * 			</ul>
	 * 
	 */
	private static int commandDispatcher(String command) {
		if(command.equalsIgnoreCase(admin_access)) {
			new LayoutController();
			return CONTINUE_CODE;
		} else if(command.equalsIgnoreCase(close_server)) {
			System.out.println("Goodbye!");
			return EXIT_CODE;
		} else if (command.equalsIgnoreCase(help)) {
			System.out.println("===Server Console Help Section===");
			System.out.println("--List of supported commands");
			System.out.println("adminconf\t--\tOpen admin panel to manage user certificates.");
			System.out.println("quit     \t--\tClose this server.");
		} else {
			System.out.println("!!Unknown command!!");
		}
		//aggiungi qui altri comandi
		
		return CONTINUE_CODE;
	}

}

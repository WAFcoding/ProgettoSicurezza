package servermain;
import java.io.IOException;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;

/**
 * Definisce il thread principale del server.
 * 
 * @author Giovanni Rossi
 */
public abstract class ServerThread extends Thread implements Runnable {

	/**
	 * La socket utilizzata dal server per accettare le connessioni.
	 */
	protected SSLServerSocket socket;
	
	/**
	 * Costruttore principale del thread.
	 * @param s		La socket su cui accettare le connessioni.
	 */
	protected ServerThread(SSLServerSocket s) {
		if(s==null)
			throw new NullPointerException("Invalid SSL Server Socket");
		this.socket = s;
		printServerSocketInfo(this.socket);
	}

	/**
	 * Attende connessioni e genera nuovi thread per gestirle al loro arrivo.
	 */
	@Override
	public void run() {
		WorkerThread work = null;
		while(true) {
			try {
				SSLSocket sock = (SSLSocket)socket.accept();
				work = initWorker(sock);
				work.setDaemon(true);
				work.setName("Worker-"+System.currentTimeMillis());
				work.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected abstract WorkerThread initWorker(SSLSocket sock) throws IOException;

	/**
	 * Stampa le informazioni essenziali della socket su cui è in ascolto il server.
	 * @param s		La socket su cui il server è in ascolto.
	 */
	protected static void printServerSocketInfo(SSLServerSocket s) {
		System.out.println("Server socket class: " + s.getClass());
		System.out.println("\tSocket address = " + s.getInetAddress().toString());
		System.out.println("\tSocket port = " + s.getLocalPort());
		System.out.println("\tNeed client authentication = " + s.getNeedClientAuth());
		System.out.println("\tWant client authentication = " + s.getWantClientAuth());
		System.out.println("\tUse client mode = " + s.getUseClientMode());
	}
}

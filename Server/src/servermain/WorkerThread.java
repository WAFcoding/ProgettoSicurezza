package servermain;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import request.RequestFactory;

/**
 * Definisce il thread che esegue il lavoro del server.
 * 
 * @author Giovanni Rossi
 */
public class WorkerThread extends Thread implements Runnable{

	/**
	 * Oggetto per la scrittura di dati sul peer.
	 */
	private BufferedWriter w;
	
	/**
	 * Oggetto per la lettura di dati dal peer.
	 */
	private BufferedReader r;
	
	/**
	 * Socket della connessione corrente.
	 */
	private SSLSocket sock;
	
	/**
	 * Messaggio di benvenuto del server.
	 */
	private static final String welcomeMessage = "+OK SSL Server Ready";
	
	/**
	 * Costruttore principale di questo thread.
	 * @param sock	La socket relativa alla connessione corrente.
	 * 
	 * @throws IOException
	 */
	public WorkerThread(SSLSocket sock) throws IOException {
		if(sock==null)
			throw new NullPointerException("Socket Passed is NULL");
		try {
			printSocketInfo(sock);
			this.sock = sock;
			this.w = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
			this.r = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			sock.close();
		}
		
	}
	
	/**
	 * Esegue il lavoro del server elaborando i comandi dati dai peer.
	 */
	@Override
	public void run() {
		if(w==null || r==null) {
			try {
				sock.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		
		try {
			w.write(welcomeMessage, 0, welcomeMessage.length());
			w.newLine();
			w.flush();
			
			String buffer;
			while ((buffer = r.readLine()) != null) {
				if (buffer.equals("."))
					break;
				
				byte[] data = executeWork(buffer, this.sock.getSession());
				
				w.write(new String(data), 0, data.length);
				w.newLine();
				w.flush();
			}
			w.close();
			r.close();
			sock.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Esegue il lavoro del server.
	 * @param request	La stringa relativa alla richiesta attuale.
	 * @param session	La sessione SSL relativa a questa connessione.
	 * 
	 * @return Il risultato dell'elaborazione del server.
	 * 
	 * @throws SSLPeerUnverifiedException Se il peer non si è autenticato correttamente.
	 */
	private static byte[] executeWork(String request, SSLSession session) throws SSLPeerUnverifiedException {
		return RequestFactory.generateRequest(request, session).doAndGetResult().toSendFormat().getBytes();
	}
	
	/**
	 * Stampa le informazioni base relative alla socket e 
	 * alla sessione SSL attuale (a cui questo thread è stato assegnato).  
	 * @param s		La socket SSL relativa alla connessione attuale.
	 */

	private static void printSocketInfo(SSLSocket s) {
		System.out.println("Socket class: " + s.getClass());
		System.out.println("\tRemote address = " + s.getInetAddress().toString());
		System.out.println("\tRemote port = " + s.getPort());
		System.out.println("\tLocal socket address = " + s.getLocalSocketAddress().toString());
		System.out.println("\tLocal address = " + s.getLocalAddress().toString());
		System.out.println("\tLocal port = " + s.getLocalPort());
		System.out.println("\tNeed client authentication = " + s.getNeedClientAuth());
	    SSLSession ss = s.getSession();
	      try {
	         System.out.println("Session class: "+ss.getClass());
	         System.out.println("\tCipher suite = "+ss.getCipherSuite());
	         System.out.println("\tProtocol = "+ss.getProtocol());
	         System.out.println("\tPeerPrincipal = "+ss.getPeerPrincipal().getName());
	         System.out.println("\tLocalPrincipal = "+ss.getLocalPrincipal().getName());
	      } catch (Exception e) {
	         System.err.println(e.toString());
	      }
	}
}

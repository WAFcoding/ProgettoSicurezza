package connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public abstract class ClientConnection implements Closeable {
	
	private SSLSocket socket = null;
	private BufferedWriter writer = null;
	private BufferedReader reader = null;
	
	public boolean openConnection(String url, int servPort, String keystorePassword) throws Exception {
		try {
			String password = keystorePassword;
			System.setProperty("javax.net.ssl.trustStore", ClientConfig.getInstance().getProperty(ClientConfig.KEYSTORE_PATH) );
			System.setProperty("javax.net.ssl.trustStorePassword", password);

			SSLContext sc = SSLContext.getInstance("TLS");

			// Open the keystore, retrieve the private key, and certificate chain
			KeyStore ks = KeyStore.getInstance("jks");
			ks.load(new FileInputStream(ClientConfig.getInstance().getProperty(ClientConfig.KEYSTORE_PATH) ), null);

			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(ks, password.toCharArray());

			sc.init(kmf.getKeyManagers(), null, null);

			SSLSocketFactory f = (SSLSocketFactory) sc.getSocketFactory();

			socket = (SSLSocket) f.createSocket(url, servPort);
			socket.startHandshake();
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			//read welcome message
			reader.readLine();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public String sendCommand(String command) throws IOException {
		if(socket == null || writer==null || reader==null) {
			System.err.println("No connection opened");
			return null;
		}
		//send command
		writer.write(command, 0, command.length());
		writer.newLine();
		
		//read response
		String response = reader.readLine();
		
		return response;
	}
	
	@Override
	public void close() throws IOException{
		if(socket != null && writer!=null && reader!=null) {
			writer.close();
			reader.close();
			socket.close();
		}
	}
	
}

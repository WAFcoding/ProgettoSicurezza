package connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509KeyManager;

import util.KeyTool;

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
	
	public boolean openNeedClientAuthConnection(String url, int servPort, String clientAlias, String keystorePassword, String userPassword) throws Exception {
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
			

			KeyManager[] kms = kmf.getKeyManagers();
	        for (int i = 0; i < kms.length; i++) {
	            if (kms[i] instanceof X509KeyManager) {
	                kms[i] = new AliasForcingKeyManager((X509KeyManager) kms[i], clientAlias, password, ks);
	            }
	        }
			
			sc.init(kms, null, null);

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
	
	protected String sendCommand(String command) throws IOException {
		if(socket == null || writer==null || reader==null) {
			System.err.println("No connection opened");
			return null;
		}
		//send command
		writer.write(command, 0, command.length());
		writer.newLine();
		writer.flush();
		
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
	
	 /*
     * This wrapper class overwrites the default behavior of a X509KeyManager and
     * always render a specific certificate whose alias matches that provided in the constructor
     */
    private static class AliasForcingKeyManager implements X509KeyManager {

        X509KeyManager baseKM = null;
        String alias = null;
        KeyStore ks = null;
        String password = null;

        public AliasForcingKeyManager(X509KeyManager keyManager, String alias, String password, KeyStore ks) {
            baseKM = keyManager;
            this.alias = alias;
            this.ks = ks;
            this.password = password;
        }

        /*
         * Always render the specific alias provided in the constructor
         */
        public String chooseClientAlias(String[] keyType, Principal[] issuers, Socket socket) {
            return alias;
        }

        public String chooseServerAlias(String keyType, Principal[] issuers, Socket socket) {
            return baseKM.chooseServerAlias(keyType, issuers, socket);
        }

        public X509Certificate[] getCertificateChain(String alias) {
            return baseKM.getCertificateChain(alias);
        }

        public String[] getClientAliases(String keyType, Principal[] issuers) {
            return baseKM.getClientAliases(keyType, issuers);
        }

        public PrivateKey getPrivateKey(String alias) {
        	/*PrivateKey pkey = null;
        	try {
				pkey = KeyTool.getPrivateKey(ks, alias, password);
			} catch (NoSuchAlgorithmException | UnrecoverableEntryException
					| KeyStoreException e) {
				e.printStackTrace();
			}*/
            //return pkey;//
        	return baseKM.getPrivateKey(alias);
        }

        public String[] getServerAliases(String keyType, Principal[] issuers) {
            return baseKM.getServerAliases(keyType, issuers);
        }
    }
	
}

package test;

/**
 * SslSocketClient.java
 * Copyright (c) 2005 by Dr. Herong Yang
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class TestClient {
	
	private static final String path = "/home/giovanni/workspaceSII/ProgettoSicurezza/Server/test_user.jks";
	
	public static void main(String[] args) throws Exception{
		System.setProperty("javax.net.ssl.trustStore",path);
		System.setProperty("javax.net.ssl.trustStorePassword", "progettoSII");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintStream out = System.out;

		SSLContext sc = SSLContext.getInstance("TLS");
		
		// Open the keystore, retrieve the private key, and certificate chain
		KeyStore ks = KeyStore.getInstance("jks");
		ks.load(new FileInputStream(path), null);
		
		KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
		kmf.init(ks, "progettoSII".toCharArray());
		
		sc.init(kmf.getKeyManagers(), null, null);

		SSLSocketFactory f = (SSLSocketFactory) sc.getSocketFactory();

		try {
			SSLSocket c = (SSLSocket) f.createSocket("localhost", 8888);
			printSocketInfo(c);
			c.startHandshake();
			BufferedWriter w = new BufferedWriter(new OutputStreamWriter(
					c.getOutputStream()));
			BufferedReader r = new BufferedReader(new InputStreamReader(
					c.getInputStream()));
			String m = null;
			while ((m = r.readLine()) != null) {
				out.println(m);
				m = in.readLine();
				w.write(m, 0, m.length());
				w.newLine();
				w.flush();
			}
			w.close();
			r.close();
			c.close();
		} catch (IOException e) {
			System.err.println(e.toString());
		} 
	}

	private static void printSocketInfo(SSLSocket s) {
		System.out.println("Socket class: " + s.getClass());
		System.out.println("   Remote address = "
				+ s.getInetAddress().toString());
		System.out.println("   Remote port = " + s.getPort());
		System.out.println("   Local socket address = "
				+ s.getLocalSocketAddress().toString());
		System.out.println("   Local address = "
				+ s.getLocalAddress().toString());
		System.out.println("   Local port = " + s.getLocalPort());
		System.out.println("   Need client authentication = "
				+ s.getNeedClientAuth());
		SSLSession ss = s.getSession();
		System.out.println("   Cipher suite = " + ss.getCipherSuite());
		System.out.println("   Protocol = " + ss.getProtocol());
	}
}
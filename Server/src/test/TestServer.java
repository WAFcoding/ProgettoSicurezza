package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class TestServer {

	public static void main(String[] args) {

		try {

			SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			SSLSocket sslSock = (SSLSocket) factory.createSocket("mail.google.com", 443);

			// send HTTP get request
			BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(
					sslSock.getOutputStream(), "UTF8"));
			wr.write("GET /mail HTTP/1.1\r\nhost: mail.google.com\r\n\r\n");
			wr.flush();

			// read response
			BufferedReader rd = new BufferedReader(new InputStreamReader(sslSock.getInputStream()));
			String string = null;

			while ((string = rd.readLine()) != null) {
				System.out.println(string);
				System.out.flush();
			}

			rd.close();
			wr.close();
			// Close connection.
			sslSock.close();

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
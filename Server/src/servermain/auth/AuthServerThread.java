package servermain.auth;

import java.io.IOException;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;

import servermain.ServerThread;
import servermain.WorkerThread;

public class AuthServerThread extends ServerThread {

	protected AuthServerThread(SSLServerSocket s) {
		super(s);
	}

	@Override
	protected WorkerThread initWorker(SSLSocket sock) throws IOException {
		return new AuthWorkerThread(sock);
	}

}

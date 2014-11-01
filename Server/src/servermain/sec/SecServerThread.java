package servermain.sec;

import java.io.IOException;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;

import servermain.ServerThread;
import servermain.WorkerThread;

public class SecServerThread extends ServerThread {

	protected SecServerThread(SSLServerSocket s) {
		super(s);
	}
	
	@Override
	protected WorkerThread initWorker(SSLSocket sock) throws IOException {
		return new SecWorkerThread(sock);
	}

}

package servermain.auth;

import java.io.IOException;

import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import request.AuthRequestFactory;
import servermain.WorkerThread;

public class AuthWorkerThread extends WorkerThread {
	
	public AuthWorkerThread(SSLSocket sock) throws IOException {
		super(sock);
		super.welcomeMessage = "+OK SSL Registration Server Ready";
	}

	@Override
	protected byte[] executeWork(String request, SSLSession session) throws SSLPeerUnverifiedException {
		return AuthRequestFactory.generateRequest(request, session).doAndGetResult().toSendFormat().getBytes();
	}

}

package servermain.sec;

import java.io.IOException;

import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import request_man.factory.SecRequestFactory;
import servermain.WorkerThread;

public class SecWorkerThread extends WorkerThread {

	public SecWorkerThread(SSLSocket sock) throws IOException {
		super(sock);
		super.welcomeMessage = "+OK SSL Key Distribution Server Ready";
	}

	@Override
	protected byte[] executeWork(String request, SSLSession session) throws SSLPeerUnverifiedException {
		return SecRequestFactory.generateRequest(request, session).doAndGetResult().toSendFormat().getBytes();
	}

}

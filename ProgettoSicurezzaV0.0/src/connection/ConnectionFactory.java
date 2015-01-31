package connection;

public class ConnectionFactory {
	protected ConnectionFactory () {}
	
	public static RegistrationClient getRegistrationServerConnection(String url, String keystorePwd) throws Exception {
		RegistrationClient client = new RegistrationClient();
		client.openConnection(url, 8889, keystorePwd);
		return client;
	}
	
	public static KeyDistributionClient getKeyDistributionServerConnection(String url, String clientAlias, String keystorePwd) throws Exception {
		KeyDistributionClient client = new KeyDistributionClient();
		client.openNeedClientAuthConnection(url, 8888, clientAlias, keystorePwd);
		return client;
	}
}

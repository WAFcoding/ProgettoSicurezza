package connection;

public class ConnectionFactory {
	protected ConnectionFactory () {}
	
	public static RegistrationClient getRegistrationServerConnection(String url, String keystorePwd) throws Exception {
		RegistrationClient client = new RegistrationClient();
		client.openConnection(url, 8889, keystorePwd);
		return client;
	}
	
	public static KeyDistributionClient getKeyDistributionServerConnection(String url, String keystorePwd) throws Exception {
		KeyDistributionClient client = new KeyDistributionClient();
		client.openConnection(url, 8888, keystorePwd);
		return client;
	}
}

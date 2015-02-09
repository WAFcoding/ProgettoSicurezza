package connection;

public class ConnectionFactory {
	protected ConnectionFactory () {}
	
	public static RegistrationClient getRegistrationServerConnection(String url, String keystorePwd) throws Exception {
		RegistrationClient client = new RegistrationClient();
		client.openConnection(url, 8889, keystorePwd);
		return client;
	}
	
	public static KeyDistributionClient getKeyDistributionServerConnection(String url, String clientAlias, String keystorePwd, String userPassword) throws Exception {
		KeyDistributionClient client = new KeyDistributionClient();
		System.out.println("User involved:"+clientAlias.toLowerCase() + " url:"+url +" password:" + keystorePwd);
		client.openNeedClientAuthConnection(url, 8888, clientAlias.toLowerCase(), keystorePwd, userPassword);
		return client;
	}
}

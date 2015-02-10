package servermain;

import java.security.KeyStore;

import servermain.sec.SecServerController;

public class ServerMasterData {
	public static char[] passphrase;
	public static String keyStorePath;
	public static KeyStore ks;
	public static SecServerController srvController;
}

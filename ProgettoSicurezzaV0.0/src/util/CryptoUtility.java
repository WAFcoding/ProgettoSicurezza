package util;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.DSAKeyPairGenerator;
import org.bouncycastle.crypto.generators.DSAParametersGenerator;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.DSAKeyGenerationParameters;
import org.bouncycastle.crypto.params.DSAParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.signers.DSASigner;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Arrays;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class CryptoUtility {

	private static final String BouncyProvider = "BC";

	public enum CRYPTO_ALGO {
		AES, DES
	};

	public enum HASH_ALGO {
		MD5, SHA1, SHA256, SHA512
	};

	public static String toBase64(byte[] data) {
		return new BASE64Encoder().encode(data);
	}

	public static byte[] fromBase64(String data) throws IOException {
		return new BASE64Decoder().decodeBuffer(data);
	}

	private static String getCipher(CRYPTO_ALGO algo) {
		switch (algo) {
		case AES:
			return "AES";
		case DES:
			return "DES";
		default:
			return "AES";
		}
	}

	private static String getHash(HASH_ALGO algo) {
		switch (algo) {
		case MD5:
			return "MD5";
		case SHA1:
			return "SHA-1";
		case SHA256:
			return "SHA-256";
		case SHA512:
			return "SHA-512";
		default:
			return "SHA-1";
		}
	}

	private static byte[] preProcess(CRYPTO_ALGO algo, byte[] key) {
		switch (algo) {
		case AES:
			return Arrays.copyOf(key, 32);
		case DES:
			return Arrays.copyOf(key, 8);
		default:
			return key;
		}
	}

	private static String bytesToHex(byte[] b) {
		char hexDigit[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		StringBuffer buf = new StringBuffer();
		for (int j = 0; j < b.length; j++) {
			buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
			buf.append(hexDigit[b[j] & 0x0f]);
		}
		return buf.toString();
	}

	public static byte[] encrypt(CRYPTO_ALGO algo, String data, String key)
			throws Exception {
		return encrypt(algo, data.getBytes(), key.getBytes());
	}

	public static byte[] encrypt(CRYPTO_ALGO algo, byte[] data, String key)
			throws Exception {
		return encrypt(algo, data, key.getBytes());
	}

	public static byte[] encrypt(CRYPTO_ALGO algo, byte[] data, byte[] key)
			throws Exception {
		Security.addProvider(new BouncyCastleProvider());

		String algoType = getCipher(algo);
		key = preProcess(algo, key);

		Cipher c = Cipher.getInstance(algoType, BouncyProvider);
		c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, algoType));

		return c.doFinal(data);
	}

	public static byte[] decrypt(CRYPTO_ALGO algo, byte[] data, byte[] key)
			throws Exception {
		Security.addProvider(new BouncyCastleProvider());

		String algoType = getCipher(algo);
		key = preProcess(algo, key);

		Cipher c = Cipher.getInstance(algoType, BouncyProvider);
		c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, algoType));

		return c.doFinal(data);
	}

	public static String decrypt(CRYPTO_ALGO algo, byte[] data, String key)
			throws Exception {
		return new String(decrypt(algo, data, key.getBytes()));
	}

	public static String hash(HASH_ALGO algo, String data) throws Exception {
		return hash(algo, data.getBytes());
	}

	public static String hash(HASH_ALGO algo, byte[] data) throws Exception {
		Security.addProvider(new BouncyCastleProvider());

		String algoHash = getHash(algo);
		MessageDigest md = MessageDigest.getInstance(algoHash, BouncyProvider);

		return bytesToHex(md.digest(data));
	}

	public static AsymmetricCipherKeyPair getKeyPair() {
		SecureRandom    random = new SecureRandom();

        DSAParametersGenerator  pGen = new DSAParametersGenerator();
        pGen.init(512, 80, random);

        DSAParameters params = pGen.generateParameters();
		DSAKeyPairGenerator dsaKeyGen = new DSAKeyPairGenerator();
		DSAKeyGenerationParameters genParam = new DSAKeyGenerationParameters(random, params);

		dsaKeyGen.init(genParam);

		return dsaKeyGen.generateKeyPair();
	}
	
	public static BigInteger[] sign(AsymmetricKeyParameter privateKey, String message) throws Exception {
		SecureRandom    random = new SecureRandom();
		DSASigner signer = new DSASigner();
		
		ParametersWithRandom param = new ParametersWithRandom(privateKey, random);

		signer.init(true, param);
		return signer.generateSignature(hash(HASH_ALGO.SHA1,message).getBytes());
	}
	
	public static boolean verify(AsymmetricKeyParameter publicKey, BigInteger[] signature, String message) throws Exception {
		DSASigner signer = new DSASigner();
		signer.init(false, publicKey);
		return signer.verifySignature(hash(HASH_ALGO.SHA1,message).getBytes(), signature[0], signature[1]);
	}

}

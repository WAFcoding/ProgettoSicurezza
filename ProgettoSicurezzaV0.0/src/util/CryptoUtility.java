package util;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;

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

/**
 * Classe di utilità per l'utilizzo degli algoritmi di crittografia.
 * 
 * @author Giovanni Rossi
 *
 */
public class CryptoUtility {

	/**
	 *	Provide per la libreria Bouncy Castle. 
	 */
	private static final String BouncyProvider = "BC";

	/**
	 * Permette di stabilire il tipo di algoritmo di cifratura da usare.
	 */
	public enum CRYPTO_ALGO {
		AES, DES
	};

	/**
	 * Permette di stabilire il tipo di algoritmo di hashing da usare.
	 */
	public enum HASH_ALGO {
		MD5, SHA1, SHA256, SHA512
	};

	/**
	 * Converte i dati in input in Base64.
	 * @param data	I dati da convertire.
	 * 
	 * @return I dati in formato Base64.	
	 */
	public static String toBase64(byte[] data) {
		return new BASE64Encoder().encode(data);
	}

	/**
	 * Decodifica i dati dal Base64.
	 * @param data	I dati da decodificare.
	 * 
	 * @return	I dati decodificati.
	 * 
	 * @throws IOException
	 */
	public static byte[] fromBase64(String data) throws IOException {
		return new BASE64Decoder().decodeBuffer(data);
	}

	/**
	 * Restituisce la stringa con il tipo di algoritmo di cifratura da usare.
	 * @param algo	Il tipo di algoritmo.
	 * 
	 * @return	La stringa corrispondente al tipo di algoritmo da usare.
	 */
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

	/**
	 * Restituisce la stringa con il tipo di algoritmo di hashing da usare.
	 * @param algo	Il tipo di algoritmo.
	 * 
	 * @return	La stringa corrispondente al tipo di algoritmo da usare.
	 */
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

	/**
	 * Pre processa la chiave in input per adattarla all'algoritmo scelto.
	 * @param algo	L'algoritmo di cifratura scelto.
	 * @param key	La chiave da manipolare.
	 * 
	 * @return La chiave processata.
	 */
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

	/**
	 * Converte i byte in ingresso in una stringa esadecimale.
	 * @param b		I byte da convertire.
	 * 
	 * @return La stringa convertita in formato esadecimale.
	 */
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

	/**
	 * Cifra la stringa in input con la chiave e l'algoritmo scelto.
	 * @param algo	L'algoritmo di cifratura.
	 * @param data	La stringa da cifrare.
	 * @param key	La chiave da usare nel processo.
	 * 
	 * @return I dati cifrati.
	 * 
	 * @throws Exception
	 */
	public static byte[] encrypt(CRYPTO_ALGO algo, String data, String key)
			throws Exception {
		return encrypt(algo, data.getBytes(), key.getBytes());
	}

	/**
	 * Cifra i dati in input con la chiave e l'algoritmo scelto.
	 * @param algo	L'algoritmo di cifratura.
	 * @param data	I dati da cifrare.
	 * @param key	La chiave da usare nel processo.
	 * 
	 * @return I dati cifrati.
	 * 
	 * @throws Exception
	 */
	public static byte[] encrypt(CRYPTO_ALGO algo, byte[] data, String key)
			throws Exception {
		return encrypt(algo, data, key.getBytes());
	}

	/**
	 * Cifra i dati in input con la chiave e l'algoritmo scelto.
	 * @param algo	L'algoritmo di cifratura.
	 * @param data	I dati da cifrare.
	 * @param key	La chiave da usare nel processo.
	 * 
	 * @return I dati cifrati.
	 * 
	 * @throws Exception
	 */
	public static byte[] encrypt(CRYPTO_ALGO algo, byte[] data, byte[] key)
			throws Exception {
		Security.addProvider(new BouncyCastleProvider());

		String algoType = getCipher(algo);
		key = preProcess(algo, key);

		Cipher c = Cipher.getInstance(algoType, BouncyProvider);
		c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, algoType));

		return c.doFinal(data);
	}

	/**
	 * Decifra i dati in input con la chiave e l'algoritmo scelto.
	 * @param algo	L'algoritmo di decifratura.
	 * @param data	I dati da decifrare.
	 * @param key	La chiave da usare nel processo.
	 * 
	 * @return I dati decifrati.
	 * 
	 * @throws Exception
	 */
	public static byte[] decrypt(CRYPTO_ALGO algo, byte[] data, byte[] key)
			throws Exception {
		Security.addProvider(new BouncyCastleProvider());

		String algoType = getCipher(algo);
		key = preProcess(algo, key);

		Cipher c = Cipher.getInstance(algoType, BouncyProvider);
		c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, algoType));

		return c.doFinal(data);
	}

	/**
	 * Decifra i dati in input con la chiave e l'algoritmo scelto.
	 * @param algo	L'algoritmo di decifratura.
	 * @param data	I dati da decifrare.
	 * @param key	La chiave da usare nel processo.
	 * 
	 * @return I dati decifrati.
	 * 
	 * @throws Exception
	 */
	public static String decrypt(CRYPTO_ALGO algo, byte[] data, String key)
			throws Exception {
		return new String(decrypt(algo, data, key.getBytes()));
	}

	/**
	 * Calcola l'hash della stringa in input seguendo l'algoritmo indicato.
	 * @param algo	L'algoritmo di hashing da usare.
	 * @param data	La stringa di cui calcolare l'hash.
	 * 
	 * @return La stringa rappresentante l'hash in formato esadecimale.
	 * 
	 * @throws Exception
	 */
	public static String hash(HASH_ALGO algo, String data) throws Exception {
		return hash(algo, data.getBytes());
	}

	/**
	 * Calcola l'hash dei dati in input seguendo l'algoritmo indicato.
	 * @param algo	L'algoritmo di hashing da usare.
	 * @param data	I dati di cui calcolare l'hash.
	 * 
	 * @return La stringa rappresentante l'hash in formato esadecimale.
	 * 
	 * @throws Exception
	 */
	public static String hash(HASH_ALGO algo, byte[] data) throws Exception {
		Security.addProvider(new BouncyCastleProvider());

		String algoHash = getHash(algo);
		MessageDigest md = MessageDigest.getInstance(algoHash, BouncyProvider);

		return bytesToHex(md.digest(data));
	}

	/**
	 * Genera una coppia di chiavi asimmetriche RSA.
	 * 
	 * @return La coppia di chiavi.
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 */
	public static KeyPair genKeyPairRSA() throws NoSuchAlgorithmException,
			NoSuchProviderException {
		Security.addProvider(new BouncyCastleProvider());

		KeyPairGenerator g = KeyPairGenerator.getInstance("RSA", BouncyProvider);

		g.initialize(1024, new SecureRandom());

		return g.generateKeyPair();
	}
	
	/**
	 * Firma i dati passati in input usando l'algoritmo RSA ("SHA1withRSA").
	 * @param pkey	La chiave privata.
	 * @param data	I dati da firmare.
	 * 
	 * @return I dati firmati con la chiave privata.
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	public static byte[] signRSA(PrivateKey pkey, String data) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
		Security.addProvider(new BouncyCastleProvider());
		Signature sign = Signature.getInstance("SHA1withRSA", BouncyProvider);
		sign.initSign(pkey, new SecureRandom());
		sign.update(data.getBytes());
		return sign.sign();
	}
	
	/**
	 * Verifica la firma usando RSA ("SHA1withRSA").
	 * @param pkey		La chiave pubblica.
	 * @param data		I dati firmati.
	 * @param message	Il messaggio da verificare.
	 * 
	 * @return true se la stringa non ha subito manipolazione e false altrimenti.
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	public static boolean verifyRSA(PublicKey pkey, byte[] data, String message) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
		Security.addProvider(new BouncyCastleProvider());
		Signature sign = Signature.getInstance("SHA1withRSA", BouncyProvider);
		sign.initVerify(pkey);
		sign.update(message.getBytes());
		return sign.verify(data);
	}

	/**
	 * Genera una coppia di chiavi DSA (Digital Signature Algorithm).
	 * 
	 * @return La coppia di chiavi.
	 */
	public static AsymmetricCipherKeyPair genDSAKeyPair() {
		SecureRandom random = new SecureRandom();

		DSAParametersGenerator pGen = new DSAParametersGenerator();
		pGen.init(512, 80, random);

		DSAParameters params = pGen.generateParameters();
		DSAKeyPairGenerator dsaKeyGen = new DSAKeyPairGenerator();
		DSAKeyGenerationParameters genParam = new DSAKeyGenerationParameters(
				random, params);

		dsaKeyGen.init(genParam);

		return dsaKeyGen.generateKeyPair();
	}

	/**
	 * Firma il messaggio in input con DSA.
	 * @param privateKey	La chiave privata.
	 * @param message		Il messaggio da firmare.
	 * 
	 * @return	La firma dei dati.
	 * 
	 * @throws Exception
	 */
	public static BigInteger[] signDSA(AsymmetricKeyParameter privateKey,
			String message) throws Exception {
		SecureRandom random = new SecureRandom();
		DSASigner signer = new DSASigner();

		ParametersWithRandom param = new ParametersWithRandom(privateKey,
				random);

		signer.init(true, param);
		return signer.generateSignature(hash(HASH_ALGO.SHA1, message)
				.getBytes());
	}

	/**
	 * Verifica la firma del messaggio in input usando DSA.
	 * @param publicKey		La chiave pubblica.
	 * @param signature		La firma del messaggio.
	 * @param message		Il messaggio da verificare.
	 * 
	 * @return true se il messaggio non ha subito manipolazioni e false altrimenti.
	 * @throws Exception
	 */
	public static boolean verifyDSA(AsymmetricKeyParameter publicKey,
			BigInteger[] signature, String message) throws Exception {
		DSASigner signer = new DSASigner();
		signer.init(false, publicKey);
		return signer.verifySignature(hash(HASH_ALGO.SHA1, message).getBytes(),
				signature[0], signature[1]);
	}

}

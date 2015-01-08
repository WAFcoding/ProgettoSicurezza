package util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
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
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.security.spec.ECGenParameterSpec;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.x509.X509V3CertificateGenerator;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Classe di utilità per l'utilizzo degli algoritmi di crittografia.
 * 
 * @author Giovanni Rossi
 * 
 */
@SuppressWarnings("deprecation")
public class CryptoUtility {

	/**
	 * Provide per la libreria Bouncy Castle.
	 */
	private static final String BouncyProvider = "BC";

	/**
	 * Permette di stabilire il tipo di algoritmo di cifratura da usare.
	 */
	public enum CRYPTO_ALGO {
		AES, DES, IDEA, TWOFISH_256, RIJNDAEL, RC6
	};

	/**
	 * Permette di stabilire il tipo di algoritmo di hashing da usare.
	 */
	public enum HASH_ALGO {
		MD5, SHA1, SHA256, SHA512
	};
	
	public enum ASYMMCRYPTO_ALGO {
		RSA
	};
	
	public enum CERT_SIGALGO {
		SHA1withRSA, SHA256withRSA, SHA1withDSA, SHA1withECDSA, SHA256withECDSA
	}

	/**
	 * Converte i dati in input in Base64.
	 * 
	 * @param data
	 *            I dati da convertire.
	 * 
	 * @return I dati in formato Base64.
	 */
	public static String toBase64(byte[] data) {
		return new BASE64Encoder().encode(data);
	}

	/**
	 * Decodifica i dati dal Base64.
	 * 
	 * @param data
	 *            I dati da decodificare.
	 * 
	 * @return I dati decodificati.
	 * 
	 * @throws IOException
	 */
	public static byte[] fromBase64(String data) throws IOException {
		return new BASE64Decoder().decodeBuffer(data);
	}
	
	/**
	 * Decodifica i dati dal Base64.
	 * 
	 * @param data
	 *            I dati da decodificare.
	 * 
	 * @return I dati decodificati.
	 * 
	 * @throws IOException
	 */
	public static byte[] fromBase64(byte[] data) throws IOException {
		return new BASE64Decoder().decodeBuffer(new String(data));
	}

	/**
	 * Restituisce la stringa con il tipo di algoritmo di cifratura da usare.
	 * 
	 * @param algo
	 *            Il tipo di algoritmo.
	 * 
	 * @return La stringa corrispondente al tipo di algoritmo da usare.
	 */
	private static String getCipher(CRYPTO_ALGO algo) {
		//IDEA, TWOFISH_256, RIJNDAEL, RC5_64, RC6 
		switch (algo) {
		case AES:
			return "AES/ECB/PKCS5Padding";
		case DES:
			return "DES/ECB/PKCS5Padding";
		case IDEA:
			return "IDEA";
		case TWOFISH_256:
			return "TWOFISH";
		case RIJNDAEL:
			return "RIJNDAEL";
		case RC6:
			return "RC6";
		default:
			return "AES/ECB/PKCS5Padding";
		}
	}

	/**
	 * Restituisce la stringa con il tipo di algoritmo di hashing da usare.
	 * 
	 * @param algo
	 *            Il tipo di algoritmo.
	 * 
	 * @return La stringa corrispondente al tipo di algoritmo da usare.
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
	 * Restituisce la stringa con il tipo di algoritmo di cifratura asimmetrica da usare.
	 * 
	 * @param algo
	 *            Il tipo di algoritmo.
	 * 
	 * @return La stringa corrispondente al tipo di algoritmo da usare.
	 */
	private static String getAsymmCipher(ASYMMCRYPTO_ALGO algo) {
		switch (algo) {
		case RSA:
			return "RSA";
		default:
			return "RSA";
		}
	}
	
	/**
	 * Restituisce la stringa con il tipo di algoritmo di firma da usare per un certificato.
	 * 
	 * @param algo
	 *            Il tipo di algoritmo.
	 * 
	 * @return La stringa corrispondente al tipo di algoritmo da usare.
	 */
	private static String getCertSigAlgo(CERT_SIGALGO algo) {
		switch (algo) {
		case SHA256withRSA:
			return "SHA256WithRSA";
		case SHA1withRSA:
			return "SHA1WITHRSA";
		case SHA1withDSA:
			return "SHA1WITHDSA";
		case SHA256withECDSA:
			return "SHA256WITHECDSA";
		case SHA1withECDSA:
			return "SHA1withECDSA";
		default:
			return "SHA1WITHRSA";
		}
	}

	/**
	 * Pre processa la chiave in input per adattarla all'algoritmo scelto.
	 * 
	 * @param algo
	 *            L'algoritmo di cifratura scelto.
	 * @param key
	 *            La chiave da manipolare.
	 * 
	 * @return La chiave processata.
	 */
	private static byte[] preProcess(CRYPTO_ALGO algo, byte[] key) {
		switch (algo) {
		case AES:
			return Arrays.copyOf(key, 32);
		case DES:
			return Arrays.copyOf(key, 8);
		case IDEA:
			return Arrays.copyOf(key, 16);
		case TWOFISH_256:
			return Arrays.copyOf(key, 32);
		case RIJNDAEL:
			return Arrays.copyOf(key, 32);
		case RC6:
			return Arrays.copyOf(key, 32);
			
		default:
			return key;
		}
	}

	/**
	 * Converte i byte in ingresso in una stringa esadecimale.
	 * 
	 * @param b
	 *            I byte da convertire.
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
	 * 
	 * @param algo
	 *            L'algoritmo di cifratura.
	 * @param data
	 *            La stringa da cifrare.
	 * @param key
	 *            La chiave da usare nel processo.
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
	 * 
	 * @param algo
	 *            L'algoritmo di cifratura.
	 * @param data
	 *            I dati da cifrare.
	 * @param key
	 *            La chiave da usare nel processo.
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
	 * 
	 * @param algo
	 *            L'algoritmo di cifratura.
	 * @param data
	 *            I dati da cifrare.
	 * @param key
	 *            La chiave da usare nel processo.
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
	 * Cripta/Firma i dati in input usando un cifrario asimmetrico.
	 * @param algo	L'algoritmo a chiave asimmetrica da usare.
	 * @param data	I dati da cifrare o firmare.
	 * @param key	La chiave pubblica(cifratura) / privata(firma) da usare.
	 * 
	 * @return	I dati cifrati/firmati.
	 * 
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws NoSuchPaddingException
	 */
	public static byte[] asymm_encrypt(ASYMMCRYPTO_ALGO algo, byte[] data, Key key) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException {
		Security.addProvider(new BouncyCastleProvider());

		String algoType = getAsymmCipher(algo);

		Cipher c = Cipher.getInstance(algoType, BouncyProvider);
		c.init(Cipher.ENCRYPT_MODE, key);
		

		return c.doFinal(data);
	}
	
	/**
	 * Decripta i dati in input usando un cifrario asimmetrico.
	 * @param algo	L'algoritmo a chiave asimmetrica da usare.
	 * @param data	I dati da decifrare.
	 * @param key	La chiave pubblica(verifica) / privata(decifratura) da usare.
	 * 
	 * @return	I dati decifrati.
	 * 
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws NoSuchPaddingException
	 */
	public static byte[] asymm_decrypt(ASYMMCRYPTO_ALGO algo, byte[] data, Key key) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException {
		Security.addProvider(new BouncyCastleProvider());

		String algoType = getAsymmCipher(algo);

		Cipher c = Cipher.getInstance(algoType, BouncyProvider);
		c.init(Cipher.DECRYPT_MODE, key);

		return c.doFinal(data);
	}

	/**
	 * Decifra i dati in input con la chiave e l'algoritmo scelto.
	 * 
	 * @param algo
	 *            L'algoritmo di decifratura.
	 * @param data
	 *            I dati da decifrare.
	 * @param key
	 *            La chiave da usare nel processo.
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
	 * 
	 * @param algo
	 *            L'algoritmo di decifratura.
	 * @param data
	 *            I dati da decifrare.
	 * @param key
	 *            La chiave da usare nel processo.
	 * 
	 * @return I dati decifrati.
	 * 
	 * @throws Exception
	 */
	public static String decrypt(CRYPTO_ALGO algo, byte[] data, String key)
			throws Exception {
		return new String(decrypt(algo, data, key.getBytes()));
	}
	
	public static byte[] decryptToByte(CRYPTO_ALGO algo, byte[] data, String key)
			throws Exception{
		return decrypt(algo, data, key.getBytes());
	}

	/**
	 * Calcola l'hash della stringa in input seguendo l'algoritmo indicato.
	 * 
	 * @param algo
	 *            L'algoritmo di hashing da usare.
	 * @param data
	 *            La stringa di cui calcolare l'hash.
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
	 * 
	 * @param algo
	 *            L'algoritmo di hashing da usare.
	 * @param data
	 *            I dati di cui calcolare l'hash.
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
	 * Genera una coppia di chiavi asimmetriche ECDSA.
	 * 
	 * @return La coppia di chiavi.
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws InvalidAlgorithmParameterException 
	 */
	public static KeyPair getKeyPairECDSA() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
		Security.addProvider(new BouncyCastleProvider());

		ECGenParameterSpec ecGenSpec = new ECGenParameterSpec("prime192v1");
		KeyPairGenerator g = KeyPairGenerator.getInstance("ECDSA", BouncyProvider);

		g.initialize(ecGenSpec, new SecureRandom());
		
		return g.generateKeyPair();
	}

	/**
	 * Firma i dati passati in input usando l'algoritmo RSA ("SHA1withRSA").
	 * 
	 * @param pkey
	 *            La chiave privata.
	 * @param data
	 *            I dati da firmare.
	 * 
	 * @return I dati firmati con la chiave privata.
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	public static byte[] signRSA(PrivateKey pkey, String data)
			throws NoSuchAlgorithmException, NoSuchProviderException,
			InvalidKeyException, SignatureException {
		Security.addProvider(new BouncyCastleProvider());
		Signature sign = Signature.getInstance("SHA1withRSA", BouncyProvider);
		sign.initSign(pkey, new SecureRandom());
		sign.update(data.getBytes());
		return sign.sign();
	}

	/**
	 * Verifica la firma usando RSA ("SHA1withRSA").
	 * 
	 * @param pkey
	 *            La chiave pubblica.
	 * @param data
	 *            I dati firmati.
	 * @param message
	 *            Il messaggio da verificare.
	 * 
	 * @return true se la stringa non ha subito manipolazione e false
	 *         altrimenti.
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	public static boolean verifyRSA(PublicKey pkey, byte[] data, String message)
			throws NoSuchAlgorithmException, NoSuchProviderException,
			InvalidKeyException, SignatureException {
		Security.addProvider(new BouncyCastleProvider());
		Signature sign = Signature.getInstance("SHA1withRSA", BouncyProvider);
		sign.initVerify(pkey);
		sign.update(message.getBytes());
		return sign.verify(data);
	}
	
	/**
	 * Genera un certificato X.509 con i parametri inseriti dall'utente.
	 * 
	 * @param algo
	 * 			  Il tipo di algoritmo scelto per la firma del certificato
	 * @param pubkey
	 *            La chiave pubblica dell'utente.
	 * @param privkey
	 * 			  La chiave privata dell'utente. Deve essere conforme all'algoritmo di firma scelto.
	 * @param name
	 *            Il nome dell'utente.
	 * @param surname
	 *            Il cognome dell'utente.
	 * @param country_code
	 *            Il codice del Paese.
	 * @param organization
	 *            L'organizzazione.
	 * @param locality
	 *            La località.
	 * @param state
	 *            Il nome del paese per esteso.
	 * @param email
	 *            L'email.
	 * @param notBefore
	 * 			  La data di scadenza del certificato.
	 * @param notAfter
	 * 			  La data di inizio di validità del certificato.
	 * 
	 * @return Un certificato X.509 valido.
	 * 
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 * @throws SignatureException
	 * @throws CertificateException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws OperatorCreationException
	 */
	public static X509Certificate createX509Certificate2 (CERT_SIGALGO algo, PublicKey pubkey, PrivateKey privkey, String UID,  String name, String surname, String country_code, String organization,
			String locality, String state, String email, Date notBefore, Date notAfter) throws InvalidKeyException, SecurityException, 
			SignatureException, CertificateException, NoSuchAlgorithmException, NoSuchProviderException, OperatorCreationException {
		
		Security.addProvider(new BouncyCastleProvider());
		
	    X500NameBuilder builder=new X500NameBuilder(BCStyle.INSTANCE);
	    
		if (UID==null || name == null || surname==null || UID.isEmpty() || name.isEmpty() || surname.isEmpty()) {
			System.err.println("Missing Name/Surname--Cannot create X.509 Certificate\n");
			return null;
		}
		
		builder.addRDN(BCStyle.UID, UID);
		builder.addRDN(BCStyle.NAME, name);
		builder.addRDN(BCStyle.SURNAME, surname);

		if (country_code != null && !country_code.isEmpty()) {
			builder.addRDN(BCStyle.C, country_code);
		}

		if (organization != null && !organization.isEmpty()) {
			builder.addRDN(BCStyle.O, organization);
		}

		if (locality != null && !locality.isEmpty()) {
			builder.addRDN(BCStyle.L, locality);
		}

		if (state != null && !state.isEmpty()) {
			builder.addRDN(BCStyle.ST, state);
		}

		if (email != null && !email.isEmpty()) {
			builder.addRDN(BCStyle.E, email);
		}
	   
	    BigInteger serial=BigInteger.valueOf(System.currentTimeMillis());
	    X509v3CertificateBuilder certGen=new JcaX509v3CertificateBuilder(builder.build(),serial,notBefore,notAfter,builder.build(), pubkey);
	    ContentSigner sigGen=new JcaContentSignerBuilder(CryptoUtility.getCertSigAlgo(algo)).setProvider(BouncyProvider).build(privkey);
	    X509Certificate cert=new JcaX509CertificateConverter().setProvider(BouncyProvider).getCertificate(certGen.build(sigGen));
   
	    return cert;
	}
	
	/**
	 * Verifica il certificato passato in input con la chiave pubblica data.
	 * 
	 * @param cert		Il certificato da verificare.
	 * @param pubkey	La chiave pubblica da usare nella verifica.
	 * @return	true se il certificato è valido e false altrimenti.
	 */
	public static boolean verifyCertificate(X509Certificate cert, PublicKey pubkey) {
		if(cert==null || pubkey==null)
			return false;
		try {
			cert.checkValidity(new Date());
		} catch (CertificateExpiredException | CertificateNotYetValidException e) {
			e.printStackTrace();
			return false;
		}
		
		Security.addProvider(new BouncyCastleProvider());
		
		try {
			cert.verify(pubkey, CryptoUtility.BouncyProvider);
		} catch (InvalidKeyException | CertificateException
				| NoSuchAlgorithmException | NoSuchProviderException
				| SignatureException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	/**
	 * Genera un certificato X.509 con i parametri inseriti dall'utente.
	 * @deprecated
	 * @see {@link CryptoUtility#createX509Certificate2(KeyPair, String, String, String, String, String, String, String, Date, Date)}
	 * 
	 * @param kp
	 *            La coppia di chiavi RSA.
	 * @param name
	 *            Il nome dell'utente.
	 * @param surname
	 *            Il cognome dell'utente.
	 * @param country_code
	 *            Il codice del Paese.
	 * @param organization
	 *            L'organizzazione.
	 * @param locality
	 *            La località.
	 * @param state
	 *            Il nome del paese per esteso.
	 * @param email
	 *            L'email.
	 * @param notBefore
	 * 			  La data di scadenza del certificato.
	 * @param notAfter
	 * 			  La data di inizio di validità del certificato.
	 * 
	 * @return Un certificato X.509 valido.
	 * 
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 * @throws SignatureException
	 * @throws CertificateException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 */
	public static Certificate createX509Certificate(KeyPair kp, String name,
			String surname, String country_code, String organization,
			String locality, String state, String email, Date notBefore, Date notAfter)
			throws InvalidKeyException, SecurityException, SignatureException,
			CertificateException, NoSuchAlgorithmException,
			NoSuchProviderException {
		Security.addProvider(new BouncyCastleProvider());

		PublicKey pubKey = kp.getPublic();
		PrivateKey privKey = kp.getPrivate();

		//
		// distinguished name table.
		//
		Hashtable<ASN1ObjectIdentifier, String> attrs = new Hashtable<ASN1ObjectIdentifier, String>();
		Vector<ASN1ObjectIdentifier> order = new Vector<ASN1ObjectIdentifier>();

		if (name != null && !name.isEmpty()) {
			attrs.put(BCStyle.NAME, name);
			order.addElement(BCStyle.NAME);
		}

		if (surname != null && !surname.isEmpty()) {
			attrs.put(BCStyle.SURNAME, surname);
			order.addElement(BCStyle.SURNAME);
		}

		if (country_code != null && !country_code.isEmpty()) {
			attrs.put(BCStyle.C, country_code);
			order.addElement(BCStyle.C);
		}

		if (organization != null && !organization.isEmpty()) {
			attrs.put(BCStyle.O, organization);
			order.addElement(BCStyle.O);
		}

		if (locality != null && !locality.isEmpty()) {
			attrs.put(BCStyle.L, locality);
			order.addElement(BCStyle.L);
		}

		if (state != null && !state.isEmpty()) {
			attrs.put(BCStyle.ST, state);
			order.addElement(BCStyle.ST);
		}

		if (email != null && !email.isEmpty()) {
			attrs.put(BCStyle.E, email);
			order.addElement(BCStyle.E);
		}

		//
		// create the certificate - version 3
		//
		
		X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();

		certGen.setSerialNumber(BigInteger.valueOf(1));
		certGen.setIssuerDN(new X509Principal(order, attrs));
		certGen.setNotBefore(notBefore);
		certGen.setNotAfter(notAfter);
		certGen.setSubjectDN(new X509Principal(order, attrs));
		certGen.setPublicKey(pubKey);
		certGen.setSignatureAlgorithm("SHA1withRSA");
		
		
		X509Certificate cert = certGen.generate(privKey);

		cert.checkValidity(new Date());

		cert.verify(pubKey);

		ByteArrayInputStream bIn = new ByteArrayInputStream(cert.getEncoded());
		CertificateFactory fact = CertificateFactory.getInstance("X.509",
				BouncyProvider);

		cert = (X509Certificate) fact.generateCertificate(bIn);

		return cert;
	}

	/**
	 * Genera una coppia di chiavi DSA (Digital Signature Algorithm).
	 * 
	 * @return La coppia di chiavi.
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 */
	public static KeyPair genKeyPairDSA() throws NoSuchAlgorithmException,
			NoSuchProviderException {
		Security.addProvider(new BouncyCastleProvider());

		KeyPairGenerator g = KeyPairGenerator
				.getInstance("DSA", BouncyProvider);

		g.initialize(1024, new SecureRandom());

		return g.generateKeyPair();
	}

	/**
	 * Firma il messaggio in input con DSA.
	 * 
	 * @param key
	 *            La chiave privata.
	 * @param data
	 *            Il messaggio da firmare.
	 * 
	 * @return La firma dei dati.
	 * 
	 * @throws Exception
	 */
	public static byte[] signDSA(PrivateKey key, String data) throws Exception {
		Signature signer = Signature.getInstance("SHA1withDSA");
		signer.initSign(key);
		signer.update(data.getBytes());
		return (signer.sign());
	}

	/**
	 * Verifica la firma del messaggio in input usando DSA.
	 * 
	 * @param publicKey
	 *            La chiave pubblica.
	 * @param signed
	 *            La firma del messaggio.
	 * @param message
	 *            Il messaggio da verificare.
	 * 
	 * @return true se il messaggio non ha subito manipolazioni e false
	 *         altrimenti.
	 * @throws Exception
	 */
	public static boolean verifyDSA(PublicKey publicKey, byte[] signed,
			String message) throws Exception {
		Signature signer = Signature.getInstance("SHA1withDSA");
		signer.initVerify(publicKey);
		signer.update(message.getBytes());
		return (signer.verify(signed));
	}
}

package test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyPair;

import javax.imageio.ImageIO;

import magick.MagickImage;

import org.apache.commons.io.IOUtils;

import util.CryptoUtility;
import util.ImagePHash;
import util.MagickUtility;
import util.QRCode;

public class TestImageHash {

	public static void main(String[] args) throws Exception {
		
		ImagePHash hash = new ImagePHash(42, 5);

		String originale = hash.getHash(new FileInputStream("/home/pasquale/ProgettoSicurezza/pasquale/output/prova1.png"));
		QRCode qr1= new QRCode();
		qr1.writeQRCode(originale, 100, 100);
		qr1.saveQRCodeToFile("/home/pasquale/ProgettoSicurezza/pasquale/output/qrorigin.png");
		String q1= QRCode.getTextFromQrCode("/home/pasquale/ProgettoSicurezza/pasquale/output/qrorigin.png");

		KeyPair key = CryptoUtility.genKeyPairRSA();;
		String public_key= "";
		String private_key= "";
		public_key= CryptoUtility.toBase64(key.getPublic().getEncoded()).replace("\n", "");
		private_key= CryptoUtility.toBase64(key.getPrivate().getEncoded()).replace("\n", "");

		//String origin_crypt= new String(CryptoUtility.encrypt(CRYPTO_ALGO.AES, originale.getBytes(), private_key));
		//String origin_decr= new String(CryptoUtility.decrypt(CRYPTO_ALGO.AES, origin_crypt.getBytes(), public_key));
		
		byte[] origin_crypt= CryptoUtility.signRSA(key.getPrivate(), originale);
		String s= CryptoUtility.toBase64(origin_crypt);
		QRCode qr= new QRCode();
		qr.writeQRCode(s, 100, 100);
		qr.saveQRCodeToFile("/home/pasquale/ProgettoSicurezza/pasquale/output/qr123.png");
		String q= QRCode.getTextFromQrCode("/home/pasquale/ProgettoSicurezza/pasquale/output/qr123.png");
		boolean correct= CryptoUtility.verifyRSA(key.getPublic(), CryptoUtility.fromBase64(q), q1);

		if(correct) System.out.println("ok");
		else System.out.println("not ok");
		
		//String disturbo = hash.getHash(new FileInputStream("/home/giovanni/Scrivania/prova3Disturbata.png"));
		//String disturbo2 = hash.getHash(new FileInputStream("/home/giovanni/Scrivania/prova3Disturbata2.png"));



		System.out.println("originale - originale: " + hash.matchPercentage(originale, originale) + "\n" + originale);
//		System.out.println("originale - disturbo: " + hash.matchPercentage(originale, disturbo));
//		System.out.println("originale - disturbo1: " + hash.matchPercentage(originale, disturbo2));
		

	}
}

package test;

import java.io.FileInputStream;
import java.math.BigInteger;
import java.util.zip.CRC32;

import util.CryptoUtility;
import util.ImagePHash;

public class TestImageHash {

	public static void main(String[] args) throws Exception {
		
		/*ImagePHash hash = new ImagePHash(42, 5);

		String originale = hash.getHash(new FileInputStream("/home/giovanni/Scrivania/prova3Disturbata1.png"));
		String disturbo = hash.getHash(new FileInputStream("/home/giovanni/Scrivania/prova3Disturbata.png"));
		String disturbo2 = hash.getHash(new FileInputStream("/home/giovanni/Scrivania/prova3Disturbata2.png"));



		System.out.println("originale - originale: " + hash.matchPercentage(originale, originale));
		System.out.println("originale - disturbo: " + hash.matchPercentage(originale, disturbo));
		System.out.println("originale - disturbo1: " + hash.matchPercentage(originale, disturbo2));*/
		
		String original= "1234567890qwertyuiopasdfghjkl";
		String h= "1234567891qwertyuiopasdfghjkl";
		long hash= 0;
		CRC32 crc= new CRC32();
		crc.update(original.getBytes());
		long orig= crc.getValue();
		CRC32 crc2= new CRC32();
		crc2.update(h.getBytes());
		hash= crc2.getValue();
		
		System.out.println(Long.toBinaryString(orig));
		System.out.println(Long.toBinaryString(hash));
		System.out.println("origin crc= " + orig + ", hash= " + hash);
		
		

	}
}

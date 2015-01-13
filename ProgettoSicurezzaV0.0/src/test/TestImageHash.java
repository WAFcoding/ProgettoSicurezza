package test;

import java.io.FileInputStream;
import java.math.BigInteger;

import util.ImagePHash;

public class TestImageHash {

	public static void main(String[] args)  {
		ImagePHash hash = new ImagePHash(32,10);
		
		try{
			String hash1 = hash.getHash(new FileInputStream("/home/giovanni/Scaricati/opencv-2.4.9/samples/cpp/tsukuba_l.png"));
			String hash2 = hash.getHash(new FileInputStream("/home/giovanni/Scaricati/opencv-2.4.9/samples/cpp/tsukuba_r.png"));
			String hash3 = hash.getHash(new FileInputStream("/home/giovanni/Scaricati/opencv-2.4.9/samples/cpp/board.jpg"));
			String hash4 = hash.getHash(new FileInputStream("/home/giovanni/Scaricati/opencv-2.4.9/samples/cpp/fruits.jpg"));
			String hash5 = hash.getHash(new FileInputStream("/home/giovanni/Scaricati/opencv-2.4.9/samples/cpp/baboon.jpg"));

			System.out.println(hash1.length());

			int hammingDist = hash.distance(hash1, hash2);
			int hammingDist2 = hash.distance(hash1, hash3);
			int hammingDist3 = hash.distance(hash1, hash4);
			int hammingDist4 = hash.distance(hash1, hash5);

			System.out.println("Hash1:" + hash1+"\nHash2:" + hash2 + "\nDistance1:" + hammingDist + "\n====");
			System.out.println("Hash1:" + hash1+"\nHash3:" + hash3 + "\nDistance2:" + hammingDist2 + "\n====");
			System.out.println("Hash1:" + hash1+"\nHash4:" + hash4 + "\nDistance3:" + hammingDist3 + "\n====");
			System.out.println("Hash1:" + hash1+"\nHash5:" + hash5 + "\nDistance4:" + hammingDist4 + "\n====");
			
			String hex = toHex(hash1);
			String bin = fromHex(hex);
			
			System.out.println("Hex:"+hex);
			System.out.println("Bina:"+bin + "\nHash:" +hash1);

			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String toHex(String s) {
		BigInteger bi = new BigInteger(s, 2);
		return bi.toString(16);
	}
	
	public static String fromHex(String s) {
		BigInteger bi = new BigInteger(s,16);
		return "0" + bi.toString(2);
	}
}

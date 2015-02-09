package test;

import java.io.FileInputStream;

import util.ImagePHash;

public class TestImageHash {

	public static void main(String[] args) throws Exception {
		
		ImagePHash hash = new ImagePHash(42, 5);

		String originale = hash.getHash(new FileInputStream("/home/giovanni/Scrivania/prova3Disturbata1.png"));
		String disturbo = hash.getHash(new FileInputStream("/home/giovanni/Scrivania/prova3Disturbata.png"));
		String disturbo2 = hash.getHash(new FileInputStream("/home/giovanni/Scrivania/prova3Disturbata2.png"));



		System.out.println("originale - originale: " + hash.matchPercentage(originale, originale));
		System.out.println("originale - disturbo: " + hash.matchPercentage(originale, disturbo));
		System.out.println("originale - disturbo1: " + hash.matchPercentage(originale, disturbo2));

	}
}

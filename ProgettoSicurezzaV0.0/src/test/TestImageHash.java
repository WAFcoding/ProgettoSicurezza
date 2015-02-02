package test;

import java.io.FileInputStream;

import util.ImagePHash;

public class TestImageHash {

	public static void main(String[] args) throws Exception {
		
		/*int startsize = 12;
		int maxsize = 50;
		
		FileOutputStream fos = new FileOutputStream(new File("/home/giovanni/Scrivania/dati.csv"));
		PrintStream ps = new PrintStream(fos);
		ps.println("max;min;contr1;contr2");
		for(int i = startsize; i < maxsize; i++) {

			for(int j = 2; j <= i ; j ++){

				System.out.println("max: " + i + " === min: " + j);*/
		ImagePHash hash = new ImagePHash(42, 5);

		//String originale = hash.getHash(new FileInputStream("/home/giovanni/Scrivania/testphash/2/test_2.png"));
		String originale = hash.getHash(new FileInputStream("/home/giovanni/Scrivania/testphash/1/test1_1.png"));
		String luminosita1 = hash.getHash(new FileInputStream("/home/giovanni/Scrivania/testphash/1/test1_2_lum.png"));
		String luminosita2 = hash.getHash(new FileInputStream("/home/giovanni/Scrivania/testphash/1/test1_3_lum.png"));
		String contrasto1 = hash.getHash(new FileInputStream("/home/giovanni/Scrivania/testphash/1/test1_2_contrasto.png"));
		String contrasto2 = hash.getHash(new FileInputStream("/home/giovanni/Scrivania/testphash/1/test1_3_contrasto.png"));
		String disturbo1 = hash.getHash(new FileInputStream("/home/giovanni/Scrivania/testphash/1/test1_2_disturbo.png"));
		String disturbo2 = hash.getHash(new FileInputStream("/home/giovanni/Scrivania/testphash/1/test1_3_disturbo.png"));
		String scanner1 = hash.getHash(new FileInputStream("/home/giovanni/Scrivania/testphash/1/test1_1_scanner.png"));
		String scanner2 = hash.getHash(new FileInputStream("/home/giovanni/Scrivania/testphash/1/test1_2_scanner.png"));
		//String contr1 = hash.getHash(new FileInputStream("/home/giovanni/Scrivania/testphash/2/test_2_1_contraffazione.png"));
		//String contr2 = hash.getHash(new FileInputStream("/home/giovanni/Scrivania/testphash/2/test_2_2_contraffazione.png"));
		String contr1 = hash.getHash(new FileInputStream("/home/giovanni/Scrivania/testphash/1/test1_1_contraffatto.png"));
		String contr2 = hash.getHash(new FileInputStream("/home/giovanni/Scrivania/testphash/1/test1_2_contraffatto.png"));


		System.out.println("originale - lum1: " + hash.matchPercentage(originale, luminosita1));
		System.out.println("originale - lum2: " + hash.matchPercentage(originale, luminosita2));

		System.out.println("originale - contrasto1: " + hash.matchPercentage(originale, contrasto1));
		System.out.println("originale - contrasto2: " + hash.matchPercentage(originale, contrasto2));

		System.out.println("originale - disturbo1: " + hash.matchPercentage(originale, disturbo1));
		System.out.println("originale - disturbo2: " + hash.matchPercentage(originale, disturbo2));

		System.out.println("originale - scanner1: " + hash.matchPercentage(originale, scanner1));
		System.out.println("originale - scanner2: " + hash.matchPercentage(originale, scanner2));
		double c1 = hash.matchPercentage(originale, contr1);
		double c2 = hash.matchPercentage(originale, contr2);
		System.out.println("originale - contraffatto1: " + c1);
		System.out.println("originale - contraffatto2: " + c2);
				/*
				ps.println(i + ";" + j + ";" + c1 + ";" +c2 );
				System.out.println("============================================");

			}
		}
		ps.close();
		fos.close();*/
	}
}

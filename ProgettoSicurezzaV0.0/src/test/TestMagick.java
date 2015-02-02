package test;

import java.awt.Color;

import magick.MagickException;
import magick.MagickImage;
import util.CryptoUtility;
import util.MagickUtility;
import exceptions.MagickImageNullException;

/**
 * 
 * Test
 *
 */
public class TestMagick {

	/**
	 * @param args
	 * @throws MagickException 
	 * @throws MagickImageNullException 
	 */
	public static void main(String[] args) throws MagickException, MagickImageNullException {
		String user = "giovanni";
		String path = "/home/" + user + "/Immagini/";
		
		MagickImage img = MagickUtility.getImage(path + "Immagine2.jpg");
		MagickImage cropped = MagickUtility.cropImage(img, 10, 50, 400, 200);
		MagickImage covered = MagickUtility.coverWithImage(img, cropped, 400, 400);
		MagickUtility.saveImage(covered, path + "covered.jpg");
		
		MagickImage rect = MagickUtility.createRectangleImage(new Color(255, 0, 0), 100, 100);
		MagickImage rectText = MagickUtility.createRectangleImageWithText(new Color(255,0,0), "BELLA X TE!!", new Color(255,255,255), 12.0, 45, 45, 400, 400);
		MagickImage covered2 = MagickUtility.coverWithImage(img, rect, 30, 30);
		MagickImage covered3 = MagickUtility.coverWithImage(covered2, rectText, 60, 60);
		MagickUtility.saveImage(covered2, path + "covered2.jpg");
		MagickUtility.saveImage(covered3, path + "covered3.jpg");
		
		byte[] bytes = MagickUtility.getMagickBytes(covered3);
		System.out.println("BYTES:\n" + CryptoUtility.toBase64(bytes));
	}

}

/**
 * Pasquale Verlotta - pasquale.verlotta@gmail.com
 * ProgettoSicurezzaV0.0 - 10/feb/2015
 */
package test;

import magick.MagickException;
import exceptions.MagickImageNullException;
import util.MagickUtility;

/**
 * @author pasquale
 *
 */
public class TestCropImage {
	
	public static void main(String[] args) throws MagickException, MagickImageNullException {
		String path= "/home/pasquale/ProgettoSicurezza/crop3/test_pdf.png";
		MagickUtility.getDataToDecrypt(path);
	}
}

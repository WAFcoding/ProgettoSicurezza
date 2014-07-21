package test;

import magick.MagickImage;
import util.MagickUtility;
import util.QRCode;


public class TestQRCode {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		String user = "giovanni";
		String path = "/home/" + user + "/Immagini/";
		String filename = "TEST.jpg";
		String filenameMagick = "TEST_MAGICK.jpg";
		
		QRCode qr = new QRCode();
		qr.writeQRCode("bellaaaa!", QRCode.DEFAULT_WIDTH, QRCode.DEFAULT_HEIGHT);
		qr.saveQRCodeToFile(path + filename);
		System.out.println("QRCode Scritto in:" + path + filename);
		
		QRCode qrRead = QRCode.readQRCodeFromFile(path + filename);
		String data = qrRead.readQRCode().getText();
		System.out.println("Letto:" + data);
		
		MagickImage qrmagick = qr.getQRMagick();
		MagickUtility.saveImage(qrmagick, path + filenameMagick);
		System.out.println("QRCode Scritto in:" + path + filenameMagick);
		
		MagickImage qrreadmagick = MagickUtility.getImage(path + filenameMagick);
		QRCode qrdmagick = QRCode.getQRCodeFromMagick(qrreadmagick);
		data = qrdmagick.readQRCode().getText();
		System.out.println("Letto:" + data);
		
		qrmagick.destroyImages();
		qrreadmagick.destroyImages();
	}

}

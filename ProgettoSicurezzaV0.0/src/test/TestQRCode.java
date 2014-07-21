package test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import util.QRCodeUtility;

import com.google.zxing.EncodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class TestQRCode {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws WriterException, IOException,
	NotFoundException {
		String path = "/home/giovanni/";
		String qrCodeData = "hello world";
		String filePath = path + "QRCode.png";
		String charset = "UTF-8"; // or "ISO-8859-1"
		Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		
		QRCodeUtility.createQRCode(qrCodeData, filePath, charset, hintMap, 200, 200);
		System.out.println("QR Code image created successfully!");

		System.out.println("Data read from QR Code: "
				+ QRCodeUtility.readQRCode(filePath, charset, hintMap));
	}

}

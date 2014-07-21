package util;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.PixelInterleavedSampleModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import magick.MagickImage;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.Decoder;
import com.google.zxing.qrcode.detector.Detector;

public class QRCode {

	private BitMatrix bitData;
	public static final int DEFAULT_WIDTH = 200;
	public static final int DEFAULT_HEIGHT = 200;

	public QRCode(int width, int height) {
		this.bitData = new BitMatrix(width, height);
	}

	public QRCode() {
		this.bitData = new BitMatrix(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public QRCode(BitMatrix data) {
		this.bitData = data;
	}

	public static QRCode getQRCodeFromMagick(MagickImage img) throws Exception {
		BufferedImage bufimg = magickImageToBufferedImage(img);
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufimg)));
		DetectorResult res = new Detector(binaryBitmap.getBlackMatrix()).detect();
		return new QRCode(res.getBits());
	}

	public DecoderResult readQRCode() throws ChecksumException, FormatException {
		if (this.bitData == null)
			return null;
		return new Decoder().decode(this.bitData);
	}

	public void writeQRCode(String qrCodeData, int qrWidth, int qrHeight) throws WriterException {
		this.bitData = new QRCodeWriter().encode(qrCodeData, BarcodeFormat.QR_CODE, qrWidth, qrHeight);
	}
	
	public MagickImage getQRMagick() {
		//TODO
		return null;
	}

	private static BufferedImage magickImageToBufferedImage(
			MagickImage magickImage) throws Exception {
		Dimension dim = magickImage.getDimension();
		int size = dim.width * dim.height;
		byte[] pixels = new byte[size * 3];

		magickImage.dispatchImage(0, 0, dim.width, dim.height, "RGB", pixels);

		BufferedImage bimage = createInterleavedRGBImage(dim.width, dim.height, pixels);
		ColorModel cm = bimage.getColorModel();
		Raster raster = bimage.getData();
		WritableRaster writableRaster = null;
		if (raster instanceof WritableRaster) {
			writableRaster = (WritableRaster) raster;
		} else {
			writableRaster = raster.createCompatibleWritableRaster();
		}
		BufferedImage Buffimage = new BufferedImage(cm, writableRaster, false, null);

		return Buffimage;

	}

	private static BufferedImage createInterleavedRGBImage(int imageWidth,
			int imageHeight, byte data[]) {
		int[] numBits = new int[3];
		int[] bandoffsets = new int[3];

		for (int i = 0; i < 3; i++) {
			numBits[i] = 8;
			bandoffsets[i] = i;
		}

		ComponentColorModel ccm = new ComponentColorModel(
				ColorSpace.getInstance(ColorSpace.CS_sRGB), numBits, false,
				false, // Alpha pre-multiplied
				Transparency.OPAQUE, DataBuffer.TYPE_BYTE);

		PixelInterleavedSampleModel csm = new PixelInterleavedSampleModel(
				DataBuffer.TYPE_BYTE, imageWidth, imageHeight, 3, // Pixel stride
				imageWidth * 3, // Scanline stride
				bandoffsets);

		DataBuffer dataBuf = new DataBufferByte(data, imageWidth * imageHeight * 3);
		WritableRaster wr = Raster.createWritableRaster(csm, dataBuf, new Point(0, 0));
		return new BufferedImage(ccm, wr, false, null);
	}
}

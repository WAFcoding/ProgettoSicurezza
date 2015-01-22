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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import magick.MagickException;
import magick.MagickImage;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.Decoder;
import com.google.zxing.qrcode.detector.Detector;

/**
 * Classe wrapper per l'utilizzo dei QR Code.
 * 
 * @author Giovanni Rossi
 *
 */
public class QRCode {

	/**
	 * I dati binari del QR Code.
	 */
	private BitMatrix bitData;
	
	/**
	 * Larghezza di default.
	 */
	public static final int DEFAULT_WIDTH = 200;
	
	/**
	 * Altezza di default.
	 */
	public static final int DEFAULT_HEIGHT = 200;

	/**
	 * Genera un QR Code vuoto con la risoluzione desiderata.
	 * @param width		La larghezza del QR Code.
	 * @param height	L'altezza del QR Code.
	 */
	public QRCode(int width, int height) {
		this.bitData = new BitMatrix(width, height);
	}

	/**
	 * Genera un QR Code vuoto con la risoluzione di default.
	 */
	public QRCode() {
		this.bitData = new BitMatrix(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	/**
	 * Genera un QR Code a partire da dati binari.
	 * @param data	I dati binari del QR Code.
	 */
	public QRCode(BitMatrix data) {
		this.bitData = data;
	}

	/**
	 * Genera un istanza a partire da un immagine della libreria ImageMagick contenente un QR Code.
	 * @param img	L'immagine in cui cercare il QR Code.
	 * 
	 * @return	Un istanza del QR Code.
	 * @throws Exception
	 */
	public static QRCode getQRCodeFromMagick(MagickImage img) throws Exception {
		BufferedImage bufimg = magickImageToBufferedImage(img);
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufimg)));
		DetectorResult res = new Detector(binaryBitmap.getBlackMatrix()).detect();
		return new QRCode(res.getBits());
	}

	/**
	 * Legge i dati contenuti nel QR Code.
	 * 
	 * @return L'insieme delle informazioni lette dal QR Code (basta eseguire <code>DecoderResult.getText()</code> per ottenere i dati.
	 * @see {@link DecoderResult#getText() }
	 * @throws ChecksumException
	 * @throws FormatException
	 */
	public DecoderResult readQRCode() throws ChecksumException, FormatException {
		if (this.bitData == null)
			return null;
		return new Decoder().decode(this.bitData);
	}

	/**
	 * Scrive i dati dentro il QR Code.
	 * @param qrCodeData	I dati del QR Code.
	 * @param qrWidth		La larghezza del QR Code.
	 * @param qrHeight		L'altezza del QR Code.
	 * 
	 * @throws WriterException
	 */
	public void writeQRCode(String qrCodeData, int qrWidth, int qrHeight) throws WriterException {
		this.bitData = new QRCodeWriter().encode(qrCodeData, BarcodeFormat.QR_CODE, qrWidth, qrHeight);
	}
	
	/**
	 * Ottiene un'immagine di Image Magick a partire da questo QR Code.
	 * 
	 * @return	L'istanza di MagickImage.
	 * 
	 * @throws MagickException
	 */
	public MagickImage getQRMagick() throws MagickException {
		if(this.bitData==null)
			return null;
		
		int height = this.bitData.getHeight();
		int width = this.bitData.getWidth();
		
		byte[] qrData = new byte[height * width * 3];
		for(int i = 0; i < height ; i++) {
			for(int j = 0; j < width; j++) {
				
				boolean color = this.bitData.get(i, j);
				
				//se true è NERO
				byte value = color ? (byte)0 : (byte)255;
				
				int indexheight = width * i;
				
				qrData[3*(indexheight + j)] = value;
				qrData[3*(indexheight + j) + 1] = value;
				qrData[3*(indexheight + j) + 2] = value;
			}
		}
		
		MagickImage qr = new MagickImage();	    
		qr.constituteImage(this.bitData.getWidth(),this.bitData.getHeight() , "RGB", qrData);
		
		return qr;
	}
	
	/**
	 * Salva questo QR Code su file.
	 * @param filePath	Il percorso del file in cui salvare.
	 * 
	 * @throws WriterException
	 * @throws IOException
	 */
	public void saveQRCodeToFile(String filePath) throws WriterException, IOException {
		System.out.println("QRCode salvato in: " + filePath);
		MatrixToImageWriter.writeToPath(this.bitData , filePath.substring(filePath.lastIndexOf('.') + 1), Paths.get(filePath));
	}
	
	/**
	 * Legge un QR Code da file.
	 * @param filePath	Il percorso del file.
	 * 
	 * @return	Un istanza del QR Code.
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws NotFoundException
	 * @throws FormatException
	 */
	public static QRCode readQRCodeFromFile(String filePath)
			throws FileNotFoundException, IOException, NotFoundException, FormatException {
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
				new BufferedImageLuminanceSource(
						ImageIO.read(new FileInputStream(filePath)))));
		DetectorResult res = new Detector(binaryBitmap.getBlackMatrix()).detect();
		return new QRCode(res.getBits());
	}

	/**
	 * Converte un'immagine MagickImage in BufferedImage.
	 * @param magickImage	L'immagine da convertire.
	 * 
	 * @return	L'immagine convertita.
	 * 
	 * @throws Exception
	 */
	public static BufferedImage magickImageToBufferedImage(
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

	/**
	 * Crea una BufferedImage a partire dai dati grezzi RGB.
	 * @param imageWidth	La larghezza dell'immagine.
	 * @param imageHeight	L'altezza dell'immagine.
	 * @param data			I dati grezzi dell'immagine.
	 * 
	 * @return Un istanza della BufferedImage.
	 */
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
	/**
	 * Salva su file il testo passato
	 * @param path String il percorso dove salvare il file
	 * @param text String il testo da scrivere nel file
	 */
	public static void saveFile(String path, String text){
		File f= new File(path);
		FileWriter fw;
		try {
			fw = new FileWriter(f);
			fw.write(text);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}

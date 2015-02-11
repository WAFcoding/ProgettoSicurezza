package util;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.TessAPI;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.vietocr.ImageHelper;
import magick.CompositeOperator;
import magick.DrawInfo;
import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;
import magick.PixelPacket;

import com.itextpdf.text.PageSize;
import com.recognition.software.jdeskew.ImageDeskew;

import exceptions.MagickImageNullException;

/**
 * Questa classe facilita ulteriormente l'utilizzo delle funzioni della libreria ImageMagick.
 * @author Giovanni Rossi
 *
 */
public class MagickUtility {
	
	/**
	 * Ritaglia l'immagine nel rettangolo selezionato.
	 * @param img		L'immagine in cui ritagliare.
	 * @param rect		Il rettangolo che definisce dove tagliare.
	 * @return L'immagine ritagliata.
	 * 
	 * @throws MagickImageNullException
	 * @throws MagickException
	 */
	public static MagickImage cropImage(MagickImage img, Rectangle rect) throws MagickImageNullException, MagickException {
		if(img==null) {
			throw new MagickImageNullException("Invalid Image: null");
		}
		return img.cropImage(rect);
	}
	
	/**
	 * Ritaglia l'immagine nel rettangolo selezionato.
	 * @param img		L'immagine da ritagliare.
	 * @param x			La coordinata X da cui cominciare a tagliare.
	 * @param y			La coordinata Y da cui cominciare a tagliare.
	 * @param height	L'altezza del ritaglio.
	 * @param width		La lunghezza del ritaglio.
	 * @return	L'immagine ritagliata.
	 * 
	 * @throws MagickImageNullException
	 * @throws MagickException
	 */
	public static MagickImage cropImage(MagickImage img, int x, int y, int height, int width) throws MagickImageNullException, MagickException {
		if(img==null) {
			throw new MagickImageNullException("Invalid Image: null");
		}
		Rectangle rect = new Rectangle(x, y, width, height);
		return cropImage(img, rect);
	}
	
	/**
	 * Ritaglia l'immagine nel rettangolo selezionato.
	 * @param imgpath	Il percorso dell'immagine da ritagliare.
	 * @param x			La coordinata X da cui cominciare a tagliare.
	 * @param y			La coordinata Y da cui cominciare a tagliare.
	 * @param height	L'altezza del ritaglio.
	 * @param width		La lunghezza del ritaglio.
	 * @return	L'immagine ritagliata.
	 * 
	 * @throws MagickException
	 * @throws MagickImageNullException
	 */
	public static MagickImage cropImage(String imgpath, int x, int y, int height, int width) throws MagickException, MagickImageNullException {
		ImageInfo info = new ImageInfo(imgpath);
		MagickImage img = new MagickImage(info);
		
		Rectangle rect = new Rectangle(x, y, width, height);
		MagickImage cropped = cropImage(img, rect);
	
		img.destroyImages();
		return cropped;
	}
	
	/**
	 * Copre l'immagine in input con quella selezionata. La copertura ha le stesse dimensioni dell'immagine coprente.
	 * @param img		L'immagine da coprire.
	 * @param cover		L'immagine con cui coprire.
	 * @param x			La coordinata X da dove iniziare a coprire.
	 * @param y			La coordinata Y da dove iniziare a coprire.
	 * @return	L'immagine coperta.
	 * 
	 * @throws MagickException
	 * @throws MagickImageNullException
	 */
	public static MagickImage coverWithImage(MagickImage img, MagickImage cover, int x, int y) throws MagickException, MagickImageNullException {
		if(img==null || cover==null)
			throw new MagickImageNullException("Invalid Arguments:null");
		
		img.compositeImage(CompositeOperator.AtopCompositeOp, cover, x, y);
		return img;
	}
	
	/**
	 * Copre l'immagine in input con quella selezionata. La copertura ha le stesse dimensioni dell'immagine coprente.
	 * @param img			L'immagine da coprire.
	 * @param coverPath		Il percorso dell'immagine con cui coprire.
	 * @param x				La coordinata X da dove iniziare a coprire.
	 * @param y				La coordinata Y da dove iniziare a coprire.
	 * @return	L'immagine coperta.
	 * 
	 * @throws MagickException
	 * @throws MagickImageNullException
	 */
	public static MagickImage coverWithImage(MagickImage img, String coverPath, int x, int y) throws MagickException, MagickImageNullException {
		if(img==null)
			throw new MagickImageNullException("Invalid Arguments:null");
		ImageInfo info = new ImageInfo(coverPath);
		MagickImage cover = new MagickImage(info);
		
		MagickImage covered = coverWithImage(img, cover, x, y);
		
		cover.destroyImages();
		return covered;
	}
	
	/**
	 * Genera un rettangolo colorato delle dimensioni desiderate.
	 * @param color		Il colore da applicare al rettangolo.
	 * @param width		La larghezza del rettangolo.
	 * @param height	L'altezza del rettangolo.
	 * @return	L'immagine rappresentante il rettangolo.
	 * 
	 * @throws MagickException
	 */
	public static MagickImage createRectangleImage(Color color, int width, int height) throws MagickException {
		
	    MagickImage rect = new MagickImage();	    
	    rect.constituteImage(1, 1, "RGBA", new byte[] {(byte)color.getRed(),(byte)color.getGreen(),(byte)color.getBlue(),(byte)color.getAlpha()});
	    
	    MagickImage scaledRect = rect.scaleImage(width, height);
	    
	    rect.destroyImages();
	    return scaledRect;
	}
	
	/**
	 * Crea un rettangolo colorato contenente del testo.
	 * @param color			Il colore del rettangolo.
	 * @param text			Il testo da inserire.
	 * @param textColor		Il colore del testo da inserire.	
	 * @param textSize		La dimensione del testo.
	 * @param textX			La coordinata X del punto in cui inserire il testo.
	 * @param textY			La coordinata Y del punto in cui inserire il testo.
	 * @param width			La larghezza del rettangolo.
	 * @param height		L'altezza del rettangolo.
	 * @return	L'immagine col rettangolo e il testo.
	 * 
	 * @throws MagickException
	 */
	public static MagickImage createRectangleImageWithText(Color color, String text, Color textColor, double textSize, int textX, int textY, int width, int height) throws MagickException {
		MagickImage rect = createRectangleImage(color, width, height);
		
		ImageInfo info = new ImageInfo();
		DrawInfo dinfo = new DrawInfo(info);
		
		PixelPacket tcolor = new PixelPacket(textColor.getRed(), textColor.getGreen(), textColor.getBlue(), textColor.getAlpha());
		
		dinfo.setText(text);
		dinfo.setFont("Arial");
		dinfo.setPointsize(textSize);
		dinfo.setFill(tcolor);
		dinfo.setTextAntialias(true);
		dinfo.setGeometry("+" + textX + "+" + textY);
		
		rect.annotateImage(dinfo);
		
		return rect;
	}
	
	/**
	 * Crea un oggetto MagickImage da poter usare per le elaborazioni a partire da un immagine nel sistema.
	 * @param pathToImage	Il percorso dell'immagine.
	 * @return	Un istanza di MagickImage.
	 * 
	 * @throws MagickException
	 */
	public static MagickImage getImage(String pathToImage) throws MagickException {
		ImageInfo info = new ImageInfo(pathToImage);
		return new MagickImage(info);
	}
	
	/**
	 * Salva un istanza di MagickImage nel sistema.
	 * @param img		L'immagine da salvare.
	 * @param path		Il percorso (compreso il nome del file) in cui salvare.
	 * 
	 * @throws MagickImageNullException
	 * @throws MagickException
	 */
	public static void saveImage(MagickImage img, String path) throws MagickImageNullException, MagickException {
		File f= new File(path);
		if(f.exists())
			f.delete();
		if(img==null)
			throw new MagickImageNullException("Invalid Arguments:null");
		ImageInfo info = new ImageInfo(path);
		img.setFileName(path);
		if(!img.writeImage(info)) {
			System.err.println("Unable to save image \"" + path + "\"");
		}
	}
	
	/**
	 * Recupera i byte di un immagine in formato MagickImage.
	 * @param img	L'immagine da cui ottenere i byte.
	 * 
	 * @return I byte dell'immagine.
	 * 
	 * @throws MagickImageNullException
	 * @throws MagickException
	 */
	public static byte[] getMagickBytes(MagickImage img) throws MagickImageNullException, MagickException {
		if(img==null)
			throw new MagickImageNullException("Invalid Arguments:null");
		ImageInfo info = new ImageInfo(img.getFileName());
		info.setMagick(img.getMagick());
		return img.imageToBlob(info);
	}
	
	public static String getTextFromMagickImage(MagickImage magickimage){
		String toReturn= "";
		
		try {
			BufferedImage img= QRCode.magickImageToBufferedImage(magickimage);

			img= ImageHelper.convertImageToBinary(img);
			
			Tesseract ts = Tesseract.getInstance();
			
			ts.setPageSegMode(TessAPI.TessPageSegMode.PSM_SINGLE_BLOCK);
			
			//serve a vedere quanto è ruotato il testo: troppo angolato non lo legge.
			ImageDeskew deskew = new ImageDeskew(img);
			double angle = deskew.getSkewAngle();
			
			/*Valore soglia: da vedere l'angolazione max tollerabile per non ruotare ogni volta l'immagine*/
			if(Math.abs(angle)>5) {
				img = ImageHelper.rotateImage(img, -angle);
			
				deskew = new ImageDeskew(img);
				angle = deskew.getSkewAngle();
			}
			
			toReturn = ts.doOCR(img);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return toReturn;
	}
	/**
	 * 0-text
	 * 1-cod info
	 * 2-signature
	 * 3..12-qrcodes
	 * @param path
	 * @return
	 */
	public static ArrayList<MagickImage> getDataToDecrypt(String path){
		
		ArrayList<MagickImage> toReturn= new ArrayList<MagickImage>();
		try {
			BufferedImage bimg = ImageIO.read(new File(path));
			int width          = bimg.getWidth();
			int height         = bimg.getHeight();
			
			MagickImage img= getImage(path);

			int pagesizex= new Double(PageSize.A4.getWidth()).intValue();
			int pagesizey= new Double(PageSize.A4.getHeight()).intValue();
			int resizedPagesizex= resizeX(pagesizex, width, pagesizex);
			
			//title
			int title_x= resizeX(129, width, pagesizex);
			int title_y= resizeY(10, height, pagesizey);
			int title_width= resizeX(200, width, pagesizex);
			//MagickImage img_title= cropImage(img, title_x, title_y, resizeY(40, height, pagesizey), title_width);
			//saveImage(img_title, "/home/pasquale/ProgettoSicurezza/crop/img_title.jpg");
			//toReturn.add(img_title);
			//author
			int author_x= resizeX(129, width, pagesizex);
			int author_y= resizeY(50, height, pagesizey);
			int author_width= resizeX(230, width, pagesizex);
			//MagickImage img_author= cropImage(img, author_x, author_y, resizeY(30, height, pagesizey), author_width);
			//saveImage(img_author, "/home/pasquale/ProgettoSicurezza/crop/img_author.jpg");
			//toReturn.add(img_author);
			//info
			int info_x= resizeX(129, width, pagesizex);
			int info_y= resizeY(80, height, pagesizey);
			//MagickImage img_info= cropImage(img, info_x, info_y, resizeY(20, height, pagesizey), title_width);
			//saveImage(img_info, "/home/pasquale/ProgettoSicurezza/crop/img_info.jpg");
			//toReturn.add(img_info);
			//text
			int text_x= resizeX(1, width, pagesizex);
			int text_y= resizeY(160, height, pagesizey);
			MagickImage img_text= cropImage(img, text_x, text_y, resizeY(400, height, pagesizey), resizedPagesizex);
			saveImage(img_text, "/home/pasquale/ProgettoSicurezza/crop3/img_text.png");
			toReturn.add(img_text);
			//codification info
			int codInfo_x= resizeX(pagesizex - 222, width, pagesizex);
			int codInfo_y= resizeY(10, height, pagesizey);
			int codeInfo_width= resizeX(101, width, pagesizex);
			MagickImage img_codInfo= cropImage(img, codInfo_x, codInfo_y, resizeY(101, height, pagesizey), codeInfo_width);
			//saveImage(img_codInfo, "/home/pasquale/ProgettoSicurezza/crop/img_codeInfo.jpg");
			toReturn.add(img_codInfo);
			//signature
			int signature_x= resizeX(pagesizex - 111, width, pagesizex);
			int signature_y= resizeY(10, height, pagesizey);
			int signature_width= resizeX(101, width, pagesizex);
			MagickImage img_signature= cropImage(img, signature_x, signature_y, resizeY(101, height, pagesizey), signature_width);
			//saveImage(img_signature, "/home/pasquale/ProgettoSicurezza/crop/img_signature.jpg");
			toReturn.add(img_signature);
			//qrcodes encripted
			int qrcode_y= resizeY(609, height, pagesizey);
			int qrcode_width= resizeX(101, width, pagesizex);
			int qrcode_x= resizeX(20, width, pagesizex);
			int j=0;
			for(int i= qrcode_x; i< resizeX(466, width, pagesizex);i= i + resizeX(110, width, pagesizex)){
				MagickImage img_qrcode= cropImage(img, i, qrcode_y, resizeY(101, height, pagesizey), qrcode_width);
				//saveImage(img_qrcode, "/home/pasquale/ProgettoSicurezza/crop/img_qrcode_"+j+".jpg");
				toReturn.add(img_qrcode);
				j++;
			}
			qrcode_y= resizeY(719, height, pagesizey);
			for(int i= qrcode_x; i< resizeX(466, width, pagesizex);i= i + resizeX(110, width, pagesizex)){
				MagickImage img_qrcode= cropImage(img, i, qrcode_y, resizeY(101, height, pagesizey), qrcode_width);
				//saveImage(img_qrcode, "/home/pasquale/ProgettoSicurezza/crop/img_qrcode_"+j+".jpg");
				toReturn.add(img_qrcode);
				j++;
			}
			
		} catch (MagickException | MagickImageNullException | IOException e) {
			e.printStackTrace();
		}
		
		return toReturn;
	}
	
	public static int resizeX(int xToResize, int resX, int pageSizeX){
		return (xToResize * resX)/ pageSizeX;
	}
	
	public static int resizeY(int yToResize, int resY, int pageSizeY){
		return (yToResize * resY)/pageSizeY;
	}

}

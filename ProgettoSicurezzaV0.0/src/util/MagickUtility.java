package util;

import java.awt.Color;
import java.awt.Rectangle;

import exceptions.MagickImageNullException;

import magick.CompositeOperator;
import magick.DrawInfo;
import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;
import magick.PixelPacket;

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
		if(img==null)
			throw new MagickImageNullException("Invalid Arguments:null");
		ImageInfo info = new ImageInfo(path);
		img.setFileName(path);
		if(!img.writeImage(info)) {
			System.err.println("Unable to save image \"" + path + "\"");
		}
	}
}

package test;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.recognition.software.jdeskew.ImageDeskew;
import com.recognition.software.jdeskew.ImageUtil;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.vietocr.ImageHelper;

/**
 * Guida per l'utilizzo di Tesseract e l'esecuzione di questo esempio.
 * 
 * 1. Innanzittutto installare Tesseract sul sistema:
 * 
 * [sudo apt-get install]
 * tesseract-ocr libtesseract3 tesseract-ocr-osd tesseract-ocr-ita tesseract-ocr-equ tesseract-ocr-eng
 * 
 * 2. Importare nel progetto le librerie(inviate x email):
 * 	tess4j.jar
 *  log4j-1.2.17.jar
 *  ghost4j-0.5.1.jar
 *  jai-imageio.jar
 *  jna-4.1.0.jar
 *  junit-4.10.jar
 *  
 * 3. Nella cartella principale del progetto va inserita la cartella "tessdata" (inviata zippata)
 * che serve per il funzionamento di Tess4J. 
 *  
 * 4. C'è un brutto bug di Tesseract che causa questo errore e non permette di fare nulla.
 * -->
 * Error: Illegal min or max specification!
 * "Fatal error encountered!" == NULL:Error:Assert failed:in file globaloc.cpp, line 75
 * <--
 * 
 * Se eseguito da shell bash deve essere impostata una variabile d'ambiente in questo modo:
 * export LC_NUMERIC="C"
 * 
 * Per renderlo definitivo si può editare il file ~/.bashrc ed inserire in fondo la linea
 * export LC_NUMERIC="C"
 * 
 * Questo però non va bene per Eclipse. Bisogna andare in Run-Configurations relativa al file TestOcr.java e
 * andare nella scheda 'Environment', click su 'Add' e immettere rispettivamente [LC_NUMERIC] e [C] (senza parentesi quadre).
 * Click su 'Apply' e poi 'Run'
 * 
 * Così dovrebbe funzionare tutto.
 * 
 * 
 * @author Giovanni Rossi
 *
 */

public class TestOcr {

	public static void main(String[] args) throws Exception {
		
		/**
		 * FIXME
		 * Il percorso del file immagine va cambiato (mando anche queste immagini per email)
		 */
		File f = new File("/home/giovanni/NetBeansProjects/Tess4J/eurotext_deskew.png");
		BufferedImage img = ImageIO.read(f);
		
		Tesseract ts = Tesseract.getInstance();
		
		//serve a vedere quanto è ruotato il testo: troppo angolato non lo legge.
		ImageDeskew deskew = new ImageDeskew(img);
		double angle = deskew.getSkewAngle();
		
		System.out.println("Angle before rotation:" + angle);
		
		/*Valore soglia: da vedere l'angolazione max tollerabile per non ruotare ogni volta l'immagine*/
		if(Math.abs(angle)>5) {
			img = ImageHelper.rotateImage(img, -angle);
		
			deskew = new ImageDeskew(img);
			angle = deskew.getSkewAngle();
			System.out.println("Angle after rotation (if needed):" + angle);
		}
		
		String result = ts.doOCR(img);
		System.out.println("Risultato:\n===\n" + result + "\n====\n");
	}
}

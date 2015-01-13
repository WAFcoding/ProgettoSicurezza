/**
 * 
 */
package util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

/**
 * Classe di utilità per la creazione dei pdf
 * 
 * @author "Pasquale Verlotta - pasquale.verlotta@gmail.com"
 *
 */
public class PDFUtil {
	
	private static final float TITLEX= 150;
	private static final float TITLEY= PageSize.A4.getHeight() - 50;
	private static final float DEFSIZETEXT= 11;
	private static final float DEFLINESIZE= 0.05f;
	
	private static PdfWriter pdfwr;
	private static Document doc;
	private static PdfContentByte pdfcb;
	private static Font font; 
	private static BaseFont bf_helv;

	/**
	 * crea un nuovo documento pdf
	 * @param doc Document
	 * @param file_path String il percorso dove salvare
	 * @see Document
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
	public static boolean create(String file_path) throws FileNotFoundException, DocumentException{
		doc= new Document(PageSize.A4);
		pdfwr= PdfWriter.getInstance(doc, new FileOutputStream(file_path));
		if(open()){
			pdfcb= pdfwr.getDirectContent();
			font= new Font(FontFamily.HELVETICA, 12);
			bf_helv = font.getCalculatedBaseFont(false);
			return true;
		}

		return false;
	}
	/**
	 * Crea ed apre un documento pdf
	 * @param doc Document
	 * @param path_file
	 * @return true se il documento si è aperto, false altrimenti
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
	public static boolean open() throws FileNotFoundException, DocumentException{
		
		doc.open();
		
		if(doc.isOpen()) return true;
		
		return false;
	}
	/**
	 * Chiude un documento aperto
	 * @param doc Document
	 * @return true se il documento era aperto, false altrimenti
	 */
	public static boolean close(){
		if(doc.isOpen()){
			doc.close();
			return true;
		}
		
		return false;
	}
	/**
	 * Aggiunge le credenziali al documento pdf
	 * @param doc Document il documento sul quale aggiungere le info
	 * @param title String il titolo
	 * @param subject String l'oggetto del testo
	 * @param keywords String le parole chiave
	 * @param author String l'autore
	 * @param creator String il creatore
	 */
	public static void addCredentials(String title, String subject, String keywords, 
									  String author, String creator){
		if(doc.isOpen()){
			if(title != null) doc.addTitle(title);
			if(subject != null) doc.addSubject(subject);
			if(keywords != null) doc.addKeywords(keywords);
			if(author != null) doc.addAuthor(author);
			if(creator != null) doc.addCreator(creator);
		}
		else{
			System.out.println("Aprire prima il documento");
		}
	}
	/**
	 * aggiunge il testo passato in un nuovo paragrafo
	 * @param text String
	 * @throws DocumentException
	 */
	public static void addText(String text, float x, float y, float textSize) throws DocumentException{
		pdfcb.beginText();
		if(textSize == 0) textSize= DEFSIZETEXT;
		pdfcb.setFontAndSize(bf_helv, textSize);
		//TODO PDFUtil: cambiare i valori della posizione del titolo
		pdfcb.showTextAligned(Element.ALIGN_LEFT, text, x, y, 0);
		pdfcb.endText();
		//pdfcb.saveState();
	}
	/**
	 * aggiunge un link in una phrase e poi al documento
	 * @param doc Document
	 * @param text String il testo da visualizzare
	 * @param name String il nome del link
	 * @param ref String il percorso del link
	 * @throws DocumentException
	 */
	//TODO PDFUtil: da modificare
	public static void addLink(String text, String name, String ref) throws DocumentException{
		if(doc.isOpen()){
			Anchor anc= new Anchor(text);
			anc.setName(name);
			anc.setReference(ref);
			Phrase c= new Phrase();
			c.add(anc);
			doc.add(c);
		}
	}
	/**
	 * Aggiunge nline righe vuote al documento doc
	 * @param doc Document
	 * @param nline int
	 * @throws DocumentException
	 */
	public static void addEmptyLine(int nline) throws DocumentException{
		Paragraph par= new Paragraph(" ");
		for(int i=0;i<nline;i++){
			par.add(" ");
		}
		if(doc.isOpen()){
			doc.add(par);
		}
		else
			System.out.println("Aprire prima il file");
	}
	
	public static void addImage(String path, float x, float y) throws MalformedURLException, IOException, DocumentException{
		Image img = Image.getInstance(path);
		//img.scaleAbsolute(50, 50);
		img.setAbsolutePosition(x, y);
		doc.add(img);
	}
	/**
	 * Aggiunge una linea orizzontale
	 * @param x1
	 * @param x2
	 * @param y
	 */
	public static void addLineHorizontal(float x, float y, float lenght){
		pdfcb.saveState();
		pdfcb.setLineWidth(DEFLINESIZE);
		pdfcb.moveTo(x, y);
		pdfcb.lineTo(x + lenght, y);
		pdfcb.stroke();
		pdfcb.restoreState();
	}
	/**
	 * Aggiunge una linea verticale
	 * @param y1
	 * @param y2
	 * @param x
	 */
	public static void addLineVertical(float x, float y, float lenght){
		pdfcb.saveState();
		pdfcb.setLineWidth(DEFLINESIZE);
		pdfcb.moveTo(x, y);
		pdfcb.lineTo(x, y + lenght);
		pdfcb.stroke();
		pdfcb.restoreState();
	}
	/**
	 * Aggiunge il titolo del pdf in cima ad esso
	 * @param title
	 */
	public static void addTitle(String title){

		pdfcb.beginText();
		pdfcb.setFontAndSize(bf_helv, 30);
		//TODO PDFUtil: cambiare i valori della posizione del titolo
		pdfcb.showTextAligned(Element.ALIGN_LEFT, title, TITLEX, TITLEY, 0);
		pdfcb.endText();
	}
	/**
	 * Aggiunge il logo al pdf nell'angolo in alto a sinistra
	 * @param imagePath il percorso dell'immagine da aggiungere
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws DocumentException 
	 */
	public static void addLogo(String imagePath, float x, float y) throws MalformedURLException, IOException, DocumentException{
		Image img = Image.getInstance(imagePath);
		//img.scaleAbsolute(50, 50);
		img.setAbsolutePosition(x, y);
		doc.add(img);
	}
	
	public static Document getDocument(){
		return doc;
	}

}

/**
 * 
 */
package util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
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

	/**
	 * crea un nuovo documento pdf
	 * @param doc Document
	 * @param file_path String il percorso dove salvare
	 * @return il Document relativo
	 * @see Document
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
	public static Document create(String file_path) throws FileNotFoundException, DocumentException{
		Document doc= new Document(PageSize.A4);
		PdfWriter.getInstance(doc, new FileOutputStream(file_path));
		
		return doc;
	}
	/**
	 * Crea ed apre un documento pdf
	 * @param doc Document
	 * @param path_file
	 * @return true se il documento si è aperto, false altrimenti
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
	public static boolean open(Document doc) throws FileNotFoundException, DocumentException{
		
		doc.open();
		
		if(doc.isOpen()) return true;
		
		return false;
	}
	/**
	 * Chiude un documento aperto
	 * @param doc Document
	 * @return true se il documento era aperto, false altrimenti
	 */
	public static boolean close(Document doc){
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
	public static void addCredentials(Document doc, String title, String subject, String keywords, 
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
	 * @param doc Document
	 * @param text String
	 * @throws DocumentException
	 */
	public static void addText(Document doc, String text) throws DocumentException{
		if(doc.isOpen()){
			doc.add(new Paragraph(text));
		}
	}
	/**
	 * aggiunge al documento passato una frase
	 * @param doc Document
	 * @param phrase String
	 * @throws DocumentException
	 */
	public static void addPhrase(Document doc, String phrase) throws DocumentException{
		if(doc.isOpen()){
			doc.add(new Phrase(phrase));
		}
	}
	/**
	 * aggiunge un link in una phrase e poi al documento
	 * @param doc Document
	 * @param text String il testo da visualizzare
	 * @param name String il nome del link
	 * @param ref String il percorso del link
	 * @throws DocumentException
	 */
	public static void addLink(Document doc, String text, String name, String ref) throws DocumentException{
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
	public static void addEmptyLine(Document doc, int nline) throws DocumentException{
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
	
	public static void addImage(Document doc, String path, int x, int y){
		
	}
	
	public static void addLineHorizontal(Document doc, float x1, float x2, float y){
		

	}

}

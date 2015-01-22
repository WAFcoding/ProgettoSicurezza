/**
 * 
 */
package util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.xmlgraphics.ps.dsc.tools.PageExtractor;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfImageObject;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;

/**
 * Classe di utilità per la creazione dei pdf
 * 
 * @author "Pasquale Verlotta - pasquale.verlotta@gmail.com"
 *
 */
public class PDFUtil {

	private static final Rectangle PAGESIZE= PageSize.A4;
	private static final float LOGOWIDTH= 70;
	private static final float LOGOHEIGHT= 83;
	private static final float LOGOX= 10;
	private static final float LOGOY= PAGESIZE.getHeight() - 10;
	private static final float TITLEX= LOGOX + LOGOWIDTH + 20;
	private static final float TITLEY= PAGESIZE.getHeight() - 30 - 10;
	private static final float AUTHORX= TITLEX;
	private static final float AUTHORY= TITLEY - 20 - 10;
	private static final float INFOX= TITLEX + 50;
	private static final float INFOY= AUTHORY - 10 - 10;
	private static final float DEFSIZETEXT= 11;
	private static final float DEFSIZELINE= 0.05f;
	
	private static PdfWriter pdfwr;
	private static Document doc;
	private static String filepath;
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
		doc= new Document(PAGESIZE);
		filepath= file_path;
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
		img.scaleAbsolute(100, 100);
		//img.setAbsolutePosition(x, PAGESIZE.getHeight() - y - img.getHeight());
		img.setAbsolutePosition(x, PAGESIZE.getHeight() - y - img.getScaledHeight());
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
		pdfcb.setLineWidth(DEFSIZELINE);
		pdfcb.moveTo(x, PAGESIZE.getHeight() - y);
		if(lenght == 0) lenght= PAGESIZE.getWidth() - (2*x);
		pdfcb.lineTo(x + lenght, PAGESIZE.getHeight() - y);
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
		pdfcb.setLineWidth(DEFSIZELINE);
		pdfcb.moveTo(x, PAGESIZE.getHeight() - y);
		pdfcb.lineTo(x, PAGESIZE.getHeight() - y - lenght);
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
	
	public static void addAuthor(String author){

		pdfcb.beginText();
		pdfcb.setFontAndSize(bf_helv, 20);
		//TODO PDFUtil: cambiare i valori della posizione del titolo
		pdfcb.showTextAligned(Element.ALIGN_LEFT, author, AUTHORX, AUTHORY, 0);
		pdfcb.endText();
	}
	
	public static void addSubtitleInfo(String date, String pagenumber, String info, String receiver){

		pdfcb.beginText();
		pdfcb.setFontAndSize(bf_helv, 10);
		String allInfo= date + " - " + pagenumber + " - " + info + " - " + receiver;
		pdfcb.showTextAligned(Element.ALIGN_LEFT, allInfo, INFOX, INFOY, 0);
		pdfcb.endText();
		//TODO PDFUtil: aggiungere linea orizzontale
	}
	/**
	 * Aggiunge il logo al pdf nell'angolo in alto a sinistra
	 * @param imagePath il percorso dell'immagine da aggiungere
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws DocumentException 
	 */
	public static void addLogo(String imagePath) throws MalformedURLException, IOException, DocumentException{
		Image img = Image.getInstance(imagePath);
		img.scaleAbsolute(LOGOWIDTH, LOGOHEIGHT);
		//System.out.println("larghezza = " + img.getWidth() + "altezza= " + img.getHeight());
		img.setAbsolutePosition(LOGOX, LOGOY - img.getScaledHeight());
		doc.add(img);
	}
	
	public static Document getDocument(){
		return doc;
	}
	
	public static void readPDF() throws IOException{
		PdfReader reader= new PdfReader(filepath);
		System.out.println("letto");
	}
	/**
	 * Estrae le immagini da un file pdf
	 * @param source
	 * @param destination
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static void extractImages(String source, String destination) throws IOException, DocumentException{
		PdfReader reader= new PdfReader(source);
		PdfReaderContentParser parser= new PdfReaderContentParser(reader);
		ImageRenderListener listener= new ImageRenderListener(destination);

        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            parser.processContent(i, listener);
        }
        reader.close();
	}
	
	private static class ImageRenderListener implements RenderListener{
		
		protected String path;
		
		public ImageRenderListener(String p){
			path= p;
		}

		/* (non-Javadoc)
		 * @see com.itextpdf.text.pdf.parser.RenderListener#beginTextBlock()
		 */
		@Override
		public void beginTextBlock() {
			// TODO Auto-generated method stub
			
		}

		/* (non-Javadoc)
		 * @see com.itextpdf.text.pdf.parser.RenderListener#endTextBlock()
		 */
		@Override
		public void endTextBlock() {
			// TODO Auto-generated method stub
			
		}

		/* (non-Javadoc)
		 * @see com.itextpdf.text.pdf.parser.RenderListener#renderImage(com.itextpdf.text.pdf.parser.ImageRenderInfo)
		 */
		@Override
		public void renderImage(ImageRenderInfo renderInfo) {
			 try {
		            String filename;
		            FileOutputStream os;
		            PdfImageObject image = renderInfo.getImage();
		            if (image == null) {
		                return;
		            }
		            filename = String.format(path, renderInfo.getRef().getNumber(), image.getFileType());
		            //System.out.println("Writing image to file: " + filename);
		            os = new FileOutputStream(filename);
		            os.write(image.getImageAsBytes());
		            os.flush();
		            os.close();
		        } catch (IOException e) {
		            Logger.getLogger(ImageRenderListener.class.getName()).log(Level.SEVERE, null, e);
		        }
		}

		/* (non-Javadoc)
		 * @see com.itextpdf.text.pdf.parser.RenderListener#renderText(com.itextpdf.text.pdf.parser.TextRenderInfo)
		 */
		@Override
		public void renderText(TextRenderInfo arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}

}

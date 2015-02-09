/**
 * 
 */
package util;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
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
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
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
	private static final float LOGOWIDTH= 109;
	private static final float LOGOHEIGHT= 130;
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
	private static final float RECTWIDTH= 101;
	private static final float RECTHEIGHT= 101;
	private static final float SIGNATUREX= PAGESIZE.getWidth() - 10 - RECTWIDTH;
	private static final float SIGNATUREY= 10 + RECTWIDTH;
	

	public static final String LOGO_PATH= "/home/giovanni/workspaceSII/ProgettoSicurezza/ProgettoSicurezzaV0.0/files/logoroma_torvergata.jpg";
	
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
		/*pdfcb.beginText();
		pdfcb.showTextAligned(Element.ALIGN_LEFT, text, x, PAGESIZE.getHeight() - y, 0);
		pdfcb.endText();*/
		if(textSize == 0) textSize= DEFSIZETEXT;
		ColumnText ct= new ColumnText(pdfcb);
		Phrase p= new Phrase(text);
		p.setFont(new Font(bf_helv, textSize));
		ct.setSimpleColumn(p, x, PAGESIZE.getHeight() - y, PAGESIZE.getWidth() - 20, PAGESIZE.getHeight() - y - 450, 20, Element.ALIGN_LEFT);
		ct.go();
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
	
	public static void addQRCodeImage(String path, float x, float y) throws MalformedURLException, IOException, DocumentException{
		Image img = Image.getInstance(path);
		//img.scaleAbsolute(100, 100);
		img.setAbsolutePosition(x, PAGESIZE.getHeight() - y - img.getHeight());
		//img.setAbsolutePosition(x, PAGESIZE.getHeight() - y - img.getScaledHeight());
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
	 * aggiunge un rettagolo alla pagina
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public static void addRectangle(float x, float y, float w, float h){
		pdfcb.saveState();
		pdfcb.setColorStroke(BaseColor.BLACK);
		pdfcb.setLineWidth(DEFSIZELINE);
		pdfcb.rectangle(x,PAGESIZE.getHeight() - y, w, h); 
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
			addRectangle(10, 710, 100, 100);
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
	
	public static void createDocument(String title, String author, String text, String signaturePath, String[] subtitleInfo, String[] qrCodes){
		try {
			addLogo(LOGO_PATH);
			addTitle(title);
			addAuthor(author);
			addSubtitleInfo(subtitleInfo[0], subtitleInfo[1], subtitleInfo[2], subtitleInfo[3]);
			addRectangle(SIGNATUREX, SIGNATUREY, RECTWIDTH, RECTHEIGHT);
			if(signaturePath != null && !signaturePath.equals("")){
				addQRCodeImage(signaturePath, SIGNATUREX + 1, SIGNATUREY + 1 - RECTHEIGHT);
			}
			addLineHorizontal(10, 150, 0);
			addText(text, 10, 170, 0);
			addLineHorizontal(10, 600, 0);
			for(float i=20; i<466; i= i + RECTWIDTH + 10){
				addRectangle(i, 710, RECTWIDTH, RECTHEIGHT);
			}
			for(float i=20; i<466; i= i + RECTWIDTH + 10){
				addRectangle(i, 820, RECTWIDTH, RECTHEIGHT);
			}
			int j=0;
			for(float i=20; i<500; i= i + DEFSIZELINE + 100 + DEFSIZELINE + 10){
				String tmp_qrcodePath= qrCodes[j];
				if(tmp_qrcodePath != null && !tmp_qrcodePath.equals("")){
					addQRCodeImage(tmp_qrcodePath, i, 611);
					j++;
				}
			}
			for(float i=20; i<500; i= i + DEFSIZELINE + 100 + DEFSIZELINE + 10){
				String tmp_qrcodePath= qrCodes[j];
				if(tmp_qrcodePath != null && !tmp_qrcodePath.equals("")){
					addQRCodeImage(tmp_qrcodePath, i, 721);
					j++;
				}
			}
			
		} catch (IOException | DocumentException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * crea la tabella riassuntiva della registrazione
	 * @param text1
	 * @param text2
	 * @param text3
	 * @param text4
	 * @param text5
	 * @param text6
	 * @param text7
	 * @param text8
	 * @param text9
	 * @param text10
	 * @param text11
	 * @param text12
	 * @param text13
	 * @param text14
	 * @param text15
	 * @throws DocumentException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static void createResumeTable(String text1, String text2, String text3, String text4, String text5,
										 String text6, String text7, String text8, String text9, String text10,
										 String text11, String text12, String text13, String text14, String text15) throws DocumentException, MalformedURLException, IOException{
		addLogo(LOGO_PATH);
		addTitle("Registration resume");
		PDFUtil.addLineHorizontal(10, 150, 0);
		
		PdfPTable table= new PdfPTable(2);
		table.setTotalWidth(PAGESIZE.getWidth() - 40);

		table.addCell("Name");
		table.addCell(text1);
		table.addCell("Surname");
		table.addCell(text2);
		table.addCell("Password");
		table.addCell(text3);
		table.addCell("Code");
		table.addCell(text4);
		table.addCell("Mail");
		table.addCell(text5);
		table.addCell("City");
		table.addCell(text6);
		table.addCell("Country");
		table.addCell(text7);
		table.addCell("Contry Code");
		table.addCell(text8);
		table.addCell("Organization");
		table.addCell(text9);
		table.addCell("Default Directory");
		table.addCell(text10);
		table.addCell("Output Direcotory");
		table.addCell(text11);
		table.addCell("Input Directory");
		table.addCell(text12);
		table.addCell("Public Key");
		table.addCell(text13);
		table.addCell("Private Key");
		table.addCell(text14);
		table.addCell("Secure ID");
		table.addCell(text15);
		table.writeSelectedRows(0, 15, 20, PAGESIZE.getHeight() - 170, pdfcb);
		
		//doc.add(table);
	}
	
	private static class ImageRenderListener implements RenderListener{
		
		protected String path;
		
		public ImageRenderListener(String p){
			path= p;
		}


		@Override
		public void beginTextBlock() {
			
		}


		@Override
		public void endTextBlock() {
			
		}


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

		@Override
		public void renderText(TextRenderInfo arg0) {
			
		}
		
	}

}

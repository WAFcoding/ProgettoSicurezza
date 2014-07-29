/**
 * 
 */
package test;

import java.io.FileNotFoundException;

import util.PDFUtil;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

/**
 * @author "Pasquale Verlotta - pasquale.verlotta@gmail.com"
 *
 */
public class TestPDF {

	/**
	 * @param args
	 * @throws DocumentException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, DocumentException {
		/*Document document= new Document(PageSize.A4);
		PdfWriter.getInstance(document, new FileOutputStream("/home/pasquale/ProgettoSicurezza/test.pdf"));
		document.open();*/
		Document doc= PDFUtil.create("/home/pasquale/ProgettoSicurezza/test2.pdf");
		if(PDFUtil.open(doc)){
			PDFUtil.addCredentials(doc, "secondo PDF", "prova", "java", "PV", "PV");
			PDFUtil.addEmptyLine(doc, 4);
			String text= "questo è il secondo pdf creato, vai giovannino";
			PDFUtil.addText(doc, text);
			PDFUtil.addLink(doc, "link", "LINK", "www.google.it");
			doc.newPage();
			PDFUtil.addPhrase(doc, "Questa è l'ultima pagina xD");
			PDFUtil.close(doc);
		}
	}

}

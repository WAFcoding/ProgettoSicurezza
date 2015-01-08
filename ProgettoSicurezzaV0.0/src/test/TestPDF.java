/**
 * 
 */
package test;

import java.io.FileNotFoundException;
import java.io.IOException;

import util.PDFUtil;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;

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
	public static void main(String[] args) throws Exception {
		/*Document document= new Document(PageSize.A4);
		PdfWriter.getInstance(document, new FileOutputStream("/home/pasquale/ProgettoSicurezza/test.pdf"));
		document.open();*/
		String image_path= "/home/pasquale/Documenti/logoroma_torvergata.jpg";
		Document doc= PDFUtil.create("/home/pasquale/ProgettoSicurezza/test_pdf.pdf");
		if(PDFUtil.open(doc)){
			PDFUtil.addCredentials(doc, "test_pdf", "prova", "java", "PV", "PV");
			Image img = Image.getInstance(image_path);
			img.scaleAbsolute(50, 50);
			img.setAbsolutePosition(10, PageSize.A4.getHeight() - img.getScaledHeight() - 10);
			doc.add(img);
			//PDFUtil.addEmptyLine(doc, 4);
			String title= "title";
			doc.addCreationDate();
			doc.add(new Chunk(title));
			//PDFUtil.addText(doc, title);
			//PDFUtil.addLink(doc, "link", "LINK", "www.google.it");
			//doc.newPage();
			PDFUtil.addPhrase(doc, "Questa è l'ultima pagina xD");
			//doc.newPage();
			PDFUtil.close(doc);
		}
	}

}

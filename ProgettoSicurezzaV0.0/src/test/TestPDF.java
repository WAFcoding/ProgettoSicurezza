/**
 * 
 */
package test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import util.PDFUtil;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

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
		//Document doc= PDFUtil.create("/home/pasquale/ProgettoSicurezza/test_pdf.pdf");
		Document doc= new Document(PageSize.A4);
		PdfWriter pdfwr = PdfWriter.getInstance(doc, new FileOutputStream("/home/pasquale/ProgettoSicurezza/test_pdf.pdf"));
		if(PDFUtil.open(doc)){
			PDFUtil.addCredentials(doc, "test_pdf", "prova", "java", "PV", "PV");
			Image img = Image.getInstance(image_path);
			img.scaleAbsolute(50, 50);
			img.setAbsolutePosition(10, PageSize.A4.getHeight() - img.getScaledHeight() - 10);
			doc.add(img);
			//PDFUtil.addEmptyLine(doc, 4);
			String title= "title";
			Font helvetica = new Font(FontFamily.HELVETICA, 12);
			BaseFont bf_helv = helvetica.getCalculatedBaseFont(false);
			PdfContentByte pdfcb= pdfwr.getDirectContent();
			pdfcb.beginText();
			pdfcb.setFontAndSize(bf_helv, 30);
			pdfcb.showTextAligned(Element.ALIGN_LEFT, title, 10 + img.getScaledWidth() + 20  , PageSize.A4.getHeight() - 10 - 30, 0);
			pdfcb.endText();
			pdfcb.saveState();
			pdfcb.setLineWidth(0.05f);
			pdfcb.moveTo(10, 200);
			pdfcb.lineTo(100, 200);
			pdfcb.stroke();
			pdfcb.restoreState();
			//doc.addCreationDate();
			//doc.add(ct);
			//PDFUtil.addText(doc, title);
			//PDFUtil.addLink(doc, "link", "LINK", "www.google.it");
			//doc.newPage();
			//PDFUtil.addPhrase(doc, "Questa è l'ultima pagina xD");
			//doc.newPage();
			PDFUtil.close(doc);
		}
	}

}

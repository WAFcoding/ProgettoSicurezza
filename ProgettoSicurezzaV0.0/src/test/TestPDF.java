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

		String image_path= "/home/pasquale/Documenti/logoroma_torvergata.jpg";
		boolean created= PDFUtil.create("/home/pasquale/ProgettoSicurezza/test_pdf.pdf");
		//Document doc= new Document(PageSize.A4);
		//PdfWriter pdfwr = PdfWriter.getInstance(doc, new FileOutputStream("/home/pasquale/ProgettoSicurezza/test_pdf.pdf"));
		if(created){
			PDFUtil.addCredentials("test_pdf", "prova", "java", "PV", "PV");
			/*Image img = Image.getInstance(image_path);
			img.scaleAbsolute(50, 50);
			img.setAbsolutePosition(10, PageSize.A4.getHeight() - img.getScaledHeight() - 10);
			doc.add(img);*/
			//PDFUtil.addEmptyLine(doc, 4);
			//PdfContentByte pdfcb= pdfwr.getDirectContent();
			/*pdfcb.beginText();
			pdfcb.setFontAndSize(bf_helv, 30);
			pdfcb.showTextAligned(Element.ALIGN_LEFT, title, 10 + img.getScaledWidth() + 20  , PageSize.A4.getHeight() - 10 - 30, 0);
			pdfcb.endText();*/
			//pdfcb.saveState();
			/*pdfcb.setLineWidth(0.05f);
			pdfcb.moveTo(10, 200);
			pdfcb.lineTo(100, 200);
			//pdfcb.stroke();
			pdfcb.restoreState();*/
			//doc.addCreationDate();
			//doc.add(ct);
			String title= "title";
			PDFUtil.addLogo(image_path);
			PDFUtil.addTitle(title);
			PDFUtil.addAuthor("author");
			PDFUtil.addSubtitleInfo("date", "1/1", "info", "receiver");
			PDFUtil.addLineHorizontal(10, 150, 0);
			PDFUtil.addLineHorizontal(10, 600, 0);
			PDFUtil.addImage("/home/pasquale/ProgettoSicurezza/test_0_101.jpg", 10, 610);
			PDFUtil.addImage("/home/pasquale/ProgettoSicurezza/test_0_200.jpg", 120, 610);
			PDFUtil.addImage("/home/pasquale/ProgettoSicurezza/test_0_297.jpg", 230, 610);
			PDFUtil.addImage("/home/pasquale/ProgettoSicurezza/test_0_393.jpg", 340, 610);
			PDFUtil.addImage("/home/pasquale/ProgettoSicurezza/test_0_484.jpg", 450, 610);
			//PDFUtil.addLineVertical(40, 30, 400);
			PDFUtil.close();
		}
	}

}

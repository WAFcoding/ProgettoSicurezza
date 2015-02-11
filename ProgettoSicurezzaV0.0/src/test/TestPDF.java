/**
 * 
 */
package test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;

import javax.imageio.ImageIO;

import magick.MagickImage;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import util.MagickUtility;
import util.PDFUtil;

import com.itextpdf.text.DocumentException;
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

		String image_path= "/home/pasquale/Developing/WorkSpace/Java/ProgettoSiurezzaV0.0/files/logoroma_torvergata.jpg";
		String path_test= "/home/pasquale/ProgettoSicurezza/test_pdf.pdf";
		String path_registration_test= "/home/pasquale/ProgettoSicurezza/registration_test_pdf.pdf";
		boolean created= PDFUtil.create(path_test);
		//Document doc= new Document(PageSize.A4);
		//PdfWriter pdfwr = PdfWriter.getInstance(doc, new FileOutputStream("/home/pasquale/ProgettoSicurezza/test_pdf.pdf"));
		if(created){
			//PDFUtil.addCredentials("test_pdf", "prova", "java", "PV", "PV");

			String title= "title";
			String author= "author";
			String text="… è cosi bizzarramente improbabile che una cosa straordinariamente utile come il pesce Babele si sia evoluta per puro caso, che alcuni pensatori sono arrivati a vedere in ciò la prova finale e lampante della non-esistenza di Dio. “Le loro argomentazioni seguono pressapoco questo schema: ‘Mi rifiuto di dimostrare che esisto’ dice Dio ‘perchè la dimostrazione è una negazione della fede, e senza la fede io non sono niente’. “‘Ma’ dice l’Uomo ‘il pesce Babele è una chiara dimostrazione involontaria della Tua esistenza, no? Non avrebbe mai potuto evolversi per puro caso. Esso dimostra che Tu esisti, e dunque, grazie a questa dimostrazione, Tu, per via di quanto Tu stesso asserisci a proposito delle dimostrazioni, non esisti. Q. E. D., Quot Erat Demonstrandum’. “‘Povero me!’, dice Dio. ‘Non ci avevo pensato!’ e sparisce immediatamente in una nuvoletta di logica. “‘Oh, com’è stato facile!’ dice l’Uomo, e, per fare il bis, passa a dimostrare che il nero è bianco, per poi finire ucciso sul primo attraversamento pedonale che successivamente incontra”.";
			String[] subtitleInfo= {"receiver", "date"};
			String[] qrCodes= {"/home/pasquale/ProgettoSicurezza/test_0.jpg", 
					"/home/pasquale/ProgettoSicurezza/test_1.jpg", 
					"/home/pasquale/ProgettoSicurezza/test_0.jpg",
					"/home/pasquale/ProgettoSicurezza/test_0.jpg",
					"/home/pasquale/ProgettoSicurezza/test_0.jpg",
					"/home/pasquale/ProgettoSicurezza/test_0.jpg",
					"/home/pasquale/ProgettoSicurezza/test_0.jpg",
					"/home/pasquale/ProgettoSicurezza/test_0.jpg",
					"/home/pasquale/ProgettoSicurezza/test_0.jpg",
					"/home/pasquale/ProgettoSicurezza/test_0.jpg"};
			String signaturePath= "/home/pasquale/ProgettoSicurezza/test_1.jpg";
			String infoQrCodePath= "/home/pasquale/ProgettoSicurezza/test_1.jpg";
			PDFUtil.createDocument(title, author, text, infoQrCodePath, subtitleInfo, qrCodes);
			
			
			
			
			//PDFUtil.addLineVertical(40, 30, 400);
			//PDFUtil.createResumeTable("test1", "test2", "test3", "test4", "test5", "test6", "test7",
				//					  "test8", "test9", "test10", "test11", "test12", "test13", "test14");
			PDFUtil.close();
			
			PDDocument document= PDDocument.load(path_test);
			PDPage page= (PDPage) document.getDocumentCatalog().getAllPages().get(0);
			BufferedImage img = page.convertToImage(BufferedImage.TYPE_BYTE_BINARY, 400);
			int resx= img.getWidth();
			int resy= img.getHeight();
			File outputfile = new File("/home/pasquale/ProgettoSicurezza/tmp_mattata.png");
			ImageIO.write(img, "jpg", outputfile);
			

			int pagesizex= new Double(PageSize.A4.getWidth()).intValue();
			int pagesizey= new Double(PageSize.A4.getHeight()).intValue();
			MagickImage magick= MagickUtility.getImage("/home/pasquale/ProgettoSicurezza/tmp_mattata.png");
			int x = MagickUtility.resizeX(1, resx, pagesizex);
			int y= MagickUtility.resizeY(160, resy, pagesizey);
			int width= MagickUtility.resizeX(pagesizex, resx, pagesizex);
			int height= MagickUtility.resizeY(pagesizey - 160, resy, pagesizey);
			MagickImage crop= MagickUtility.cropImage(magick, x, y, height, width);
			MagickUtility.saveImage(crop, "/home/pasquale/ProgettoSicurezza/tmp_crop_mattata.png");
		}
		//PDFUtil.extractImages(path_test, "/home/pasquale/ProgettoSicurezza/img/Img%s.%s");
		
	}

}

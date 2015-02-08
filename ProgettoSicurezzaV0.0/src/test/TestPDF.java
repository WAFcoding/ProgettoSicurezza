/**
 * 
 */
package test;

import java.io.FileNotFoundException;

import util.PDFUtil;

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
	public static void main(String[] args) throws Exception {

		String image_path= "/home/pasquale/Developing/WorkSpace/Java/ProgettoSiurezzaV0.0/files/logoroma_torvergata.jpg";
		String path_test= "/home/pasquale/ProgettoSicurezza/test_pdf.pdf";
		String path_registration_test= "/home/pasquale/ProgettoSicurezza/registration_test_pdf.pdf";
		boolean created= PDFUtil.create(path_registration_test);
		//Document doc= new Document(PageSize.A4);
		//PdfWriter pdfwr = PdfWriter.getInstance(doc, new FileOutputStream("/home/pasquale/ProgettoSicurezza/test_pdf.pdf"));
		if(created){
			//PDFUtil.addCredentials("test_pdf", "prova", "java", "PV", "PV");

			String title= "title";
			String author= "author";
			String text= "text";
			String[] subtitleInfo= {"date", "pagenumber", "info", "receiver"};
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
			/*PDFUtil.addLogo(PDFUtil.LOGO_PATH);
			PDFUtil.addTitle(title);
			PDFUtil.addAuthor("author");
			PDFUtil.addSubtitleInfo("date", "1/1", "info", "receiver");
			PDFUtil.addLineHorizontal(10, 150, 0);
			PDFUtil.addLineHorizontal(10, 600, 0);
			PDFUtil.addRectangle(10, 710, 100, 100);*/
			//PDFUtil.addQRCodeImage("/home/pasquale/ProgettoSicurezza/test_0_101.jpg", 10, 610);
			//PDFUtil.addQRCodeImage("/home/pasquale/ProgettoSicurezza/test_0_200.jpg", 120, 610);
			//PDFUtil.addQRCodeImage("/home/pasquale/ProgettoSicurezza/test_0_297.jpg", 230, 610);
			//PDFUtil.addQRCodeImage("/home/pasquale/ProgettoSicurezza/test_0_393.jpg", 340, 610);
			//PDFUtil.addQRCodeImage("/home/pasquale/ProgettoSicurezza/test_0_484.jpg", 450, 610);
			PDFUtil.createDocument(title, author, text, signaturePath, subtitleInfo, qrCodes);
			
			
			//PDFUtil.addLineVertical(40, 30, 400);
			//PDFUtil.createResumeTable("test1", "test2", "test3", "test4", "test5", "test6", "test7",
				//					  "test8", "test9", "test10", "test11", "test12", "test13", "test14");
			PDFUtil.close();
			
			/*PDFUtil.create("/home/pasquale/ProgettoSicurezza/test_pdf.pdf");
			PDFUtil.extractImages(path, "/home/pasquale/ProgettoSicurezza/Img%s.%s");
			PDFUtil.close();*/
		}
		//PDFUtil.extractImages(path_test, "/home/pasquale/ProgettoSicurezza/img/Img%s.%s");
		
	}

}

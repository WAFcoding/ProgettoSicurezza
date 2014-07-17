import java.awt.Rectangle;

import magick.*;

/*
 * test
 */
public class MainSicurezza {

	public static void main(String[] args) throws Exception {

		String path = "/home/giovanni/Immagini/";
		
	    String inputfileName = path + "Immagine2.jpg";
	    ImageInfo info = new ImageInfo(inputfileName);
	    MagickImage mk = new MagickImage(info);
	     
	    MagickImage cropped = mk.cropImage(new Rectangle(10, 10, 100, 100));
	    
	    cropped.setFileName(path + "magick_img.jpg");
	    cropped.writeImage(info);
	    
	    mk.compositeImage(CompositeOperator.AtopCompositeOp, cropped, 150, 200);
	    
	    DrawInfo dinfo = new DrawInfo(info);
	    dinfo.setGeometry("0,0+50+50");
	    mk.colorFloodfillImage(dinfo, PixelPacket.queryColorDatabase("yellow"), 30, 20, PaintMethod.ReplaceMethod);
	    
	    mk.setFileName(path + "magick_draw.jpg");
	    mk.writeImage(info);
	     
		/*javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				PrimaryLayout p = new PrimaryLayout();
				p.createAndShowGUI();
			}
		});*/
		
	}
}

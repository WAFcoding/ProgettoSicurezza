import magick.*; 


/*
 * test
 */
public class MainSicurezza {

	public static void main(String[] args) {
		ImageMagick img;

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	PrimaryLayout p= new PrimaryLayout();
                p.createAndShowGUI();
            }
        });
	}

}

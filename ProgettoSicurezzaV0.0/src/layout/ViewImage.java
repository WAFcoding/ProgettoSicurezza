/**
 * 
 */
package layout;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Oggetto che consente di visualizzare un'immagine
 * 
 * @author "Pasquale Verlotta - pasquale.verlotta@gmail.com"
 *
 */
public class ViewImage extends Component{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage img;
	/**
	 * @param img
	 */
	public ViewImage(String path) {

		try {
			setImg(ImageIO.read(new File(path)));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		super.setSize(getImg().getWidth(), getImg().getHeight());
	}
	
	public ViewImage(BufferedImage img){
		
		setImg(img);
		super.setSize(getImg().getWidth(), getImg().getHeight());
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}
	
	public void paint(Graphics g){
		g.drawImage(img, 0, 0, null);
	}

}

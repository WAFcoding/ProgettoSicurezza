package Layout;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 * Questa classe e' il controllore del layout dell'applicazione
 * @author "Pasquale Verlotta - pasquale.verlotta@gmail.com"
 *
 */

public class LayoutControl {
	
	private JFrame mainFrame;
	private SettingsLayout s_layout;
	private PrimaryLayout p_layout;
	private EncodeLayout e_layout;
	
	public void createLayout(){
		
		mainFrame= new JFrame("Progetto SII");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(480, 320);
		mainFrame.setVisible(true);
		setLayout(0);
	}
	
	//=========================================================================
	//=================GESTIONE LAYOUT=========================================
	/**
	 * Imposta il layout da visualizzare
	 * @param layout int 0=Primary, 1=Encode, 2=Settings
	 */
	public void setLayout(int layout){
		if(layout == 0){
			PrimaryLayout();
		}
		else if(layout == 1){
			EncodeLayout();
		}
		else if(layout == 2){
			SettingsLayout();
		}
		
		mainFrame.pack();
	}

	public void PrimaryLayout(){
		if(p_layout == null){
			p_layout= new PrimaryLayout(this, mainFrame.getContentPane());
			p_layout.addComponentsToPane();
		}
		else{
			p_layout.addComponentsToPane();
		}
	}
	
	public void EncodeLayout(){
		if(e_layout == null){
			e_layout= new EncodeLayout(this, mainFrame.getContentPane());
			e_layout.addComponentsToPane();
		}
		else{
			e_layout.addComponentsToPane();
		}
	}
	
	public void SettingsLayout(){
		if(s_layout == null){
			s_layout= new SettingsLayout(this, mainFrame.getContentPane());
			s_layout.addComponentsToPane();
		}
		else{
			s_layout.addComponentsToPane();
		}
	}
	//============================================================================
	public String getPrimaryChoose(){
		
		if(p_layout != null){
			return p_layout.getFile_choosed();
		}
		
		return null;
	}
	
	public void drawImage(String path){

		//mainFrame.removeAll();
		viewImage v_img= new viewImage(getPrimaryChoose());
		//mainFrame.setSize(v_img.getImage().getWidth(), v_img.getImage().getHeight());
		mainFrame.add(v_img);
		
		JFrame frame= new JFrame("IMMAGINE");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(v_img.getImage().getWidth(), v_img.getImage().getHeight());
		//viewImage v_img= new viewImage(getPrimaryChoose());
		frame.add(v_img);
		frame.setVisible(true);
		
	}
	
	private class viewImage extends Component{
		
		private BufferedImage img;
		
		public viewImage(String path){

			try {
				img= ImageIO.read(new File(getPrimaryChoose()));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		public void paint(Graphics g){
			g.drawImage(img, 0, 0, null);
		}
		
		public BufferedImage getImage(){
			return this.img;
		}
	}
}

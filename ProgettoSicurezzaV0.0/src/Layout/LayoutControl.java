package Layout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ViewportLayout;

import magick.MagickException;
import magick.MagickImage;
import magick.util.MagickViewer;
import sun.awt.X11.Screen;
import util.MagickUtility;
/**
 * Questa classe e' il controllore del layout dell'applicazione
 * @author "Pasquale Verlotta - pasquale.verlotta@gmail.com"
 *
 */

public class LayoutControl {
	
	private final static int WIDTH= 400;
	private final static int HEIGHT= 400;
	
	private JFrame mainFrame;
	private SettingsLayout s_layout;
	private PrimaryLayout p_layout;
	private EncodeLayout e_layout;
	private ArrayList<String> choosed_file;
	private String[] types= {"gif", "jpg", "png", "bmp"};
	
	public LayoutControl(){
		
		choosed_file= new ArrayList<String>();
		mainFrame= new JFrame("Progetto SII");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(WIDTH, HEIGHT);
		mainFrame.setVisible(true);
		mainFrame.setLocation(150, 100);
		setLayout(0);
	}
	
	public void createLayout(){
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
		
		//mainFrame.pack();
		mainFrame.setSize(WIDTH, HEIGHT);
		mainFrame.repaint();
		mainFrame.validate();
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
			e_layout= new EncodeLayout(this, mainFrame.getContentPane(), this.choosed_file);
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
	/**
	 * 
	 * @return la scelta effettuata nel menu principale
	 */
	public String getPrimaryChoose(){
		
		if(p_layout != null){
			return p_layout.getFile_choosed();
		}
		
		return null;
	}
	
	public void addChoice(String path){
		if(!this.choosed_file.contains(path)){
			if(isAnImage(path))
				this.choosed_file.add(path);
			else
				System.out.println("file non compatibile");
		}
		else{
			System.out.println("percorso gia presente");
		}
		
		if(e_layout != null)
			e_layout.updateList();
	}
	
	public void removeItem(int pos){
		this.choosed_file.remove(pos);
		
		if(e_layout != null)
			e_layout.updateList();
	}
	
	/**
	 * controlla che il file passato sia un tipo immagine nota
	 * @param path String il percorso del file
	 * @return false se non è un'immagine
	 */
	public boolean isAnImage(String path){
		for(String s : types){
			if(path.endsWith(s)){
				return true;
			}
		}
		
		return false;
	}
	/**
	 * Disegna l'immagine scelta nella finestra dell'applicazione
	 * @param path String il percorso dell'imagine da disegnare
	 */
	public void drawImage(String path, int backTo){

		ImageLayout img_layout= new ImageLayout(this, mainFrame.getContentPane(), backTo);
		try {
			img_layout.setViewer(path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		img_layout.addComponentsToPane();
		
		if(img_layout.getImgWidth() >= 800 || img_layout.getImgHeight() >= 600){
			mainFrame.setSize(new Dimension(1000, 700));
			mainFrame.repaint();
			mainFrame.validate();
		}
		else{
			mainFrame.setSize(new Dimension(img_layout.getImgWidth()+100, img_layout.getImgHeight()+50));
			mainFrame.repaint();
			mainFrame.validate();
		}
	}
	
	
}

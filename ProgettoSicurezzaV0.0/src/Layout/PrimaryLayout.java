package Layout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.applet.Applet;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import magick.DrawInfo;
import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;
import sun.awt.image.ToolkitImage;
import util.MagickUtility;

import com.sun.media.sound.Toolkit;

/**
 * Questa classe rappresenta lo stile del layout principale dell'applicazione
 * 
 * @author "Pasquale Verlotta - pasquale.verlotta@gmail.com"
 *
 */

public class PrimaryLayout implements GeneralLayout{
    private final boolean RIGHT_TO_LEFT = false;
    private String file_choosed;
    private Container pane;
    private LayoutControl control;
    
    public PrimaryLayout(LayoutControl control, Container pane){
    	
    	setPane(pane);
    	setFile_choosed("");
    	setControl(control);
    }
    
    @Override
    public void addComponentsToPane() {

        JButton button;
        pane.removeAll();
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//natural height, maximum width
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor= GridBagConstraints.CENTER;
		c.ipady= 40;
		c.insets= new Insets(10, 10, 10, 10);

		button = new JButton("OPEN");
		c.gridx = 0;c.gridy = 0;c.weightx = 0.5;
		button.addActionListener(new OpenAction());
		pane.add(button, c);
	
		button = new JButton("SAVE");
		c.gridx = 1;c.gridy = 0;c.weightx = 0.5;
		pane.add(button, c);
	
		button = new JButton("ENCODE");
		c.gridx = 0;c.gridy = 1;c.weightx = 0.5;
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				getControl().setLayout(1);
				System.out.println("ENCODE");		
			}
		});
		pane.add(button, c);
	
		button = new JButton("DECODE");
		c.gridx = 1;c.gridy = 1;c.weightx = 0.5;
		pane.add(button, c);
	
		button = new JButton("SETTINGS");
		c.gridx = 0;c.gridy = 2;c.weightx = 0.5;c.gridwidth= 2;
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				getControl().setLayout(2);
				System.out.println("SETTINGS");		
			}
		});
		pane.add(button, c);
	
		/*button = new JButton("Long-Named Button 4");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 40;      //make this component tall
		c.weightx = 0.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 2;
		pane.add(button, c);*/
	
		/*button = new JButton("5");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0;       //reset to default
		c.weighty = 1.0;   //request any extra vertical space
		c.anchor = GridBagConstraints.PAGE_END; //bottom of space
		c.insets = new Insets(10,0,0,0);  //top padding
		c.gridx = 1;       //aligned with button 2
		c.gridwidth = 2;   //2 columns wide
		c.gridy = 3;       //third row
		pane.add(button, c);*/
    }
    
    public String getFile_choosed() {
		return file_choosed;
	}

	public void setFile_choosed(String file_choosed) {
		this.file_choosed = file_choosed;
	}

	public Container getPane() {
		return pane;
	}

	public void setPane(Container pane) {
		this.pane = pane;
	}

	public LayoutControl getControl() {
		return control;
	}

	public void setControl(LayoutControl control) {
		this.control = control;
	}

	private class OpenAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser file_chooser= new JFileChooser();
			int choose= file_chooser.showDialog(null, "apri");
			
			if(choose == JFileChooser.APPROVE_OPTION){
				File file= file_chooser.getSelectedFile();
				setFile_choosed(file.getAbsolutePath());
				System.out.println("Il file scelto è: " + getFile_choosed());
				
				control.drawImage(getFile_choosed());
				control.addChoice(getFile_choosed());
			}
		}
    }	
}

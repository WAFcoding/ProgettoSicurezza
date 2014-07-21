/**
 * 
 */
package Layout;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import util.MagickUtility;
import magick.MagickException;
import magick.MagickImage;
import magick.util.MagickViewer;

/**
 * Classe che visualizza e gestisce un'immagine
 * 
 * @author "Pasquale Verlotta - pasquale.verlotta@gmail.com"
 *
 */
public class ImageLayout implements GeneralLayout{
	
    private Container pane;
    private LayoutControl control;
    private ViewImage v_img;
    private MagickViewer viewer;
    
    public ImageLayout(LayoutControl control, Container pane){
    	setControl(control);
    	setPane(pane);
    	setViewer(new MagickViewer());
    	//setV_img(new ViewImage(path));//da impostare con un valore di default
    }
    
	@Override
	public void addComponentsToPane() {
		
		JButton button;
		pane.removeAll();
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor= GridBagConstraints.CENTER;
		c.insets= new Insets(10, 10, 10, 10);
		
		c.gridx=0;c.gridy=0;c.gridwidth=5;
		//c.ipady=v_img.getHeight();c.ipadx=v_img.getWidth();
		c.ipady= viewer.getMinimumSize().height;c.ipadx=viewer.getMinimumSize().width;
		pane.add(viewer, c);
		//pane.add(v_img, c);
		
		c.gridwidth=1;c.ipady=0;c.ipadx=0;c.fill = GridBagConstraints.NONE;
		
		c.gridx=0;c.gridy=1;c.weightx=0.5;
		button= new JButton("BACK");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getControl().setLayout(0);
			}
		});
		pane.add(button, c);
		
		c.gridx=1;c.gridy=1;c.weightx=0.5;c.fill = GridBagConstraints.NONE;c.ipady=0;
		button= new JButton("BACK");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//getPane().setVisible(false);
			}
		});
		pane.add(button, c);
		
		c.gridx=2;c.gridy=1;c.weightx=0.5;c.fill = GridBagConstraints.NONE;c.ipady=0;
		button= new JButton("BACK");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//getPane().setVisible(false);
			}
		});
		pane.add(button, c);
		
		c.gridx=3;c.gridy=1;c.weightx=0.5;c.fill = GridBagConstraints.NONE;c.ipady=0;
		button= new JButton("BACK");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//getPane().setVisible(false);
			}
		});
		pane.add(button, c);
		
		c.gridx=4;c.gridy=1;c.weightx=0.5;c.fill = GridBagConstraints.NONE;c.ipady=0;
		button= new JButton("BACK");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//getPane().setVisible(false);
			}
		});
		pane.add(button, c);
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

	public ViewImage getV_img() {
		return v_img;
	}

	public void setV_img(ViewImage v_img) {
		this.v_img = v_img;
	}

	public MagickViewer getViewer() {
		return viewer;
	}

	public void setViewer(MagickViewer viewer) {
		this.viewer = viewer;
	}
	
	public void setViewer(String path){
		try {
			MagickImage img= MagickUtility.getImage(path);
			viewer.setImage(img);
			viewer.setMinimumSize(img.getDimension());
			viewer.setMaximumSize(img.getDimension());
		} catch (MagickException e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}

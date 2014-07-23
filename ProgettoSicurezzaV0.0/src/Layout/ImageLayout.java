/**
 * 
 */
package Layout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

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
    	getPane().addMouseListener(new trackMouse());
    	getPane().addMouseMotionListener(new actionMouse());
    	//setV_img(new ViewImage(path));//da impostare con un valore di default
    }
    
	@Override
	public void addComponentsToPane() {
		
		JButton button;
		
		pane.removeAll();
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.anchor= GridBagConstraints.CENTER;
		c.insets= new Insets(0, 5, 0, 5);
		//0.0 - VIEWER
		c.gridx=0;c.gridy=0;c.gridwidth=5;c.ipady=0;c.ipadx=0;c.fill = GridBagConstraints.NONE;
		viewer.setName("VIEWER");
		viewer.addMouseListener(new trackMouse());
		viewer.addMouseMotionListener(new actionMouse());
		//System.out.println("il viewer si trova in posizione " + viewer.getLocation().getX() + " - " + viewer.getLocation().getY());
		pane.add(viewer, c);
		
		c.gridwidth=1;c.ipady=0;c.ipadx=0;c.fill = GridBagConstraints.HORIZONTAL;
		//0.1- BACK
		c.gridx=0;c.gridy=1;c.weightx=0.5;
		button= new JButton("BACK");
		button.setName("BACK");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getControl().setLayout(0);
			}
		});
		button.addMouseListener(new trackMouse());
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		pane.add(button, c);
		//1.1 - SELEZIONA
		c.gridx=1;c.gridy=1;c.weightx=0.5;c.ipady=0;
		button= new JButton("SELEZIONA");
		button.setName("SELEZIONA");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		button.addMouseListener(new trackMouse());
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		pane.add(button, c);
		//2.1 - CRIPTA
		c.gridx=2;c.gridy=1;c.weightx=0.5;c.ipady=0;
		button= new JButton("CRIPTA");
		button.setName("CRIPTA");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//getPane().setVisible(false);
			}
		});
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		pane.add(button, c);
		//3.1 - 
		c.gridx=3;c.gridy=1;c.weightx=0.5;c.ipady=0;
		button= new JButton("REFRESH");
		button.setName("REFRESH");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//getPane().setVisible(false);
			}
		});
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		pane.add(button, c);
		//4.1 - 
		c.gridx=4;c.gridy=1;c.weightx=0.5;;c.ipady=0;
		button= new JButton("-");
		button.setName("-");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//getPane().setVisible(false);
			}
		});
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
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

	private void setViewer(MagickViewer viewer) {
		this.viewer = viewer;
	}
	
	public void setViewer(String path){
		try {
			MagickImage img= MagickUtility.getImage(path);
			viewer.setImage(img);
			int tmp_w= img.getDimension().width;
			int tmp_h= img.getDimension().height;
			if(tmp_w >= 800 || tmp_h >= 600)
				viewer.setSize(new Dimension(800, 600));
			else
				viewer.setSize(new Dimension(tmp_w, tmp_h));
		} catch (MagickException e) { 
			e.printStackTrace();
		}
	}

	public int getImgWidth(){
		return this.viewer.getSize().width;
	}
	
	public int getImgHeight(){
		return this.viewer.getSize().height;
	}
	
	public int getIdOcComponent(String text){
		Component[] components= getPane().getComponents();
		int i=0;
		for(Component c : components){
			if(c.getName().equals(text)){
				return i;
			}
			i++;
		}
		return -1;
	}
	//le azioni da compiere con il mouse
	private class trackMouse implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			//if(e.getComponent().getName().equals("SELEZIONA"))
				System.out.println("evento click mouse richiamato");
			double x_pos_viewer= getViewer().getLocation().getX();
			double y_pos_viewer= getViewer().getLocation().getY();
			double viewer_width= getViewer().getSize().getWidth();
			double viewer_heigth= getViewer().getSize().getHeight();
			
			if(e.getPoint().getX() >= x_pos_viewer && e.getPoint().getX() <= (x_pos_viewer + viewer_width) &&
					e.getPoint().getY() >= y_pos_viewer && e.getPoint().getY() <= (y_pos_viewer + viewer_heigth)){
				System.out.println("mouse clicked in viewer");
			}
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			if(e.getComponent().getName().equals("SELEZIONA"))
				System.out.println("evento selezione mouse richiamato da SELEZIONA");
			

			double x_pos_viewer= getViewer().getLocation().getX();
			double y_pos_viewer= getViewer().getLocation().getY();
			double viewer_width= getViewer().getSize().getWidth();
			double viewer_heigth= getViewer().getSize().getHeight();
			
			if(e.getPoint().getX() >= x_pos_viewer && e.getPoint().getX() <= (x_pos_viewer + viewer_width) &&
					e.getPoint().getY() >= y_pos_viewer && e.getPoint().getY() <= (y_pos_viewer + viewer_heigth)){
				System.out.println("mouse entered in viewer");
			}
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			Point p= e.getPoint();
			
			System.out.println("mouse pressed at " + p.getX() + ", " + p.getY());
			

			double x_pos_viewer= getViewer().getLocation().getX();
			double y_pos_viewer= getViewer().getLocation().getY();
			double viewer_width= getViewer().getSize().getWidth();
			double viewer_heigth= getViewer().getSize().getHeight();
			
			if(e.getPoint().getX() >= x_pos_viewer && e.getPoint().getX() <= (x_pos_viewer + viewer_width) &&
					e.getPoint().getY() >= y_pos_viewer && e.getPoint().getY() <= (y_pos_viewer + viewer_heigth)){
				System.out.println("mouse pressed in viewer");
			}
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class actionMouse implements MouseMotionListener{

		/* (non-Javadoc)
		 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		/* (non-Javadoc)
		 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseMoved(MouseEvent e) {
			System.out.println("mouse movede at " + e.getPoint());
			
		}
		
	}
}

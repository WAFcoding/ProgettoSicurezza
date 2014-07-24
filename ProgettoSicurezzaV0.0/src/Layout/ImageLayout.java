/**
 * 
 */
package Layout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.bouncycastle.util.Arrays;

import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Decompressor;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;

import com.google.zxing.WriterException;

import exceptions.MagickImageNullException;
import util.CryptoUtility;
import util.MagickUtility;
import util.QRCode;
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
    private Rectangle captureRectangle;
    private JLabel screenLabel;
    private BufferedImage screen;
    private BufferedImage screenCopy;
    private boolean isSelect;
    private MagickImage m_img;
    private int backLayout;
    
    //private static final String OUTPUT= "/home/pasquale/ProgettoSicurezza/covered/";
    private static final String OUTPUT= "/home/giovanni/Immagini/";
    private static final int MAX_CRIPTO_SIZE= 1000;

	public ImageLayout(LayoutControl control, Container pane, int backTo){
    	setControl(control);
    	setPane(pane);
    	setViewer(new MagickViewer());
    	setBackLayout(backTo);
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
		
		viewer.setBackground(Color.black);
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
				getControl().setLayout(getBackLayout());
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
		button.addActionListener(new encodeAction());
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		pane.add(button, c);
		//3.1 - AGGIORNA
		c.gridx=3;c.gridy=1;c.weightx=0.5;c.ipady=0;
		button= new JButton("AGGIORNA");
		button.setName("AGGIORNA");
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
	
	public void setViewer(String path) throws Exception{
		try {
			m_img= MagickUtility.getImage(path);
			viewer.setImage(m_img);
			screen= QRCode.magickImageToBufferedImage(m_img);
			
			//ViewImage view_img= new ViewImage(QRCode.magickImageToBufferedImage(img));
			//setV_img(view_img);
			if(screen != null){
				screenCopy= new BufferedImage(screen.getWidth(), screen.getHeight(), BufferedImage.TYPE_INT_RGB);
				screenLabel= new JLabel(new ImageIcon(screenCopy));
				viewer.add(screenLabel);
				repaint(screen, screenCopy);
				screenLabel.repaint();
				
				screenLabel.addMouseMotionListener(new actionMouse());
				screenLabel.addMouseListener(new trackMouse());
			}
			else{
				System.out.println("giovannino ha detto che è null");
			}
			
			int tmp_w= m_img.getDimension().width;
			int tmp_h= m_img.getDimension().height;
			if(tmp_w >= 800 || tmp_h >= 600)
				viewer.setSize(new Dimension(800, 600));
			else
				viewer.setSize(new Dimension(tmp_w, tmp_h));
		} catch (MagickException e) { 
			e.printStackTrace();
		}
	}
	
	public void repaint(BufferedImage bf1, BufferedImage bf2){

        Graphics2D g = bf2.createGraphics();
        g.drawImage(bf1,0,0, null);
        if (captureRectangle!=null) {
            g.setColor(Color.RED);
            g.draw(captureRectangle);
            g.setColor(new Color(255,255,255,150));
            g.fill(captureRectangle);
        }
        g.dispose();
	}
    
    /**
	 * @return the m_img
	 */
	public MagickImage getM_img() {
		return m_img;
	}

	/**
	 * @param m_img the m_img to set
	 */
	public void setM_img(MagickImage m_img) {
		this.m_img = m_img;
	}

	public int getImgWidth(){
		return this.viewer.getSize().width;
	}
	
	public int getImgHeight(){
		return this.viewer.getSize().height;
	}
	
	public int getIdOfComponent(String text){
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
	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}
	public int getBackLayout() {
		return backLayout;
	}

	public void setBackLayout(int backLayout) {
		this.backLayout = backLayout;
	}
	//le azioni da compiere con il mouse
	private class trackMouse implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getComponent().getName().equals("SELEZIONA")){
				setSelect(true);
			}
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			if(isSelect()){
				setSelect(false);
	            System.out.println("Rectangle: " + captureRectangle);
			}
		}
	}
	
	private class actionMouse implements MouseMotionListener{

		Point start= new Point();
		@Override
		public void mouseDragged(MouseEvent me) {
			if(isSelect()){
	            Point end = me.getPoint();
	            captureRectangle = new Rectangle(start,
	                    new Dimension(end.x-start.x, end.y-start.y));
	            repaint(screen, screenCopy);
	            screenLabel.repaint();
			}
		}

		@Override
		public void mouseMoved(MouseEvent me) {
			if(isSelect()){
	            start = me.getPoint();
	            repaint(screen, screenCopy);
	            //System.out.println("Start Point: " + start);
	            screenLabel.repaint();
			}
		}
	}
	
	public class encodeAction implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			try {
				int width= new Double(captureRectangle.getWidth()).intValue();
				int height= new Double(captureRectangle.getHeight()).intValue();
				int pos_x= new Double(captureRectangle.getX()).intValue();
				int pos_y= new Double(captureRectangle.getY()).intValue();
				MagickImage rect = MagickUtility.createRectangleImage(Color.BLACK, width, height);
				MagickImage cropped= MagickUtility.cropImage(m_img, captureRectangle);
				MagickImage covered = MagickUtility.coverWithImage(m_img, rect, pos_x, pos_y);
				//System.out.println(m_img.getFileName());
				String path= m_img.getFileName();
				String tmp= path.substring(path.lastIndexOf("/"), path.lastIndexOf("."));
				File f= new File(OUTPUT + tmp);
				f.mkdir();
				MagickUtility.saveImage(cropped, OUTPUT + "/" + tmp + "/" + tmp + "_cropped.jpg");
				MagickUtility.saveImage(covered, OUTPUT + "/" + tmp + "/" + tmp + "_covered.jpg");
				//System.out.println(m_img.getFileName());
				control.drawImage(m_img.getFileName(), getBackLayout());
				
				byte[] byte_img= MagickUtility.getMagickBytes(cropped);
				String str_byte= CryptoUtility.toBase64(byte_img);
				//System.out.println("byte_img = " + str_byte);
				//max data in qrcode 2953
				/*if(byte_img.length > MAX_CRIPTO_SIZE){
					System.out.println("Dimensione in byte dell'immagine troppo grande = " + byte_img.length);
				}
				else{*/
					//compression
					final int decompressedLength = byte_img.length;
					System.out.println("Dimensione in byte dell'immagine = " + decompressedLength);
					LZ4Compressor compressor= LZ4Factory.fastestInstance().highCompressor();
				    int maxCompressedLength = compressor.maxCompressedLength(decompressedLength);
				    byte[] compressed = new byte[maxCompressedLength];
				    int compressedLength = compressor.compress(byte_img, 0, decompressedLength, compressed, 0, maxCompressedLength);
					//System.out.println("compressed= " + new String(compressed));
					System.out.println("dimensione compressed= " + compressedLength);
				    //decompression
				  /*  LZ4FastDecompressor decompressor = LZ4Factory.fastestInstance().fastDecompressor();
				    byte[] restored = new byte[decompressedLength];
				    int compressedLength2 = decompressor.decompress(compressed, 0, restored, 0, decompressedLength);
				    // compressedLength == compressedLength2
				    System.out.println("decompressed= " + new String(restored));*/
					QRCode qr= new QRCode();
					try {
						qr.writeQRCode(new String(Arrays.copyOf(compressed,compressedLength<1000?compressedLength:1000)), QRCode.DEFAULT_WIDTH, QRCode.DEFAULT_WIDTH);
						qr.saveQRCodeToFile(OUTPUT + "/" + tmp + "/" + tmp + "_qrcode.jpg" );
					} catch (WriterException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				//}
				
			} catch (MagickException | MagickImageNullException e1) {
				
				e1.printStackTrace();
			}
		}
		
	}
}

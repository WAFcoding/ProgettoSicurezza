/**
 * 
 */
package Layout;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import util.CryptoUtility;
import util.QRCode;

/**
 * @author "Pasquale Verlotta - pasquale.verlotta@gmail.com"
 *
 */
public class WriteLayout implements GeneralLayout{

	private LayoutControl control;
	private Container pane;

	private JTextArea area;
	private String text;
	
	public WriteLayout(LayoutControl control, Container pane){
		setControl(control);
		setPane(pane);
		text= "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris nec malesuada enim, at elementum urna. Mauris bibendum massa nec tincidunt dapibus. Suspendisse malesuada sed risus auctor vestibulum. Cras rutrum, dui sed tristique ullamcorper, tortor felis pulvinar mi, pretium suscipit libero purus non urna. Phasellus congue venenatis libero, eget feugiat ipsum commodo eget. Sed elementum, nulla eu gravida vehicula, nunc enim iaculis enim, eget mattis enim mi at risus. Vestibulum vulputate quam fringilla, sollicitudin elit et, pharetra tortor. Sed elementum, quam a dignissim tempus, lectus justo aliquet augue, nec convallis est metus ac lectus."+

"Donec gravida lorem elit, eu tempus eros aliquet in. Nulla facilisi. Aenean sed dictum urna. Aenean vehicula purus vitae lacus egestas, et dignissim libero egestas. Nunc nec rhoncus est. Duis tempus nisi nec est elementum sodales. Fusce vitae nisl id justo porta facilisis id non tortor. Vivamus eleifend eleifend neque, ut ultrices justo dictum non. Integer nec eros condimentum, mattis ipsum id, dignissim nibh. Pellentesque non ante nisi. Curabitur pellentesque turpis dignissim massa pretium malesuada. Curabitur fringilla felis vitae laoreet pharetra. Pellentesque ut libero hendrerit tellus sagittis dictum." + 

"Morbi dictum aliquam lobortis. Suspendisse scelerisque quam sit amet sem gravida malesuada. Phasellus eu enim sagittis, adipiscing magna a, condimentum risus. Quisque vitae mi sed turpis ultricies fringilla. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Integer placerat dui ut facilisis rutrum. Donec a nibh vitae ipsum eleifend cursus in pellentesque nunc. Integer placerat ipsum vel risus facilisis rhoncus. Duis augue dolor, imperdiet sed sollicitudin quis, feugiat tempor nulla. Integer commodo auctor elit, nec consequat est auctor vel. Ut a ante mi.";
		area= new JTextArea(text);
		area.setVisible(true);
	}
	
	@Override
	public void addComponentsToPane() {
		
		
		//area.setPreferredSize(new Dimension(300, 200));
		//area.setMaximumSize(new Dimension(600, 400));
		//area.setAutoscrolls(true);
		area.setMargin(new Insets(5, 5, 5, 5));
		area.setWrapStyleWord(true);
		area.setLineWrap(true);
		JScrollPane scroll_pane= new JScrollPane(area);
		scroll_pane.setPreferredSize(new Dimension(600, 400));
		scroll_pane.setMaximumSize(new Dimension(600, 400));
		scroll_pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
        
        //inserimento pulsanti
        pane.removeAll();
		pane.setLayout(new GridBagLayout());
		pane.setSize(800, 600);
		
		int posx= 0, posy= 0;
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.anchor= GridBagConstraints.WEST;
		c.insets= new Insets(10, 10, 10, 10);
		// 0.0 - text area
		c.gridx= posx;c.gridy= posy;c.gridheight= 4;c.gridwidth= 6;c.weightx= 1;c.weighty=1;
		c.fill = GridBagConstraints.BOTH;
		pane.add(scroll_pane, c);
		//7.0 - BACK
		JButton button= new JButton("BACK");
		posx=7;posy= 0;
		c.gridheight= 1;c.gridwidth= 1;c.weightx= 0;c.weighty=0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx= posx;c.gridy= posy;
		c.ipadx= 0;c.ipady= 0;
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				control.setLayout(0);
			}
		});
		pane.add(button, c);
		//7.1 - SELEZIONA
		button= new JButton("CRIPTA");
		posy++;
		c.fill = GridBagConstraints.NONE;
		c.gridx= posx;c.gridy= posy;
		c.ipadx= 0;c.ipady= 0;
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String selected_text= area.getSelectedText();
				System.out.println(selected_text);
				if(selected_text != null){
					int start= area.getSelectionStart();
					int end= area.getSelectionEnd();
					String txt= area.getText();
					String left_part= txt.substring(0, start);
					String right_part= txt.substring(end);
					String middle_part= "##########";
					
					//FIXME va criptata anche la posizione del testo e poi mandata al qr code altrimenti
					//non si può associare il qr code alla giusta posizione
						
					setAreaText(left_part + middle_part + right_part); 
					
					try {
						byte[] encripted= CryptoUtility.encrypt(CryptoUtility.CRYPTO_ALGO.AES, selected_text, "giovannino");
						String enc= new String(encripted);
						System.out.println("Selezione criptata: " + enc);
						
						QRCode qr = new QRCode();
						qr.writeQRCode(enc, QRCode.DEFAULT_WIDTH, QRCode.DEFAULT_HEIGHT);
						String path= "/home/pasquale/ProgettoSicurezza/qrcode.jpg";
						qr.saveQRCodeToFile(path);
						
						EventQueue.invokeLater(new Runnable() {
							
							@Override
							public void run() {
								LayoutControl new_control= new LayoutControl();
								new_control.draw("/home/pasquale/ProgettoSicurezza/qrcode.jpg", 0);
								
							}
						});
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//area.cut();
			}
		});
		pane.add(button, c);
		
	}
	
	/**
	 * @return the control
	 */
	public LayoutControl getControl() {
		return control;
	}

	/**
	 * @param control the control to set
	 */
	public void setControl(LayoutControl control) {
		this.control = control;
	}

	/**
	 * @return the pane
	 */
	public Container getPane() {
		return pane;
	}

	/**
	 * @param pane the pane to set
	 */
	public void setPane(Container pane) {
		this.pane = pane;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	public void setAreaText(String text){
		setText(text);
		area.setText(text);
	}
	
}

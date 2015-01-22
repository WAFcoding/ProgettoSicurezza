/**
 * 
 */
package layout;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

import util.CryptoUtility;
import util.PDFUtil;
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
	private String output_folder;
	private String name_file;
	private File currentFile;
	private boolean isAlreadyConfigured;
	private boolean receiverSetted;

	public WriteLayout(LayoutControl control, Container pane){
		setControl(control);
		setPane(pane);
		/*text= "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris nec malesuada enim, at elementum urna. Mauris bibendum massa nec tincidunt dapibus. Suspendisse malesuada sed risus auctor vestibulum. Cras rutrum, dui sed tristique ullamcorper, tortor felis pulvinar mi, pretium suscipit libero purus non urna. Phasellus congue venenatis libero, eget feugiat ipsum commodo eget. Sed elementum, nulla eu gravida vehicula, nunc enim iaculis enim, eget mattis enim mi at risus. Vestibulum vulputate quam fringilla, sollicitudin elit et, pharetra tortor. Sed elementum, quam a dignissim tempus, lectus justo aliquet augue, nec convallis est metus ac lectus."+

"Donec gravida lorem elit, eu tempus eros aliquet in. Nulla facilisi. Aenean sed dictum urna. Aenean vehicula purus vitae lacus egestas, et dignissim libero egestas. Nunc nec rhoncus est. Duis tempus nisi nec est elementum sodales. Fusce vitae nisl id justo porta facilisis id non tortor. Vivamus eleifend eleifend neque, ut ultrices justo dictum non. Integer nec eros condimentum, mattis ipsum id, dignissim nibh. Pellentesque non ante nisi. Curabitur pellentesque turpis dignissim massa pretium malesuada. Curabitur fringilla felis vitae laoreet pharetra. Pellentesque ut libero hendrerit tellus sagittis dictum." + 

"Morbi dictum aliquam lobortis. Suspendisse scelerisque quam sit amet sem gravida malesuada. Phasellus eu enim sagittis, adipiscing magna a, condimentum risus. Quisque vitae mi sed turpis ultricies fringilla. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Integer placerat dui ut facilisis rutrum. Donec a nibh vitae ipsum eleifend cursus in pellentesque nunc. Integer placerat ipsum vel risus facilisis rhoncus. Duis augue dolor, imperdiet sed sollicitudin quis, feugiat tempor nulla. Integer commodo auctor elit, nec consequat est auctor vel. Ut a ante mi.";
		*/area= new JTextArea("");
		area.setVisible(true);
		this.isAlreadyConfigured= false;
		this.receiverSetted= false;
	}
	
	@Override
	public void addComponentsToPane() {
		
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
		c.gridx= posx;c.gridy= posy;c.gridheight= 8;c.gridwidth= 6;c.weightx= 1;c.weighty=1;
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
				control.setLayout("PRIMARY");
			}
		});
		pane.add(button, c);
		//7.1 - SELEZIONA DESTINATARIO
		button= new JButton("DESTINATARIO");
		posy++;
		c.gridx= posx;c.gridy= posy;
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] options= {"Utente specifico", "Gruppo di utenti"};
				int choice= JOptionPane.showOptionDialog(pane, "Come criptare il documento", "Attenzione", JOptionPane.YES_NO_OPTION, 
						JOptionPane.QUESTION_MESSAGE, null, options, "Seleziona");
				//utente specifico
				if(choice == JOptionPane.YES_OPTION){
					setReceiverSetted(true);
					//control.setLayout("RECEIVER");
					//control.setReceiverSingleUSer(true);
				}
				//gruppo di utenti
				else if(choice == JOptionPane.NO_OPTION){
					setReceiverSetted(true);
					//control.setLayout("RECEIVER");
					//control.setReceiverSingleUSer(false);
				}
			}
		});
		pane.add(button, c);
		
		//7.1 - CRIPTA
		button= new JButton("CRIPTA");
		posy++;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx= posx;c.gridy= posy;
		c.ipadx= 0;c.ipady= 0;
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(receiverSetted){
					String selected_text= area.getSelectedText();
					int cursor_position= area.getCaretPosition() - selected_text.length();
					System.out.println(selected_text);
					selected_text= cursor_position + "_" + selected_text;
					System.out.println(selected_text);
					System.out.println("La posizione del cursore nel testo è: " + cursor_position);
					if(selected_text != null){
						int start= area.getSelectionStart();
						int end= area.getSelectionEnd();
						int text_lenght= end - start;
						System.out.println("text_lengt = " + text_lenght);

						String txt= area.getText();
						String left_part= txt.substring(0, start);
						String right_part= txt.substring(end);
						String middle_part= "##########";

						setAreaText(left_part + middle_part + right_part); 

						try {
							byte[] encripted= CryptoUtility.encrypt(CryptoUtility.CRYPTO_ALGO.AES, selected_text, "giovannino");
							String enc= new String(encripted);
							System.out.println("lunghezza criptata = " + enc.length());
							System.out.println("Selezione criptata: " + enc);

							QRCode qr = new QRCode();
							qr.writeQRCode(enc, QRCode.DEFAULT_WIDTH, QRCode.DEFAULT_HEIGHT);
							configurePath();
							//--------------------------------------------------------------
							//String qrcode_file= getOutput_folder()+"/"+getNameFile()+".jpg";
							String qrcode_file= "/home/pasquale/ProgettoSicurezza"+"/"+getNameFile()+".jpg";
							int i=1;
							while(checkExists(qrcode_file)){
								qrcode_file= qrcode_file.substring(0, qrcode_file.lastIndexOf("_")+1);
								qrcode_file= qrcode_file + i + ".jpg";
								i++;
							}
							qr.saveQRCodeToFile(qrcode_file);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				else{
					JOptionPane.showMessageDialog(pane, "Prima selezionare il destinatario", "attenzione", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		pane.add(button, c);
		//7.2 - NUOVO
		button= new JButton("NUOVO");
		posy++;
		c.gridx= posx;c.gridy= posy;
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				area.setText("");
				System.out.println("cancellato contenuto JTextArea");

				JFileChooser file_chooser= new JFileChooser();
				file_chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int choose= file_chooser.showDialog(null, "SELEZIONA");
				if(choose == JFileChooser.APPROVE_OPTION){
					String name_file= JOptionPane.showInputDialog(getPane(), "inserisci il nome del file\nsenza estensione");
					setOutput_folder(file_chooser.getSelectedFile().getAbsolutePath()+"/");
					setNameFile(name_file+"_0");
					System.out.println("Il percorso scelto è: " + getOutput_folder());
					
				}
				else if(choose == JFileChooser.CANCEL_OPTION){
					System.out.println("annullato");
				}
			}
		});
		pane.add(button, c);
		//7.3 - SALVA
		button= new JButton("SALVA");
		posy++;
		c.gridx= posx;c.gridy= posy;
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser file_chooser= new JFileChooser(getOutput_folder());
				file_chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int choose= file_chooser.showDialog(null, "SELEZIONA");
				String path="";
				if(choose == JFileChooser.APPROVE_OPTION){
					String name_file= JOptionPane.showInputDialog(getPane(), "inserisci il nome del file\nsenza estensione", getNameFile());
					setOutput_folder(file_chooser.getSelectedFile().getAbsolutePath() +  "/");
					File f= new File(path);
					f.mkdir();
					path= getOutput_folder() + name_file +  ".txt";
					System.out.println("Il percorso scelto è: " + path);
					QRCode.saveFile(path, area.getText());//funzione di utilità sta in QRCode solo per comodità
					try {
						String pat_pdf= getOutput_folder() + name_file + ".pdf";
						PDFUtil.create(pat_pdf);
						if(PDFUtil.open()){
							PDFUtil.addText(area.getText(), 40, 400, 0);
							PDFUtil.close();
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (DocumentException e) {
						e.printStackTrace();
					}
					System.out.println("File salvato in: " + path);
					
				}
				else if(choose == JFileChooser.CANCEL_OPTION){
					System.out.println("annullato");
				}
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

	public String getOutput_folder() {
		return output_folder;
	}

	public void setOutput_folder(String output_folder) {
		this.output_folder = output_folder;
	}
	
	public String getNameFile(){
		return this.name_file;
	}
	
	public void setNameFile(String name){
		this.name_file= name;
	}

	public File getCurrentFile() {
		return currentFile;
	}

	public void setCurrentFile(File currentFile) {
		this.currentFile = currentFile;
		String path= this.currentFile.getAbsolutePath();
		String name= path.substring(path.lastIndexOf("/")+1, path.lastIndexOf("."))+"_0";
		path= path.substring(0, path.lastIndexOf("/"));
		path= path.substring(0, path.lastIndexOf("/"));
		path+= "/ProgettoSicurezza/";
		File f= new File(path);
		if(!f.exists()){
			f.mkdir();
		}
		setNameFile(name);
		setOutput_folder(path);
	}
	
	/**
	 * @return the isAlreadyConfigured
	 */
	public boolean isAlreadyConfigured() {
		return isAlreadyConfigured;
	}

	/**
	 * @param isAlreadyConfigured the isAlreadyConfigured to set
	 */
	public void setAlreadyConfigured(boolean isAlreadyConfigured) {
		this.isAlreadyConfigured = isAlreadyConfigured;
	}
	
	public boolean checkExists(String path){
		return new File(path).exists();
	}
	
	public void configurePath(){

		if(!isAlreadyConfigured()){
			System.out.println("output folder= " + getOutput_folder());
			System.out.println("output file name= " + getNameFile());
			setOutput_folder(getOutput_folder() + getNameFile() + "/");
			System.out.println("output folder= " + getOutput_folder());
			File f= new File(getOutput_folder());
			if(!f.exists()){
				f.mkdir();
			}
			setAlreadyConfigured(true);
		}
	}

	public boolean isReceiverSetted() {
		return receiverSetted;
	}

	public void setReceiverSetted(boolean receiverSetted) {
		this.receiverSetted = receiverSetted;
	}
}

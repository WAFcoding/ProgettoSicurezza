/**
 * 
 */
package layout;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import usersManagement.User;
import util.CryptoUtility;
import util.CryptoUtility.CRYPTO_ALGO;
import util.CryptoUtility.HASH_ALGO;
import util.PDFUtil;
import util.QRCode;

import com.itextpdf.text.DocumentException;

/**
 * @author "Pasquale Verlotta - pasquale.verlotta@gmail.com"
 *
 */
public class WriteLayout implements GeneralLayout{

	private LayoutControl control;
	private Container pane;

	private JTextArea area;
	private JTextField field_title, field_author, field_receiver, field_info;
	private String text;
	private String output_folder;
	private String name_file;
	private File currentFile;
	private boolean isAlreadyConfigured;
	private boolean receiverSetted;
	private User user;
	private ArrayList<String> qrcodes_path;
	private int current_qrcode= 0;

	public WriteLayout(LayoutControl control, Container pane){
		setControl(control);
		setPane(pane);
		area= new JTextArea("");
		area.setVisible(true);
		this.isAlreadyConfigured= false;
		this.receiverSetted= false;
		
		this.user= control.getUser_manager().getActualUser();
		
		qrcodes_path= new ArrayList<String>(10);
		for(int i=0; i<10; i++){
			qrcodes_path.add("");
		}
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
		c.anchor= GridBagConstraints.WEST;
		c.insets= new Insets(10, 10, 10, 10);
		//0.0 - title
		field_title= new JTextField("Title");
		field_title.addFocusListener(new FocusOnClick(field_title, "Title"));
		c.gridx= posx;c.gridy= posy;c.gridheight= 1;c.gridwidth= 3;c.weightx= 1;c.weighty=0;
		c.fill = GridBagConstraints.HORIZONTAL;
		pane.add(field_title, c);
		//0.1 - author
		posy++;
		field_author= new JTextField("Author");
		field_author.addFocusListener(new FocusOnClick(field_author, "Author"));
		c.gridx= posx;c.gridy= posy;c.gridheight= 1;c.gridwidth= 6;c.weightx= 1;c.weighty=0;
		pane.add(field_author, c);
		//0.2 - receiver
		posy++;
		field_receiver= new JTextField("Receiver");
		field_receiver.addFocusListener(new FocusOnClick(field_receiver, "Receiver"));
		c.gridx= posx;c.gridy= posy;c.gridheight= 1;c.gridwidth= 6;c.weightx= 1;c.weighty=0;
		pane.add(field_receiver, c);
		//0.3 - info
		posy++;
		field_info= new JTextField("Info");
		field_info.addFocusListener(new FocusOnClick(field_info, "Info"));
		c.gridx= posx;c.gridy= posy;c.gridheight= 1;c.gridwidth= 6;c.weightx= 1;c.weighty=0;
		pane.add(field_info, c);
		// 0.2 - text area
		posy++;
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
		button.addActionListener(new SelectReceiverAction());
		pane.add(button, c);
		
		//7.1 - CRIPTA
		button= new JButton("CRIPTA");
		posy++;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx= posx;c.gridy= posy;
		c.ipadx= 0;c.ipady= 0;
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		button.addActionListener(new EncodeAction());
		pane.add(button, c);
		//7.2 - NUOVO
		button= new JButton("NUOVO");
		posy++;
		c.gridx= posx;c.gridy= posy;
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		button.addActionListener(new NewAction());
		pane.add(button, c);
		//7.3 - SALVA
		button= new JButton("SALVA");
		posy++;
		c.gridx= posx;c.gridy= posy;
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		button.addActionListener(new SaveAction());
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
	
	private class SelectReceiverAction implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String[] options= {"Utente specifico", "Gruppo di utenti"};
			int choice= JOptionPane.showOptionDialog(pane, "Come criptare il documento", "Attenzione", JOptionPane.YES_NO_OPTION, 
					JOptionPane.QUESTION_MESSAGE, null, options, "Seleziona");
			//utente specifico
			if(choice == JOptionPane.YES_OPTION){
				setReceiverSetted(true);
				control.setLayout("RECEIVER");
				control.setReceiverSingleUSer(true);
			}
			//gruppo di utenti
			else if(choice == JOptionPane.NO_OPTION){
				setReceiverSetted(true);
				control.setLayout("RECEIVER");
				control.setReceiverSingleUSer(false);
			}
		}
	}

	private class EncodeAction implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(receiverSetted){
			//if(true){
				String selected_text= area.getSelectedText();
				int cursor_position= area.getCaretPosition() - selected_text.length();
				System.out.println(selected_text);
				//format for text in qrcode= progressiveNumber_cursorPosition_text
				selected_text= current_qrcode + "_" + cursor_position + "_" + selected_text;
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
						//TODO prendere la chiave pubblica per criptare dal controller
						byte[] encripted= CryptoUtility.encrypt(CryptoUtility.CRYPTO_ALGO.AES, selected_text, "giovannino");
						String enc= new String(encripted);
						System.out.println("lunghezza criptata = " + enc.length());
						System.out.println("Selezione criptata: " + enc);

						QRCode qr = new QRCode();
						qr.writeQRCode(enc, QRCode.DEFAULT_WIDTH, QRCode.DEFAULT_HEIGHT);
						//configurePath();

						//String qrcode_file= getOutput_folder()+"/"+getNameFile()+".jpg";
						//TODO prendere il percorso per salvare dalle preferenze dell'utente
						//String qrcode_file= "/home/pasquale/ProgettoSicurezza/qrcode_0.jpg";
						String qrcode_file= control.getUser_manager().getActualUser().getDir_out() + "qrcode_0.jpg";
						int i=1;
						while(checkExists(qrcode_file)){
							qrcode_file= qrcode_file.substring(0, qrcode_file.lastIndexOf("_")+1);
							qrcode_file= qrcode_file + i + ".jpg";
							i++;
						}
						qr.saveQRCodeToFile(qrcode_file);
						qrcodes_path.set(current_qrcode, qrcode_file);
						current_qrcode++;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			else{
				JOptionPane.showMessageDialog(pane, "Prima selezionare il destinatario", "attenzione", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	private class NewAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			area.setText("");
			System.out.println("cancellato contenuto JTextArea");

			JFileChooser file_chooser= new JFileChooser();
			file_chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
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
	}

	private class SaveAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			//TODO prendere la cartella dalle preferenze dell'utente loggato
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
				String sign_path= getOutput_folder() + "sign.jpg";
				System.out.println("Il percorso scelto è: " + path);
				//QRCode.saveFile(path, area.getText());//funzione di utilità sta in QRCode solo per comodità//
				//TODO creare il pdf con i dati
				//TODO creare la signature
				try {
					String pat_pdf= getOutput_folder() + name_file + ".pdf";
					PDFUtil.create(pat_pdf);
					if(PDFUtil.open()){
						
						String title= field_title.getText();
						String author= field_author.getText();
						String text= area.getText();
						//hash del testo criptato
						String signature= CryptoUtility.hash(HASH_ALGO.SHA1, text);
						//firma dell'hash
						byte[] enc= CryptoUtility.encrypt(CRYPTO_ALGO.AES, signature, control.getUser_manager().getActualUser().getPrivateKey());
						QRCode qr= new QRCode();
						qr.writeQRCode(new String(enc), QRCode.DEFAULT_WIDTH, QRCode.DEFAULT_HEIGHT);
						qr.saveQRCodeToFile(sign_path);
						
						String[] info= {field_info.getText(), "", "", ""};
						String[] qrcodes= {"", "", "", "", "", "", "", "", "", ""};
						for(int i=0; i<10; i++){
							String tmp_path= qrcodes_path.get(i);
							if(tmp_path != null)
								qrcodes[i]= tmp_path;
							else
								qrcodes[i]= "";
						}

						PDFUtil.createDocument(title, author, text, sign_path, info, qrcodes);
						PDFUtil.close();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (DocumentException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				finally{
					File tmp_sign= new File(sign_path);
					tmp_sign.delete();
					for(String s : qrcodes_path){
						File tmp_file= new File(s);
						tmp_file.delete();
					}
				}
				System.out.println("File salvato in: " + path);

			}
			else if(choose == JFileChooser.CANCEL_OPTION){
				System.out.println("annullato");
			}
		}
	}
	
	private class FocusOnClick implements FocusListener{
		
		private JTextField field;
		private String text;

		public FocusOnClick(JTextField f, String t){
			this.field= f;
			this.text= t;
		}
		
		@Override
		public void focusGained(FocusEvent e) {
        	if(field.getText().equals(text))
        		field.setText("");
		}

		@Override
		public void focusLost(FocusEvent e) {
			if(field.getText().equals(""))
				field.setText(text);
		}
		
	}
		
}

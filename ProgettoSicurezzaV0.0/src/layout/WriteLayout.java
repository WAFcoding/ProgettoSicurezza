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
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import magick.MagickImage;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import usersManagement.User;
import util.CryptoUtility;
import util.CryptoUtility.ASYMMCRYPTO_ALGO;
import util.CryptoUtility.CRYPTO_ALGO;
import util.CryptoUtility.HASH_ALGO;
import util.ImagePHash;
import util.MagickUtility;
import util.PDFUtil;
import util.QRCode;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;

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
	private User user_sender;
	private User user_receiver;
	private int level_receiver;
	private ArrayList<String> qrcodes_path;
	private int current_qrcode= 0;

	public WriteLayout(LayoutControl control, Container pane){
		setControl(control);
		setPane(pane);
		area= new JTextArea("");
		area.setVisible(true);
		this.isAlreadyConfigured= false;
		this.receiverSetted= false;
		
		this.user_sender= control.getUser_manager().getActualUser();
		
		qrcodes_path= new ArrayList<String>(10);
		for(int i=0; i<10; i++){
			qrcodes_path.add("");
		}
		setOutput_folder(control.getUser_manager().getActualUser().getDir_out() +  "/");
	}
	
	@Override
	public void addComponentsToPane() {
		
		setCurrentFile(new File(control.getWLayoutCurrentFilePath()));
		try {
			BufferedReader buff= new BufferedReader(new FileReader(currentFile.getAbsolutePath()));
			String tmp= buff.readLine();
			String text= "";
			while(tmp != null){
				text= text + tmp;
				tmp= buff.readLine();
			}
			setAreaText(text);
			buff.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String tmp_user= "";
		
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
		field_title.setDocument(new JTextFieldLimit(18));
		c.gridx= posx;c.gridy= posy;c.gridheight= 1;c.gridwidth= 3;c.weightx= 1;c.weighty=0;
		c.fill = GridBagConstraints.HORIZONTAL;
		pane.add(field_title, c);
		//0.1 - author
		tmp_user= control.getUser_manager().getActualUser().getName() + " " + control.getUser_manager().getActualUser().getSurname() ;
		posy++;
		field_author= new JTextField(tmp_user);
		field_author.addFocusListener(new FocusOnClick(field_author, "Author"));
		field_author.setEditable(false);
		c.gridx= posx;c.gridy= posy;c.gridheight= 1;c.gridwidth= 6;c.weightx= 1;c.weighty=0;
		pane.add(field_author, c);
		//0.2 - receiver
		posy++;
		String tmp_receiver= "";
		if(control.isReceiverSingleUser()){
			if(user_receiver == null) tmp_receiver= "Receiver";
			else tmp_receiver= user_receiver.getName() + " " + user_receiver.getSurname();
		}
		else{
			level_receiver= control.getLevelForEncryptDecrypt();
			//System.out.println("level= " + level_receiver);
			tmp_receiver= "Level " + level_receiver;
		}
		field_receiver= new JTextField(tmp_receiver);
		field_receiver.addFocusListener(new FocusOnClick(field_receiver, tmp_receiver));
		field_receiver.setEditable(false);
		c.gridx= posx;c.gridy= posy;c.gridheight= 1;c.gridwidth= 6;c.weightx= 1;c.weighty=0;
		pane.add(field_receiver, c);
		//0.3 - info
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		tmp_user= dateFormat.format(date);
		posy++;
		field_info= new JTextField(tmp_user);
		field_info.addFocusListener(new FocusOnClick(field_info, tmp_user));
		field_info.setEditable(false);
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
				control.setLayout("ENCODE");
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
		/*path= path.substring(0, path.lastIndexOf("/"));
		path= path.substring(0, path.lastIndexOf("/"));
		path+= "/ProgettoSicurezza/";
		File f= new File(path);
		if(!f.exists()){
			f.mkdir();
		}*/
		setNameFile(name);
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
			//System.out.println("output folder= " + getOutput_folder());
			//System.out.println("output file name= " + getNameFile());
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
	
	/**
	 * @return the user_sender
	 */
	public User getUser_sender() {
		return user_sender;
	}

	/**
	 * @param user_sender the user_sender to set
	 */
	public void setUser_sender(User user_sender) {
		this.user_sender = user_sender;
	}

	/**
	 * @return the user_receiver
	 */
	public User getUser_receiver() {
		return user_receiver;
	}

	/**
	 * @param user_receiver the user_receiver to set
	 */
	public void setUser_receiver(User user_receiver) {
		this.user_receiver = user_receiver;
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
				control.setReceiverSingleUSer(true);
				control.setLayout("RECEIVER");
			}
			//gruppo di utenti
			else if(choice == JOptionPane.NO_OPTION){
				setReceiverSetted(true);
				control.setReceiverSingleUSer(false);
				control.setLayout("RECEIVER");
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
						String enc= "";
						if(control.isReceiverSingleUser()){
							user_receiver= control.getUserForEncryptOrDecrypt();
							PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(CryptoUtility.fromBase64(user_receiver.getPublicKey())));
							byte[] encripted= CryptoUtility.asymm_encrypt(ASYMMCRYPTO_ALGO.RSA, selected_text.getBytes(), publicKey);
							enc= new String(encripted);
						}
						else{
							level_receiver= control.getLevelForEncryptDecrypt();
							String tmp_level_key="";
							byte[] encripted= selected_text.getBytes();
							for(int i=1; i<= level_receiver;i++){
								tmp_level_key= control.getKeyByLevel(level_receiver);
								System.out.println("tmp_level_key = " + tmp_level_key);
								encripted= CryptoUtility.encrypt(CryptoUtility.CRYPTO_ALGO.AES, encripted, tmp_level_key);
							}
							enc= new String(encripted);
						}
						System.out.println("lunghezza criptata = " + enc.length());
						System.out.println("Selezione criptata: " + enc);

						QRCode qr = new QRCode();
						qr.writeQRCode(enc, QRCode.DEFAULT_WIDTH, QRCode.DEFAULT_HEIGHT);
						//configurePath();

						String qrcode_file= control.getUser_manager().getActualUser().getDir_out() + "/"+"qrcode_0.jpg";
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

			String name_file= JOptionPane.showInputDialog(getPane(), "inserisci il nome del file\nsenza estensione", "");
			String sign_path= getOutput_folder() + "sign.jpg";
			String infQrCodePath= getOutput_folder() + "infoQrCode.jpg";
			//System.out.println("Il percorso scelto è: " + path);
			String pat_pdf= getOutput_folder() + name_file + ".pdf";
			String pdf_to_img= getOutput_folder() + name_file +".png";
			String img_cropped= getOutput_folder() + "cropped.png";
			String qrcode_tosave= getOutput_folder() + "qrcode.png";
			try {
				PDFUtil.create(pat_pdf);
				if(PDFUtil.open()){

					String title= field_title.getText();
					String author= field_author.getText();
					String author_name= author.substring(0, author.indexOf(" "));
					String receiver= field_receiver.getText();
					String text= area.getText();

					//info per la decodifica in chiaro
					String infoQrCode= "Title: " + title + "\n" +
										"Sender: " + author + "\n" +
										"Sender uid: " + author_name.toLowerCase() + "_" +user_sender.getServer_uid() +"\n"+
										"Receiver: " + receiver;
					QRCode qrInfo= new QRCode();
					qrInfo.writeQRCode(infoQrCode, QRCode.DEFAULT_WIDTH, QRCode.DEFAULT_HEIGHT);
					qrInfo.saveQRCodeToFile(infQrCodePath);

					String[] info= {receiver, field_info.getText()};
					String[] qrcodes= {"", "", "", "", "", "", "", "", "", ""};
					for(int i=0; i<10; i++){
						String tmp_path= qrcodes_path.get(i);
						if(tmp_path != null)
							qrcodes[i]= tmp_path;
						else
							qrcodes[i]= "";
					}

					PDFUtil.createDocument(title, author, text, infQrCodePath, info, qrcodes);
					PDFUtil.close();

					//prendo il documento
					PDDocument document= PDDocument.load(pat_pdf);
					PDPage page= (PDPage) document.getDocumentCatalog().getAllPages().get(0);
					//converto in immagine
					BufferedImage img = page.convertToImage(BufferedImage.TYPE_BYTE_BINARY, 400);
					int resx= img.getWidth();
					int resy= img.getHeight();
					//salvo l'immagine
					File outputfile = new File(pdf_to_img);
					ImageIO.write(img, "jpg", outputfile);
					//croppo l'immagine
					int pagesizex= new Double(PageSize.A4.getWidth()).intValue();
					int pagesizey= new Double(PageSize.A4.getHeight()).intValue();
					MagickImage magick= MagickUtility.getImage(pdf_to_img);
					int x = MagickUtility.resizeX(1, resx, pagesizex);
					int y= MagickUtility.resizeY(160, resy, pagesizey);
					int width= MagickUtility.resizeX(pagesizex, resx, pagesizex);
					int height= MagickUtility.resizeY(pagesizey - 160, resy, pagesizey);
					MagickImage crop= MagickUtility.cropImage(magick, x, y, height, width);
					MagickUtility.saveImage(crop, img_cropped);
					//faccio hash
					ImagePHash hash = new ImagePHash(42, 5);
					String signature = hash.getHash(new FileInputStream(img_cropped));
					//firma dell'hash
					PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(CryptoUtility.fromBase64(user_sender.getPrivateKey())));
					byte[] enc= CryptoUtility.asymm_encrypt(ASYMMCRYPTO_ALGO.RSA, signature.getBytes(), privateKey);
					String encripted= new String(CryptoUtility.toBase64(enc));
					encripted= encripted.replace("\n", "");
					//creo il qrcode
					QRCode qr= new QRCode();
					int qr_width= MagickUtility.resizeX(QRCode.DEFAULT_WIDTH, resx, pagesizex);
					int qr_height= MagickUtility.resizeY(QRCode.DEFAULT_HEIGHT, resy, pagesizey);
					qr.writeQRCode(encripted, qr_width , qr_height);
					qr.saveQRCodeToFile(qrcode_tosave);
					//copro l'immagine
					MagickImage qrimg= MagickUtility.getImage(qrcode_tosave);
					int coverx= MagickUtility.resizeX(pagesizex - 111, resx, pagesizex);
					int covery= MagickUtility.resizeY(11, resy, pagesizey);
					MagickImage buffImg= MagickUtility.getImage(pdf_to_img);
					MagickImage coveredimg= MagickUtility.coverWithImage(buffImg, qrimg, coverx, covery);
					MagickUtility.saveImage(coveredimg, pdf_to_img);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				File tmp= new File(sign_path);
				tmp.delete();
				tmp= new File(infQrCodePath);
				tmp.delete();
				for(String s : qrcodes_path){
					tmp= new File(s);
					tmp.delete();
				}
				tmp= new File(pat_pdf);
				tmp.delete();
				tmp= new File(img_cropped);
				tmp.delete();
				tmp= new File(qrcode_tosave);
				tmp.delete();
				
			}
			setReceiverSetted(false);
			control.setLayout("ENCODE");
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
	
	private class JTextFieldLimit extends PlainDocument {
		private static final long serialVersionUID = 4592979636590560189L;
		private int limit;
		JTextFieldLimit(int limit) {
			super();
			this.limit = limit;
		}

		public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
			if (str == null)
				return;

			if ((getLength() + str.length()) <= limit) {
				super.insertString(offset, str, attr);
			}
		}
	}
		
}

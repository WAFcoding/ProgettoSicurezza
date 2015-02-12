/**
 * Pasquale Verlotta - pasquale.verlotta@gmail.com
 * ProgettoSicurezzaV0.0 - 10/feb/2015
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
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import magick.MagickImage;
import usersManagement.User;
import util.CryptoUtility;
import util.CryptoUtility.ASYMMCRYPTO_ALGO;
import util.ImagePHash;
import util.MagickUtility;
import util.QRCode;
import connection.ConnectionFactory;
import connection.KeyDistributionClient;

/**
 * @author pasquale
 *
 */
public class ReadLayout implements GeneralLayout {
	
	public final static String keystore_pwd= "progettoSII";
	public final static String url= "localhost";//TODO UserManager: inserire il url nei settings

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
	private User actualUser;
	private String sender_uid;
	private ArrayList<String> qrcodes_path;
	private int current_qrcode = 0;
	private ArrayList<MagickImage> filePart;
	
	private String title, author, info, receiver;
	private boolean isSingleUser, error;

	private String decode_key;
	private ArrayList<String> level_key;

	public ReadLayout(LayoutControl control, Container pane) {
		this.control = control;
		this.pane = pane;

		filePart = new ArrayList<MagickImage>();
		title= "";
		author= "";
		info= "";
		text= "";
		receiver= "";
		sender_uid= "";
		isSingleUser= true;
		
		actualUser= control.getUser_manager().getActualUser();
		error= false;
		
		decode_key= "";
		level_key= new ArrayList<String>();

	}

	@Override
	public void addComponentsToPane() {

		setCurrentFile(new File(control.getRead_layout_current_file()));

		decrypt();
		
		if(!isError()){
			String tmp_user = "";
			area= new JTextArea(text);
			area.setMargin(new Insets(5, 5, 5, 5));
			area.setWrapStyleWord(true);
			area.setLineWrap(true);
			JScrollPane scroll_pane = new JScrollPane(area);
			scroll_pane.setPreferredSize(new Dimension(600, 400));
			scroll_pane.setMaximumSize(new Dimension(600, 400));
			scroll_pane
					.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

			// inserimento pulsanti
			pane.removeAll();
			pane.setLayout(new GridBagLayout());
			pane.setSize(800, 600);

			int posx = 0, posy = 0;
			GridBagConstraints c = new GridBagConstraints();
			c.anchor = GridBagConstraints.WEST;
			c.insets = new Insets(10, 10, 10, 10);
			// 0.0 - title
			field_title = new JTextField("Title");
			field_title.setEditable(false);
			field_title.setText(title);
			c.gridx = posx;
			c.gridy = posy;
			c.gridheight = 1;
			c.gridwidth = 3;
			c.weightx = 1;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			pane.add(field_title, c);
			// 0.1 - author
			posy++;
			field_author = new JTextField(author);
			field_author.setEditable(false);
			c.gridx = posx;
			c.gridy = posy;
			c.gridheight = 1;
			c.gridwidth = 6;
			c.weightx = 1;
			c.weighty = 0;
			pane.add(field_author, c);
			// 0.2 - receiver
			posy++;
			field_receiver= new JTextField(receiver);
			field_receiver.setEditable(false);
			c.gridx = posx;
			c.gridy = posy;
			c.gridheight = 1;
			c.gridwidth = 6;
			c.weightx = 1;
			c.weighty = 0;
			pane.add(field_receiver, c);
			// 0.3 - info
			posy++;
			field_info = new JTextField(info);
			field_info.setEditable(false);
			c.gridx = posx;
			c.gridy = posy;
			c.gridheight = 1;
			c.gridwidth = 6;
			c.weightx = 1;
			c.weighty = 0;
			pane.add(field_info, c);
			// 0.2 - text area
			posy++;
			c.gridx = posx;
			c.gridy = posy;
			c.gridheight = 8;
			c.gridwidth = 6;
			c.weightx = 1;
			c.weighty = 1;
			c.fill = GridBagConstraints.BOTH;
			pane.add(scroll_pane, c);
			// 7.0 - BACK
			JButton button = new JButton("BACK");
			posx = 7;
			posy = 0;
			c.gridheight = 1;
			c.gridwidth = 1;
			c.weightx = 0;
			c.weighty = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = posx;
			c.gridy = posy;
			c.ipadx = 0;
			c.ipady = 0;
			button.setBackground(Color.BLUE);
			button.setForeground(Color.WHITE);
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					control.setLayout("DECODE");
				}
			});
			pane.add(button, c);
			// 7.3 - SALVA
			button = new JButton("SALVA");
			posy++;
			c.gridx = posx;
			c.gridy = posy;
			button.setBackground(Color.BLUE);
			button.setForeground(Color.WHITE);
			// button.addActionListener(new SaveAction());
			pane.add(button, c);
		}
		else{
			control.setErrorLayout("Le firme combaciano meno del 90%\npossibili manomissioni.", "READ");
		}
	}

	/**
	 * @return the control
	 */
	public LayoutControl getControl() {
		return control;
	}

	/**
	 * @param control
	 *            the control to set
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
	 * @param pane
	 *            the pane to set
	 */
	public void setPane(Container pane) {
		this.pane = pane;
	}

	/**
	 * @return the area
	 */
	public JTextArea getArea() {
		return area;
	}

	/**
	 * @param area
	 *            the area to set
	 */
	public void setArea(JTextArea area) {
		this.area = area;
	}

	/**
	 * @return the field_title
	 */
	public JTextField getField_title() {
		return field_title;
	}

	/**
	 * @param field_title
	 *            the field_title to set
	 */
	public void setField_title(JTextField field_title) {
		this.field_title = field_title;
	}

	/**
	 * @return the field_author
	 */
	public JTextField getField_author() {
		return field_author;
	}

	/**
	 * @param field_author
	 *            the field_author to set
	 */
	public void setField_author(JTextField field_author) {
		this.field_author = field_author;
	}

	/**
	 * @return the field_receiver
	 */
	public JTextField getField_receiver() {
		return field_receiver;
	}

	/**
	 * @param field_receiver
	 *            the field_receiver to set
	 */
	public void setField_receiver(JTextField field_receiver) {
		this.field_receiver = field_receiver;
	}

	/**
	 * @return the field_info
	 */
	public JTextField getField_info() {
		return field_info;
	}

	/**
	 * @param field_info
	 *            the field_info to set
	 */
	public void setField_info(JTextField field_info) {
		this.field_info = field_info;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the output_folder
	 */
	public String getOutput_folder() {
		return output_folder;
	}

	/**
	 * @param output_folder
	 *            the output_folder to set
	 */
	public void setOutput_folder(String output_folder) {
		this.output_folder = output_folder;
	}

	/**
	 * @return the name_file
	 */
	public String getName_file() {
		return name_file;
	}

	/**
	 * @param name_file
	 *            the name_file to set
	 */
	public void setName_file(String name_file) {
		this.name_file = name_file;
	}

	/**
	 * @return the currentFile
	 */
	public File getCurrentFile() {
		return currentFile;
	}

	/**
	 * @param currentFile
	 *            the currentFile to set
	 */
	public void setCurrentFile(File currentFile) {
		this.currentFile = currentFile;
	}

	/**
	 * @return the isAlreadyConfigured
	 */
	public boolean isAlreadyConfigured() {
		return isAlreadyConfigured;
	}

	/**
	 * @param isAlreadyConfigured
	 *            the isAlreadyConfigured to set
	 */
	public void setAlreadyConfigured(boolean isAlreadyConfigured) {
		this.isAlreadyConfigured = isAlreadyConfigured;
	}

	/**
	 * @return the receiverSetted
	 */
	public boolean isReceiverSetted() {
		return receiverSetted;
	}

	/**
	 * @param receiverSetted
	 *            the receiverSetted to set
	 */
	public void setReceiverSetted(boolean receiverSetted) {
		this.receiverSetted = receiverSetted;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return actualUser;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.actualUser = user;
	}

	/**
	 * @return the qrcodes_path
	 */
	public ArrayList<String> getQrcodes_path() {
		return qrcodes_path;
	}

	/**
	 * @param qrcodes_path
	 *            the qrcodes_path to set
	 */
	public void setQrcodes_path(ArrayList<String> qrcodes_path) {
		this.qrcodes_path = qrcodes_path;
	}

	/**
	 * @return the current_qrcode
	 */
	public int getCurrent_qrcode() {
		return current_qrcode;
	}

	/**
	 * @param current_qrcode
	 *            the current_qrcode to set
	 */
	public void setCurrent_qrcode(int current_qrcode) {
		this.current_qrcode = current_qrcode;
	}

	public void setAreaText(String text) {
		setText(text);
		area.setText(text);
	}

	/**
	 * @return the isSingleUser
	 */
	public boolean isSingleUser() {
		return isSingleUser;
	}

	/**
	 * @param isSingleUser the isSingleUser to set
	 */
	public void setSingleUser(boolean isSingleUser) {
		this.isSingleUser = isSingleUser;
	}

	/**
	 * @return the error
	 */
	public boolean isError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(boolean error) {
		this.error = error;
	}

	public void decrypt(){
		
		filePart.clear();
		filePart= MagickUtility.getDataToDecrypt(currentFile.getAbsolutePath());
		MagickImage tmp_img = filePart.get(0);
		text= MagickUtility.getTextFromMagickImage(tmp_img);
		
		//codification info
		tmp_img = filePart.get(1);
		try {
			QRCode cod_info= QRCode.getQRCodeFromMagick(tmp_img);
			String tmp= cod_info.readQRCode().getText();
			String[] info_split= tmp.split("\n");
			title= info_split[0].substring(7);
			author= info_split[1].substring(8);
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			info= dateFormat.format(date);
			receiver= info_split[3].substring(10);
			if(receiver.startsWith("Level ")){
				setSingleUser(false);
			}
			else{
				setSingleUser(true);
			}
			
			sender_uid= info_split[2].substring(12);

		} catch (Exception e) {
			e.printStackTrace();
		}
		//signature
		tmp_img = filePart.get(2);
		try{
			QRCode cod_signature= QRCode.getQRCodeFromMagick(tmp_img);
			String signature= cod_signature.readQRCode().getText();
			//signature = CryptoUtility.fromBase64(signature).toString();
			String username= actualUser.getName() + "_" + actualUser.getID();
			String pwd= actualUser.getPassword();
			try (KeyDistributionClient cli = ConnectionFactory.getKeyDistributionServerConnection(url, username, keystore_pwd, pwd)) {
				String public_key= cli.getUserPublicKey(sender_uid);
				PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(CryptoUtility.fromBase64(public_key)));
				
				//byte[] dec= CryptoUtility.asymm_decrypt(ASYMMCRYPTO_ALGO.RSA, signature.getBytes(), publicKey);
				
				//String signature_decrypted= new String(dec);
				
				tmp_img= filePart.get(3);
				String tmp_path= getOutput_folder()+"tmp_img.png";
				MagickUtility.saveImage(tmp_img, tmp_path);
				ImagePHash hash = new ImagePHash(42, 5);
				
				String signature_calculated= hash.getHash(new FileInputStream(tmp_path));

				boolean correct= CryptoUtility.verifyRSA(publicKey, CryptoUtility.fromBase64(signature_calculated), signature);
				
				File tmp_f= new File(tmp_path);
				tmp_f.delete();
				
				//double match= hash.matchPercentage(signature_decrypted, signature_calculated);
				//System.out.println("signature= " + signature + "\ncalculated= " + signature_calculated);
				//if(match < 90.0){
				if(!correct){
					//System.out.println("match < 90");
					System.out.println("signature not match");
					if(!isError())
						setError(true);
					else
						setError(false);
				}
				else{
					System.out.println("signature match");
				/*	
					//decodifica del testo
					if(isSingleUser()){
						decode_key= actualUser.getPrivateKey();
					}
					else{
						Map<Integer, String> levelKey = cli.getAllAuthorizedLevelKey();
						//TODO ordinare le chiavi della mappa
						control.setKeyLevelMap(levelKey);
					}
					ArrayList<MagickImage> qrcodes= new ArrayList<MagickImage>();
					for(int i=4; i<14; i++){
						qrcodes.add(filePart.get(i));
					}
					//prenderli al contrario
					int size= qrcodes.size();
					for(int i= size-1; i>=0; i--){
						QRCode qrcode= QRCode.getQRCodeFromMagick(qrcodes.get(i));
						if(isSingleUser()){
							
						}
						else{
							//get dal control della chiave, se null errore
						}
					}*/
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		//tmp_img = filePart.get(2);
		//qrcodes
		
	}
}

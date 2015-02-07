package layout;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;

import javax.swing.JFrame;

import connection.ClientConfig;
import connection.ConnectionFactory;
import connection.RegistrationClient;
import entities.RegistrationBean;
import entities.Settings;
import entities.SettingsControl;
import usersManagement.UserManager;
import util.CryptoUtility;
import util.KeyTool;
/**
 * Questa classe e' il controllore del layout dell'applicazione
 * @author "Pasquale Verlotta - pasquale.verlotta@gmail.com"
 *
 */

public class LayoutControl {
	
	private final static int WIDTH= 600;
	private final static int HEIGHT= 600;
	public final static String keystore_pwd= "progettoSII";
	public final static String url= "localhost";//TODO UserManager: inserire il url nei settings
	
	private JFrame mainFrame;
	private SettingsLayout s_layout;
	private PrimaryLayout p_layout;
	private EncodeLayout e_layout;
	private WriteLayout w_layout;
	private HomeLayout h_layout;
	private RegistrationLayout r_layout;
	private ErrorLayout err_layout;
	private ReceiverSettingsLayout rec_layout;
	private ArrayList<String> choosed_images;
	private SettingsControl set_ctrl;

	private ArrayList<String> choosed_files;
	private String[] image_types= {"gif", "jpg", "png", "bmp"};
	private String[] text_types= {"txt"};
	
	private UserManager user_manager;
	
	private enum LAYOUT{
		PRIMARY, ENCODE, SETTINGS, HOME, WRITE, REGISTRATION, ERROR, RECEIVER
	}
	
	public LayoutControl(){
		
		choosed_images= new ArrayList<String>();
		choosed_files= new ArrayList<String>();
		mainFrame= new JFrame("Progetto SII");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(WIDTH, HEIGHT);
		mainFrame.setVisible(true);
		mainFrame.setLocation(150, 100);
		setLayout("HOME");
		
		user_manager= new UserManager(this);
		
		set_ctrl= new SettingsControl(this);
		set_ctrl.readSettings();
	}
	
	public void createLayout(){
	}
	
	//=========================================================================
	//=================GESTIONE LAYOUT=========================================
	/**
	 * Imposta il layout da visualizzare
	 * @param layout int 0=Primary, 1=Encode, 2=Settings, 
	 */
	public void setLayout(String name_layout){
		int layout= getLayoutByName(name_layout);
		
		if(layout == 0){
			PrimaryLayout();
			mainFrame.setSize(WIDTH, HEIGHT);
		}
		else if(layout == 1){
			EncodeLayout();
			mainFrame.setSize(500, 500);
		}
		else if(layout == 2){
			SettingsLayout();
			mainFrame.setSize(WIDTH, HEIGHT);
		}
		else if(layout == 3){
			WriteLayout();
			mainFrame.setSize(800, 600);
		}
		else if(layout == 4){
			HomeLayout();
			mainFrame.setSize(WIDTH, HEIGHT);
		}
		else if(layout == 5){
			RegistrationLayout();
			mainFrame.setSize(800, 700);
		}
		else if(layout == 6){

			mainFrame.setSize(600, HEIGHT);
		}
		else if(layout == 7){
			ReceiverLayout();
			mainFrame.setSize(800, 700);
		}
		
		//mainFrame.pack();
		mainFrame.repaint();
		mainFrame.validate();
	}	
	
	public int getLayoutByName(String name_layout){
		
		LAYOUT lay= LAYOUT.valueOf(name_layout);
		switch (lay){
			case PRIMARY:
				return 0;
			case ENCODE:
				return 1;
			case SETTINGS:
				return 2;
			case WRITE:
				return 3;
			case HOME:
				return 4;
			case REGISTRATION:
				return 5;
			case ERROR:
				return 6;
			case RECEIVER:
				return 7;
		}
		
		return -1;
	}
	
	public void setErrorLayout(String error_message, String backTo){

		if(err_layout == null){
			err_layout= new ErrorLayout(this, mainFrame.getContentPane(), error_message, backTo);
			err_layout.addComponentsToPane();
		}
		else{
			err_layout.setError_message(error_message);
			err_layout.setBackTo(backTo);
			err_layout.addComponentsToPane();
		}
		
		setLayout("ERROR");
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
			e_layout= new EncodeLayout(this, mainFrame.getContentPane(), this.choosed_files);
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
	
	public void WriteLayout(){
		if(w_layout == null){
			w_layout= new WriteLayout(this, mainFrame.getContentPane());
		}
		w_layout.addComponentsToPane();
	}
	
	public void HomeLayout(){
		if(h_layout == null){
			h_layout= new HomeLayout(this, mainFrame.getContentPane());
		}
		h_layout.addComponentsToPane();
	}
	
	public void RegistrationLayout(){
		if(r_layout == null){
			r_layout= new RegistrationLayout(this, mainFrame.getContentPane());
		}
		r_layout.addComponentsToPane();
	}
	
	public void ReceiverLayout(){
		if(rec_layout == null){
			rec_layout= new ReceiverSettingsLayout(this, mainFrame.getContentPane(), true);
		}
		rec_layout.addComponentsToPane();
	}
	
	public void setReceiverSingleUSer(boolean b){
		rec_layout.setSingleUser(b);
	}
	
	/**
	 * @return the choosed_files
	 */
	public ArrayList<String> getChoosed_files() {
		return choosed_files;
	}

	/**
	 * @param choosed_files the choosed_files to set
	 */
	public void setChoosed_files(ArrayList<String> choosed_files) {
		this.choosed_files = choosed_files;
	}
	/**
	 * @return the choosed_images
	 */
	public ArrayList<String> getChoosed_images() {
		return choosed_images;
	}

	/**
	 * @param choosed_images the choosed_images to set
	 */
	public void setChoosed_images(ArrayList<String> choosed_images) {
		this.choosed_images = choosed_images;
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
	
	public void addImageChoice(String path){
		if(!this.choosed_images.contains(path) && isImage(path)){
			this.choosed_images.add(path);
		}
		else{
			System.out.println("immagine gia presente e/o non supportata");
		}
		
		if(e_layout != null)
			e_layout.updateList();
	}
	
	public void addFileChoice(String path){
		if(!this.choosed_files.contains(path) && isText(path)){
			this.choosed_files.add(path);
		}
		else{
			System.out.println("file gia presente e/o non supportato");
		}
		
		if(e_layout != null)
			e_layout.updateList();
	}
	
	public void removeItem(int pos){
		this.choosed_images.remove(pos);
		
		if(e_layout != null)
			e_layout.updateList();
	}
	
	/**
	 * controlla che il file passato sia un tipo immagine nota
	 * @param path String il percorso del file
	 * @return false se non è un'immagine
	 */
	public boolean isImage(String path){
		return isImage(new File(path));
	}
	
	public boolean isImage(File f){
		for(String s : image_types){
			if(f.getAbsolutePath().endsWith(s)){
				return true;
			}
		}
		return false;
	}
	/**
	 * controlla che il file selezionato sia un tipo di testo noto
	 * @param path String il percorso del file
	 * @return false se non è un file di testo
	 */
	public boolean isText(String path){
		return isText(new File(path));
	}
	
	public boolean isText(File f){
		for(String s : text_types){
			if(f.getAbsolutePath().endsWith(s)){
				return true;
			}
		}
		return false;
	}
	/**
	 * Disegna l'immagine scelta nella finestra dell'applicazione
	 * @param path String il percorso dell'imagine da disegnare
	 */
	public void drawImage(File f, String backTo){

		ImageLayout img_layout= new ImageLayout(this, mainFrame.getContentPane(), backTo);
		try {
			img_layout.setViewer(f.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
		img_layout.addComponentsToPane();
		
		mainFrame.setSize(new Dimension(900, 600));
		mainFrame.repaint();
		mainFrame.validate();
		/*
		if(img_layout.getImgWidth() >= 800 || img_layout.getImgHeight() >= 600){
			mainFrame.setSize(new Dimension(1000, 700));
			mainFrame.repaint();
			mainFrame.validate();
		}
		else if(img_layout.getImgWidth() <= 400 || img_layout.getImgHeight() <= 400){

			mainFrame.setSize(new Dimension(500, 400));
			mainFrame.repaint();
			mainFrame.validate();
		}
		else{
			mainFrame.setSize(new Dimension(img_layout.getImgWidth()+100, img_layout.getImgHeight()+50));
			mainFrame.repaint();
			mainFrame.validate();
		}*/
	}
	
	public void drawFile(File f){

		if(w_layout == null){
			w_layout= new WriteLayout(this, mainFrame.getContentPane());
			w_layout.addComponentsToPane();
		}
		else{
			w_layout.addComponentsToPane();
		}

		mainFrame.setSize(800, 600);
		
		try {
			BufferedReader buff= new BufferedReader(new FileReader(f.getAbsolutePath()));
			String tmp= buff.readLine();
			String text= "";
			while(tmp != null){
				text= text + tmp;
				tmp= buff.readLine();
				//System.out.println(text);
			}
			w_layout.setCurrentFile(f);
			w_layout.setAreaText(text);
			/*String path= f.getAbsolutePath().substring(0, f.getAbsolutePath().lastIndexOf("/")+1);
			w_layout.setOutput_folder(path);*/
			buff.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void draw(String path, String backTo){
		File f = new File(path);
		if(isImage(f)){
			addImageChoice(path);
			drawImage(f, backTo);
		}
		else if(isText(f)){
			addFileChoice(path);
			drawFile(f);
		}
	}

	public UserManager getUser_manager() {
		return user_manager;
	}

	public void setUser_manager(UserManager user_manager) {
		this.user_manager = user_manager;
	}
	/**
	 * 
	 * @return il percorso assoluto della cartella di default
	 */
	public String getSettingsDefault(){
		return set_ctrl.getActualSettingsDefault();
	}
	/**
	 * 
	 * @return il percorso assoluto della cartella di input
	 */
	public String getSettingsInput(){
		return set_ctrl.getActualSettingsInput();
	}
	/**
	 * 
	 * @return il percorso assoluto della cartella di output
	 */
	public String getSettingsOutput(){
		return set_ctrl.getActualSettingsOutput();
	}
	/**
	 * Imposta i percorsi delle carte e li salva su file, eseguendo un hash prima
	 * @param def percorso assoluto della cartella di default
	 * @param in percorso assoluto della cartella di input
	 * @param out percorso assoluto della cartella di output
	 * @param dbPath percorso del database
	 */
	public void setActualSettings(String def, String in, String out, String dbPath, String url){
		
		set_ctrl.setActualDirSettings(def, in, out);
		set_ctrl.setActualDbPath(dbPath);
		set_ctrl.setActualUrl(url);
	}
	public boolean IsSettingsPathNull(){
		return set_ctrl.isNull();
	}
	public String getActualDbPath(){
		return set_ctrl.getActualDbPath();
	}
	public void setActualDbPath(String dbPath){
		set_ctrl.setActualDbPath(dbPath);
	}
	public void updateSettingsOnDb(){
		set_ctrl.updateDb();
	}
	public void setActualUserCode(String userCode){
		set_ctrl.setActualUserCode(userCode);
	}
	public void saveSettings(){
		set_ctrl.setPathToSave("./set.ser");
		set_ctrl.saveSetting();
	}
	public void addSettings(){
		set_ctrl.addActualSettings();
	}
	public Settings getSettingsByCode(String code){
		return set_ctrl.getSettingsByCode(code);
	}
	public void setActualSettings(Settings settings){
		set_ctrl.setActualSettings(settings);
	}
	public SettingsControl getSettingsControl(){
		return set_ctrl;
	}
	/**
	 * restituisce il certificato dal server una volta che l'utente e' stato autenticato
	 * @param secureId
	 * @param privateKey
	 */
	public boolean CertificateFromServer(String username, String secureId, String privKey){
		
		boolean toReturn= true;

		try(RegistrationClient reg = ConnectionFactory.getRegistrationServerConnection(url, keystore_pwd)) {

			PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(CryptoUtility.fromBase64(privKey)));

			RegistrationBean bean = reg.retrieveRegistrationDetails(secureId);

			System.out.println("trustLevel:" + bean.getTrustLevel());
			CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
			X509Certificate cert = (X509Certificate)certFactory.generateCertificate(new ByteArrayInputStream(bean.getCertificateData()));

			KeyStore ks = KeyTool.loadKeystore(ClientConfig.getInstance().getProperty(ClientConfig.KEYSTORE_PATH), keystore_pwd);
			KeyTool.addNewPrivateKey(ks, privateKey, cert, username, keystore_pwd.toCharArray());

			KeyTool.storeKeystore(ks, ClientConfig.getInstance().getProperty(ClientConfig.KEYSTORE_PATH), keystore_pwd);
		
		} catch (Exception e) {
			toReturn= false;
			e.printStackTrace();
		}
		
		return toReturn;
	}
}

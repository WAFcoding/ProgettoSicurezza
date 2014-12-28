/**
 * Questa classe è il controllore dell'oggetto settings, 
 * il quale memorizza i percorsi delle cartelle nel quale andare a scrivere o leggere i file
 */
package entities;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import layout.LayoutControl;

import org.hibernate.Session;
import org.hibernate.Transaction;

import usersManagement.User;
import util.HibernateUtil;

public class SettingsControl {
	
	private String pathToSave;
	private boolean isNull;
	private LayoutControl control;
	private ArrayList<Settings> al_settings;
	private Settings actualSettings;

	public SettingsControl(LayoutControl p_control){
		setPathToSave("./set.ser");
		setNull(false);
		setControl(p_control);
		setAl_settings(new ArrayList<Settings>());
		actualSettings= new Settings();
	}
	
	public SettingsControl(String def, String in, String out, String path){
		if(def != null & !def.equals("") &&
				in != null && !in.equals("") &&
					out != null && !out.equals("") &&
						path != null && !path.equals("")){
			actualSettings= new Settings(def, in, out);
			this.setPathToSave(path);
			
			al_settings= new ArrayList<Settings>();
			al_settings.add(actualSettings);
		}
	}
	
	public Settings getActualSettings(){
		return this.actualSettings;
	}

	public String getPathToSave() {
		return pathToSave;
	}

	public void setPathToSave(String path) {
		pathToSave = path;
		if(!exist(pathToSave) && !path.equals("")){
			System.out.println(pathToSave);
			String tmp_dir= pathToSave.substring(0, pathToSave.lastIndexOf("/"));
			System.out.println(tmp_dir);
			File tmp= new File(tmp_dir);
			tmp.mkdir();
			File tmp2= new File(pathToSave);
			try {
				tmp2.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean exist(String path){
		File f_tmp= new File(path);
		return f_tmp.exists();
	}
	/**
	 * serializza l'oggetto settings
	 */
	public void saveSetting(){

		try{
			FileOutputStream f_out= new FileOutputStream(getPathToSave());
			ObjectOutputStream obj_out= new ObjectOutputStream(f_out);
			obj_out.writeObject(al_settings);
			obj_out.close();
			f_out.close();
			//System.out.println("dati serializzati in " + getPathToSave());
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	/**
	 * deserializza l'oggetto settings
	 */
	public void readSettings(){
		
		try{
			FileInputStream f_in= new FileInputStream(getPathToSave());
			ObjectInputStream obj_in= new ObjectInputStream(f_in);
			al_settings= ((ArrayList<Settings>) obj_in.readObject());
			obj_in.close();
			f_in.close();
			//System.out.println("caricato l'oggetto " + getPathToSave());
		}
		catch(IOException e){
			e.printStackTrace();
			setNull(true);
		}
		catch(ClassNotFoundException c){
			c.printStackTrace();
		}
		
		for(Settings s : al_settings)
			s.PrintIt();
	}
	
	public String getActualSettingsDefault(){
		return actualSettings.getDefaultDirectory();
	}
	
	public String getActualSettingsInput(){
		return actualSettings.getInputDirectory();
	}
	
	public String getActualSettingsOutput(){
		return actualSettings.getOutputDirectory();
	}
	
	public void setActualDirSettings(String def, String in, String out){
		actualSettings.setDefaultDirectory(def);
		actualSettings.setInputDirectory(in);
		actualSettings.setOutputDirectory(out);
	}
	
	public void setActualSettings(Settings settings){
		actualSettings= settings;
	}

	public boolean isNull() {
		return isNull;
	}

	public void setNull(boolean isNull) {
		this.isNull = isNull;
	}
	
	public void setActualDbPath(String dbPath){
		actualSettings.setDbPath(dbPath);
	}
	
	public String getActualDbPath(){
		return actualSettings.getDbPath();
	}
	
	public String getActualUserCode(){
		return actualSettings.getUserCode();
	}
	
	public void setActualUserCode(String idUser){
		actualSettings.setUserCode(idUser);
	}
	/**
	 * aggiorna i valori delle cartelle di settings sul db
	 */
	public void updateDb(){
		
		System.out.println("Update DB");
		
		User actualUser= control.getUser_manager().getActualUser();
		
		if(!HibernateUtil.isCreated()){
			HibernateUtil.setDbPath(getActualDbPath());
			HibernateUtil.decryptDb(actualUser.getPassword());
			HibernateUtil.createSession();
		}
		
		Session session= HibernateUtil.getSessionFactory().openSession();
		Transaction tx= null;
		try{
			tx= session.beginTransaction();
			
			//control.getUser_manager().printActualUser();
			
			
			actualUser.setDir_def(getActualSettingsDefault());
			actualUser.setDir_in(getActualSettingsInput());
			actualUser.setDir_out(getActualSettingsOutput());
			
			session.update(actualUser);
			
			tx.commit();
			
		}catch (Exception e) {
			if(tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally{
			session.close();
			HibernateUtil.shutdown();
			HibernateUtil.encryptDb(actualUser.getPassword());
		}
	}
	
	/**
	 * @return the control
	 */
	public LayoutControl getControl() {
		return control;
	}

	/**
	 * @param control the LayoutControl to set
	 */
	public void setControl(LayoutControl control) {
		this.control = control;
	}
	
	public void printItAll(){
		System.out.println("Default= " + getActualSettingsDefault());
		System.out.println("Input= " + getActualSettingsInput());
		System.out.println("Output= " + getActualSettingsOutput());
	}

	public ArrayList<Settings> getAl_settings() {
		return al_settings;
	}

	public void setAl_settings(ArrayList<Settings> al_settings) {
		this.al_settings = al_settings;
	}
	
	public void addActualSettings(){
		al_settings.add(actualSettings);
		
		for(Settings s : al_settings)
			s.PrintIt();
	}
	
	public Settings getSettingsByCode(String code){
		for(Settings s : al_settings){
			String tmp_code= s.getUserCode();
			if(tmp_code.equals(code)){
				return s;
			}
		}
		
		return null;
	}
}

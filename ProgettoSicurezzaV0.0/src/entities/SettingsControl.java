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

public class SettingsControl {
	
	private Settings settings;
	private String pathToSave;
	
	public SettingsControl(){
		this.settings= new Settings();
		setPathToSave("");
	}
	
	public SettingsControl(Settings set, String path){
		this.settings= set;
		this.setPathToSave(path);
	}
	
	public SettingsControl(String def, String in, String out, String path){
		if(def != null & !def.equals("") &&
				in != null && !in.equals("") &&
					out != null && !out.equals("") &&
						path != null && !path.equals("")){
			this.settings= new Settings(def, in, out);
			this.setPathToSave(path);
		}
	}
	
	public Settings getSettings(){
		return this.settings;
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
	
	public void saveSetting(){

		try{
			FileOutputStream f_out= new FileOutputStream(getPathToSave());
			ObjectOutputStream obj_out= new ObjectOutputStream(f_out);
			obj_out.writeObject(settings);
			obj_out.close();
			f_out.close();
			System.out.println("dati serializzati in " + getPathToSave());
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void readSettings(){
		
		try{
			FileInputStream f_in= new FileInputStream(getPathToSave());
			ObjectInputStream obj_in= new ObjectInputStream(f_in);
			settings= (Settings) obj_in.readObject();
			obj_in.close();
			f_in.close();
			System.out.println("caricato l'oggetto " + getPathToSave());
		}
		catch(IOException e){
			e.printStackTrace();
		}
		catch(ClassNotFoundException c){
			c.printStackTrace();
		}
	}
	
	public String getSettingsDefault(){
		return settings.getDefaultDirectory();
	}
	
	public String getSettingsInput(){
		return settings.getInputDirectory();
	}
	
	public String getSettingsOutput(){
		return settings.getOutputDirectory();
	}
	
	public void setSettings(String def, String in, String out){
		settings.setDefaultDirectory(def);
		settings.setInputDirectory(in);
		settings.setOutputDirectory(out);
	}
}

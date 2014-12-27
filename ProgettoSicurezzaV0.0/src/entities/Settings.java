/**
 * Pasquale Verlotta - pasquale.verlotta@gmail.com
 * ProgettoSicurezzaV0.0 - 07/nov/2014
 */
package entities;

import java.io.Serializable;

public class Settings implements Serializable{
	

	private static final long serialVersionUID = 1L;
	
	private String defaultDirectory, inputDirectory, outputDirectory, dbPath, userCode;
	
	public Settings(){
		setDefaultDirectory("");
		setInputDirectory("");
		setOutputDirectory("");
		setUserCode("");
		setDbPath("");
	}
	
	public Settings(String def, String in, String out){
		setDefaultDirectory(def);
		setInputDirectory(in);
		setOutputDirectory(out);
		setUserCode("");
		setDbPath("");
	}

	public String getDefaultDirectory() {
		return defaultDirectory;
	}

	public void setDefaultDirectory(String defaultDirectory) {
		this.defaultDirectory = defaultDirectory;
	}

	public String getInputDirectory() {
		return inputDirectory;
	}

	public void setInputDirectory(String inputDirectory) {
		this.inputDirectory = inputDirectory;
	}

	public String getOutputDirectory() {
		return outputDirectory;
	}

	public void setOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	public String getDbPath() {
		return dbPath;
	}

	public void setDbPath(String dbPath) {
		this.dbPath = dbPath;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String idUser) {
		this.userCode = idUser;
	}
	
	public void PrintIt(){
		System.out.println(defaultDirectory + " - " + dbPath + " " + userCode);
	}

}

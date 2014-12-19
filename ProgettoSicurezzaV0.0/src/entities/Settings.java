/**
 * Pasquale Verlotta - pasquale.verlotta@gmail.com
 * ProgettoSicurezzaV0.0 - 07/nov/2014
 */
package entities;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Settings implements Serializable{
	

	private static final long serialVersionUID = 1L;
	
	private String defaultDirectory, inputDirectory, outputDirectory, dbPath;
	
	public Settings(){
		setDefaultDirectory("");
		setInputDirectory("");
		setOutputDirectory("");
	}
	
	public Settings(String def, String in, String out){
		setDefaultDirectory(def);
		setInputDirectory(in);
		setOutputDirectory(out);
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

}

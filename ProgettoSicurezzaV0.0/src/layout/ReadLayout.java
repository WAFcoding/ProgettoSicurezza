/**
 * Pasquale Verlotta - pasquale.verlotta@gmail.com
 * ProgettoSicurezzaV0.0 - 10/feb/2015
 */
package layout;

import java.awt.Container;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import usersManagement.User;

/**
 * @author pasquale
 *
 */
public class ReadLayout implements GeneralLayout {
	
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

	public ReadLayout(LayoutControl control, Container pane){
		this.control= control;
		this.pane= pane;
		
	}
	@Override
	public void addComponentsToPane() {

	}

}

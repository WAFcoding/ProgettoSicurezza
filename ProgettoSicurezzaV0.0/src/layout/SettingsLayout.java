/**
 * 
 */
package layout;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import entities.Settings;
import entities.SettingsControl;
import sun.security.action.GetLongAction;

/**
 * Questa classe rappresenta il layout necessario per impostare i percorsi delle cartelle
 * delle immagini da utilizzare o nelle quali ricercare
 * 
 * @author "Pasquale Verlotta - pasquale.verlotta@gmail.com"
 *
 */
public class SettingsLayout implements GeneralLayout, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Container pane;
	private LayoutControl control;
	private String file_choosed;
	private JTextField areaDefault, areaInput, areaOutput;
	
	public SettingsLayout(LayoutControl control, Container pane){
		setPane(pane);
		setControl(control);
	}

	@Override
	public void addComponentsToPane() {

        pane.removeAll();
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JButton button;
		areaDefault= new JTextField();
		areaInput= new JTextField();
		areaOutput= new JTextField();
		
		JLabel label;
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor= GridBagConstraints.CENTER;
		//1.0
		int posx=1, posy=0;
		label= new JLabel("Percorso della cartella di default");
		c.gridx= posx;c.gridy= posy;c.weightx = 0.5;c.insets= new Insets(10, 10, 0, 10);
		pane.add(label, c);
		//0.1
		posx--;posy++;
		button= new JButton("APRI");
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		c.gridx= posx;c.gridy= posy;c.weightx = 0.5;c.ipady=30;c.ipadx=0;
		c.insets= new Insets(0, 10, 10, 10);
		button.addActionListener(new OpenAction(areaDefault));
		pane.add(button, c);
		//1.1
		posx++;
		areaDefault.setEditable(true);areaDefault.setAutoscrolls(true);areaDefault.setColumns(15);areaDefault.setText(control.getSettingsDefault());
		c.gridx= posx;c.gridy= posy;c.weightx = 0.5;c.ipadx= areaDefault.getColumns();
		c.ipady=30;c.insets= new Insets(0, 10, 10, 10);
		pane.add(areaDefault, c);
		//1.2
		posy++;
		label= new JLabel("Percorso della cartella di input");
		c.gridx=posx;c.gridy=posy;c.weightx = 0.5;c.insets= new Insets(10, 10, 0, 10);
		pane.add(label, c);
		//0.3
		posx--;posy++;
		button= new JButton("APRI");
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		c.gridx=posx;c.gridy=posy;c.weightx = 0.5;c.ipady=30;c.ipadx=0;
		c.insets= new Insets(0, 10, 10, 10);
		button.addActionListener(new OpenAction(areaInput));
		pane.add(button, c);
		//1.3
		posx++;
		areaInput.setEditable(true);areaInput.setAutoscrolls(true);areaInput.setColumns(15);areaInput.setText(control.getSettingsInput());
		c.gridx=posx;c.gridy=posy;c.weightx = 0.5;c.ipadx= areaInput.getColumns();
		c.ipady=30;c.insets= new Insets(0, 10, 10, 10);
		pane.add(areaInput, c);
		//1.4
		posy++;
		label= new JLabel("Percorso della cartella di output");
		c.gridx=posx;c.gridy=posy;c.weightx = 0.5;c.insets= new Insets(10, 10, 0, 10);
		pane.add(label, c);
		//0.5
		posx--;posy++;
		button= new JButton("APRI");
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		c.gridx=posx;c.gridy=posy;c.weightx = 0.5;c.ipady=30;c.ipadx=0;
		c.insets= new Insets(10, 10, 0, 10);
		button.addActionListener(new OpenAction(areaOutput));
		pane.add(button, c);
		//1.5
		posx++;
		areaOutput.setEditable(true);areaOutput.setAutoscrolls(true);areaOutput.setColumns(15);areaOutput.setText(control.getSettingsOutput());
		c.gridx=posx;c.gridy=posy;c.weightx = 0.5;c.ipadx= areaOutput.getColumns();
		c.ipady=30;c.insets= new Insets(0, 10, 10, 10);
		pane.add(areaOutput, c);
		
		//0.6 - BACK
		posx--;posy++;
		button= new JButton("BACK");
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		c.gridx=posx;c.gridy=posy;c.weightx = 0.5;c.insets= new Insets(10, 10, 10, 10);
		c.ipady=30;c.ipadx=0;
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getControl().setLayout("PRIMARY");
			}
		});
		pane.add(button, c);
		//1.6- SALVA
		posx++;
		button= new JButton("SALVA");
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		c.gridx=posx;c.gridy=posy;c.weightx = 0.5;c.insets= new Insets(10, 10, 10, 10);
		c.ipady=30;c.ipadx=0;
		button.addActionListener(new SaveAction());
		pane.add(button, c);
	}

	public Container getPane() {
		return pane;
	}

	public void setPane(Container pane) {
		this.pane = pane;
	}

	public LayoutControl getControl() {
		return control;
	}

	public void setControl(LayoutControl control) {
		this.control = control;
	}

	public String getFile_choosed() {
		return file_choosed;
	}

	public void setFile_choosed(String file_choosed) {
		this.file_choosed = file_choosed;
	}

	private class OpenAction implements ActionListener{
		
		private JTextField m_area;
		public OpenAction(JTextField area){
			this.m_area= area;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser file_chooser= new JFileChooser();
			file_chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int choose= file_chooser.showDialog(null, "apri");
			
			if(choose == JFileChooser.APPROVE_OPTION){
				File file= file_chooser.getSelectedFile();
				setFile_choosed(file.getAbsolutePath());
				m_area.setText(getFile_choosed());
				System.out.println("Il file scelto è: " + getFile_choosed());
			}
		}
    }
	
	private class SaveAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			String tmp_default, tmp_input, tmp_output;
			tmp_default= areaDefault.getText();
			tmp_input= areaInput.getText();
			tmp_output= areaOutput.getText();
			control.setSettings(tmp_default, tmp_input, tmp_output);
		}
		
	}

}

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
	private JTextField fieldDefault, fieldInput, fieldOutput, fieldUrl;
	
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
		fieldDefault= new JTextField();
		fieldInput= new JTextField();
		fieldOutput= new JTextField();
		fieldUrl= new JTextField();
		
		JLabel label;
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor= GridBagConstraints.CENTER;
		//1.0
		int posx=1, posy=0;
		label= new JLabel("Default directory");
		c.gridx= posx;c.gridy= posy;c.weightx = 0.5;c.insets= new Insets(10, 10, 0, 10);
		pane.add(label, c);
		//0.1
		posx--;posy++;
		button= new JButton("APRI");
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		c.gridx= posx;c.gridy= posy;c.weightx = 0.5;c.ipady=30;c.ipadx=0;
		c.insets= new Insets(0, 10, 10, 10);
		button.addActionListener(new OpenAction(fieldDefault));
		pane.add(button, c);
		//1.1
		posx++;
		fieldDefault.setEditable(true);fieldDefault.setAutoscrolls(true);fieldDefault.setColumns(15);fieldDefault.setText(control.getSettingsDefault());
		c.gridx= posx;c.gridy= posy;c.weightx = 0.5;c.ipadx= fieldDefault.getColumns();
		c.ipady=30;c.insets= new Insets(0, 10, 10, 10);
		pane.add(fieldDefault, c);
		//1.2
		posy++;
		label= new JLabel("Input directory");
		c.gridx=posx;c.gridy=posy;c.weightx = 0.5;c.insets= new Insets(10, 10, 0, 10);
		pane.add(label, c);
		//0.3
		posx--;posy++;
		button= new JButton("APRI");
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		c.gridx=posx;c.gridy=posy;c.weightx = 0.5;c.ipady=30;c.ipadx=0;
		c.insets= new Insets(0, 10, 10, 10);
		button.addActionListener(new OpenAction(fieldInput));
		pane.add(button, c);
		//1.3
		posx++;
		fieldInput.setEditable(true);fieldInput.setAutoscrolls(true);fieldInput.setColumns(15);fieldInput.setText(control.getSettingsInput());
		c.gridx=posx;c.gridy=posy;c.weightx = 0.5;c.ipadx= fieldInput.getColumns();
		c.ipady=30;c.insets= new Insets(0, 10, 10, 10);
		pane.add(fieldInput, c);
		//1.4
		posy++;
		label= new JLabel("Output directory");
		c.gridx=posx;c.gridy=posy;c.weightx = 0.5;c.insets= new Insets(10, 10, 0, 10);
		pane.add(label, c);
		//0.5
		posx--;posy++;
		button= new JButton("APRI");
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		c.gridx=posx;c.gridy=posy;c.weightx = 0.5;c.ipady=30;c.ipadx=0;
		c.insets= new Insets(10, 10, 0, 10);
		button.addActionListener(new OpenAction(fieldOutput));
		pane.add(button, c);
		//1.5
		posx++;
		fieldOutput.setEditable(true);fieldOutput.setAutoscrolls(true);fieldOutput.setColumns(15);fieldOutput.setText(control.getSettingsOutput());
		c.gridx=posx;c.gridy=posy;c.weightx = 0.5;c.ipadx= fieldOutput.getColumns();
		c.ipady=30;c.insets= new Insets(0, 10, 10, 10);
		pane.add(fieldOutput, c);
		//1.6
		posy++;
		label= new JLabel("Url");
		c.gridx=posx;c.gridy=posy;c.weightx = 0.5;c.insets= new Insets(10, 10, 0, 10);
		pane.add(label, c);
		//1.7
		posy++;
		fieldUrl.setEditable(true);fieldUrl.setAutoscrolls(true);fieldUrl.setColumns(15);fieldUrl.setText(control.getSettingsOutput());
		c.gridx=posx;c.gridy=posy;c.weightx = 0.5;c.ipadx= fieldUrl.getColumns();
		c.ipady=30;c.insets= new Insets(0, 10, 10, 10);
		pane.add(fieldUrl, c);
		
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
			
			String tmp_default, tmp_input, tmp_output, tmp_url;
			tmp_default= fieldDefault.getText();
			tmp_input= fieldInput.getText();
			tmp_output= fieldOutput.getText();
			tmp_url= fieldUrl.getText();
			control.setActualSettings(tmp_default, tmp_input, tmp_output, tmp_default, tmp_url);
			control.saveSettings();
			control.updateSettingsOnDb();
		}
		
	}

}

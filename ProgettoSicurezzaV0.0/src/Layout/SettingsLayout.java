/**
 * 
 */
package Layout;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextArea;

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
		JTextArea area1= new JTextArea();
		JTextArea area2= new JTextArea();
		JTextArea area3= new JTextArea();
		
		JLabel label;
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor= GridBagConstraints.CENTER;
		
		//0.0
		button= new JButton("APRI");
		c.gridx= 0;c.gridy= 0;c.weightx = 0.5;c.ipady=0;c.ipadx=0;
		c.insets= new Insets(10, 0, 0, 10);
		button.addActionListener(new OpenAction(area1));
		pane.add(button, c);
		//1.0
		label= new JLabel("Percorso della cartella di default");
		c.gridx= 1;c.gridy= 0;c.weightx = 0.5;c.insets= new Insets(10, 10, 0, 10);
		pane.add(label, c);
		//1.1
		area1.setEditable(true);area1.setAutoscrolls(true);area1.setColumns(15);
		c.gridx= 1;c.gridy= 1;c.weightx = 0.5;c.ipadx= area1.getColumns();c.ipady= area1.getHeight();
		c.insets= new Insets(0, 10, 10, 10);
		pane.add(area1, c);
		
		//0.2
		button= new JButton("APRI");
		c.gridx= 0;c.gridy= 2;c.weightx = 0.5;c.ipady=0;c.ipadx=0;
		c.insets= new Insets(10, 0, 0, 10);
		button.addActionListener(new OpenAction(area2));
		pane.add(button, c);
		//1.2
		label= new JLabel("Percorso della cartella di input");
		c.gridx= 1;c.gridy= 2;c.weightx = 0.5;c.insets= new Insets(10, 10, 0, 10);
		pane.add(label, c);
		//1.3
		area2.setEditable(true);area2.setAutoscrolls(true);area2.setColumns(15);
		c.gridx= 1;c.gridy= 3;c.weightx = 0.5;c.ipadx= area2.getColumns();c.ipady= area2.getHeight();
		c.insets= new Insets(0, 10, 10, 10);
		pane.add(area2, c);
		
		//0.4
		button= new JButton("APRI");
		c.gridx= 0;c.gridy= 4;c.weightx = 0.5;c.ipady=0;c.ipadx=0;
		c.insets= new Insets(10, 0, 0, 10);
		button.addActionListener(new OpenAction(area3));
		pane.add(button, c);
		//1.4
		label= new JLabel("Percorso della cartella di output");
		c.gridx= 1;c.gridy= 4;c.weightx = 0.5;c.insets= new Insets(10, 10, 0, 10);
		pane.add(label, c);
		//1.5
		area3.setEditable(true);area3.setAutoscrolls(true);area3.setColumns(15);
		c.gridx= 1;c.gridy= 5;c.weightx = 0.5;c.ipadx= area3.getColumns();c.ipady= area3.getHeight();
		c.insets= new Insets(0, 10, 10, 10);
		pane.add(area3, c);
		
		//menu sotto parte da 0.6
		button= new JButton("BACK");
		c.gridx= 0;c.gridy= 6;c.weightx = 0.5;c.insets= new Insets(10, 10, 10, 10);
		c.ipady=30;c.ipadx=0;
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getControl().setLayout(0);
			}
		});
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
		
		private JTextArea m_area;
		public OpenAction(JTextArea area){
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

}

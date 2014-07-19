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

import javax.swing.JButton;

import sun.security.action.GetLongAction;

/**
 * Questa classe rappresenta il layout necessario per impostare i percorsi delle cartelle
 * delle immagini da utilizzare o nelle quali ricercare
 * 
 * @author "Pasquale Verlotta - pasquale.verlotta@gmail.com"
 *
 */
public class SettingsLayout implements GeneralLayout{
	
	private Container pane;
	private LayoutControl control;
	
	public SettingsLayout(LayoutControl control, Container pane){
		setPane(pane);
		setControl(control);
	}

	@Override
	public void addComponentsToPane() {
		
		JButton button;
        pane.removeAll();
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor= GridBagConstraints.CENTER;
		c.ipady= 40;
		c.insets= new Insets(10, 10, 10, 10);
		
		button= new JButton("BACK");
		c.weightx = 0.5;
		c.gridx= 0;
		c.gridy= 0;
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getControl().setLayout(0);
			}
		});
		pane.add(button, c);
		
		button= new JButton("BACK");
		c.weightx = 0.5;
		c.gridx= 0;
		c.gridy= 1;
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

}

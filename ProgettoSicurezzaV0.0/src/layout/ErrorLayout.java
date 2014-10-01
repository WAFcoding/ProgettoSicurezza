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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import usersManagement.UserManager;

/**
 * @author "Pasquale Verlotta - pasquale.verlotta@gmail.com"
 *
 */
public class ErrorLayout implements GeneralLayout{
	
	private LayoutControl control;
	private Container pane;
	private String error_message;
	private String backTo;
	
	public ErrorLayout(LayoutControl control, Container pane, String error_message, String backTo){
    	
    	setPane(pane);
    	setControl(control);
    	setError_message(error_message);
    	setBackTo(backTo);
    }
	@Override
	public void addComponentsToPane() {

        //inserimento pulsanti
        pane.removeAll();
		pane.setLayout(new GridBagLayout());
		pane.setBackground(Color.ORANGE);
		
		int posx= 0, posy= 0;
		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor= GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets= new Insets(10, 10, 10, 10);
		
		//0.0
		InnerPanelError registration= new InnerPanelError();
		registration.setBackground(Color.ORANGE);
		registration.setBorder(BorderFactory.createLineBorder(Color.blue));
		registration.addComponentsToPane();
		c.gridx=posx;c.gridy=posy;c.weightx=1;
		c.fill = GridBagConstraints.HORIZONTAL;
		pane.add(registration, c);
		
		
		//0.1 - BACK
		posy++;
		c.gridx=posx;c.gridy=posy;c.weightx=1;
		JButton button= new JButton("BACK");
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getControl().setLayout(getBackTo());
			}
		});
		pane.add(button, c);
	}


	/**
	 * @return the control
	 */
	public LayoutControl getControl() {
		return control;
	}
	/**
	 * @param control the control to set
	 */
	public void setControl(LayoutControl control) {
		this.control = control;
	}
	/**
	 * @return the pane
	 */
	public Container getPane() {
		return pane;
	}
	/**
	 * @param pane the pane to set
	 */
	public void setPane(Container pane) {
		this.pane = pane;
	}
	/**
	 * @return the error_maessage
	 */
	public String getError_maessage() {
		return error_message;
	}
	/**
	 * @param error_maessage the error_maessage to set
	 */
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
	
	public String getBackTo() {
		return backTo;
	}
	public void setBackTo(String backTo) {
		this.backTo = backTo;
	}

	private class InnerPanelError extends JPanel implements GeneralLayout{

		private static final long serialVersionUID = 1L;
		@Override
		public void addComponentsToPane() {
			
			int posx= 0, posy= 0;
			this.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.anchor= GridBagConstraints.NORTH;
			c.fill = GridBagConstraints.BOTH;
			c.ipady= 30;
			c.insets= new Insets(10, 5, 10, 5);
			
			//JLabel error_label= new JLabel();
			JTextArea error= new JTextArea(error_message);
			error.setForeground(Color.BLUE);
			error.setBackground(getBackground());
			
			c.gridx=posx;c.gridy=posy;
			this.add(error, c);
			
		}
	}

}

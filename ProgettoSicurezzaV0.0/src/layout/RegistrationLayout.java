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
import javax.swing.JTextField;

import sun.security.util.Password;
import usersManagement.UserManager;

/**
 * @author "Pasquale Verlotta - pasquale.verlotta@gmail.com"
 *
 */
public class RegistrationLayout implements GeneralLayout{

	private Container pane;
    private LayoutControl control;
    private JTextField firstname;
    private JTextField secondname;
    private JTextField mail;
    private JTextField confirm_mail;
    private JPasswordField pwd;
    private JPasswordField confirm_pwd;
	
	public RegistrationLayout(LayoutControl control, Container pane){
    	setControl(control);
    	setPane(pane);
    }
	

	public void addComponentsToPane() {

        //inserimento pulsanti
        pane.removeAll();
		pane.setLayout(new GridBagLayout());
		pane.setBackground(Color.ORANGE);
		
		int posx= 0, posy= 0;
		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor= GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets= new Insets(20, 10, 20, 10);
		
		//0.0
		InnerPanelRegistration registration= new InnerPanelRegistration();
		registration.setBackground(Color.ORANGE);
		registration.setBorder(BorderFactory.createLineBorder(Color.blue));
		registration.addComponentsToPane();
		c.gridx=posx;c.gridy=posy;c.weightx=1;
		c.fill = GridBagConstraints.HORIZONTAL;
		pane.add(registration, c);
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
	
	private class InnerPanelRegistration extends JPanel implements GeneralLayout{

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
			
			JButton button;
			
			//0.0 - FIRSTNAME
			c.gridx=posx;c.gridy=posy;c.weightx=1;c.gridwidth=1;
			firstname= new JTextField("First Name");
			firstname.addFocusListener(new FocusListener(){
				
		        @Override
		        public void focusGained(FocusEvent e){
		        	if(firstname.getText().equals("First Name"))
		        		firstname.setText("");
		        }

				@Override
				public void focusLost(FocusEvent arg0) {
					if(firstname.getText().equals(""))
						firstname.setText("First Name");
				}
		    });
			this.add(firstname, c);
			
			//1.0 - SECONDNAME
			posx++;
			c.gridx=posx;c.gridy=posy;c.weightx=1;c.gridwidth=1;
			secondname= new JTextField("Second Name");
			secondname.addFocusListener(new FocusListener(){
				
		        @Override
		        public void focusGained(FocusEvent e){
		        	if(secondname.getText().equals("Second Name"))
		        		secondname.setText("");
		        }

				@Override
				public void focusLost(FocusEvent arg0) {
					if(secondname.getText().equals(""))
						secondname.setText("Second Name");
				}
		    });
			this.add(secondname, c);
			
			//0.1 - label password
			posx--;posy++;
			c.gridx=posx;c.gridy=posy;c.gridwidth=4;
			c.insets= new Insets(10, 5, 2, 5);
			JLabel label= new JLabel("Password may contains a number, an upper case and has to be at least 8 chars.");
			this.add(label, c);
			
			//0.2 - PASSWORD
			posy++;
			c.gridx=posx;c.gridy=posy;c.weightx=1;c.gridwidth=1;
			c.insets= new Insets(2, 5, 10, 5);
			pwd= new JPasswordField("Password");
			pwd.addFocusListener(new FocusListener(){
				
		        @Override
		        public void focusGained(FocusEvent e){
		        	String tmp= new String(pwd.getPassword());
		        	if(tmp.equals("Password"))
		        		pwd.setText("");
		        }

				@Override
				public void focusLost(FocusEvent arg0) {
					if(pwd.getPassword() == null || pwd.getPassword().length == 0 )
							pwd.setText("Password");
				}
		    });
			this.add(pwd, c);
			
			//1.2 - CONFIRM PASSWORD
			posx++;
			c.gridx=posx;c.gridy=posy;c.weightx=1;c.gridwidth=1;
			confirm_pwd= new JPasswordField("Password");
			confirm_pwd.addFocusListener(new FocusListener(){
				
		        @Override
		        public void focusGained(FocusEvent e){
		        	String tmp= new String(confirm_pwd.getPassword());
		        	if(tmp.equals("Password"))
		        		confirm_pwd.setText("");
		        }

				@Override
				public void focusLost(FocusEvent arg0) {
					if(confirm_pwd.getPassword() == null || confirm_pwd.getPassword().length == 0 )
						confirm_pwd.setText("Password");
				}
		    });
			this.add(confirm_pwd, c);
			
			//0.3 - MAIL
			posx--;posy++;
			c.gridx=posx;c.gridy=posy;c.weightx=1;c.gridwidth=1;
			c.insets= new Insets(10, 5, 10, 5);
			mail= new JTextField("Mail");
			mail.addFocusListener(new FocusListener() {
				
		        @Override
		        public void focusGained(FocusEvent e){
		        	String tmp= new String(mail.getText());
		        	if(tmp.equals("Mail"))
		        		mail.setText("");
		        }

				@Override
				public void focusLost(FocusEvent arg0) {
					if(mail.getText().equals("") )
						mail.setText("Mail");
				}
			});
			this.add(mail, c);
			
			//1.3 - CONFIRM MAIL
			posx++;
			c.gridx=posx;c.gridy=posy;c.weightx=1;c.gridwidth=1;
			confirm_mail= new JTextField("Confirm Mail");
			confirm_mail.addFocusListener(new FocusListener() {
				
		        @Override
		        public void focusGained(FocusEvent e){
		        	String tmp= new String(confirm_mail.getText());
		        	if(tmp.equals("Confirm Mail"))
		        		confirm_mail.setText("");
		        }

				@Override
				public void focusLost(FocusEvent arg0) {
					if(confirm_mail.getText().equals("") )
						confirm_mail.setText("Confirm Mail");
				}
			});
			this.add(confirm_mail, c);
			
			//0.4 - REGISTRATION
			posx--;posy++;
			c.gridx=posx;c.gridy= posy;c.gridwidth=1;
			c.insets= new Insets(10, 5, 10, 5);
			button= new JButton("REGISTRATION");
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					//TODO aggiungere la chiamata a UserManager
					String[] registration_data= {firstname.getText(), secondname.getText(), 
												new String(pwd.getPassword()), new String(confirm_pwd.getPassword()), 
												mail.getText(), confirm_mail.getText()};
					for(String s : registration_data){
						System.out.print(s + " ");
					}
					System.out.print("\n");
					
					UserManager user_manager= control.getUser_manager();
					//controllo che la password e la confirm password sia identici
					if(user_manager.confirmPassword(null, null)){
						
					}
					//controllo che la password rispecchi la struttura assegnata
					else if(user_manager.checkPassword(null)){
						
					}
					//controllo che la mail e confirm mail sia identici
					else if(user_manager.confirmMail(null, null)){
						
					}
					
					user_manager.registration(registration_data);
					getControl().setLayout("PRIMARY");//da modificare la schermata successiva alla registrazione
				}
			});
			button.setBackground(Color.BLUE);
			button.setForeground(Color.WHITE);
			this.add(button, c);
			
			//1.4 - CANCEL
			posx++;
			c.gridx= posx;
			button= new JButton("CANCEL");
			button.setBackground(Color.BLUE);
			button.setForeground(Color.WHITE);
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					firstname.setText("User");
					pwd.setText("password");
				}
			});
			this.add(button, c);
		}
	}

}

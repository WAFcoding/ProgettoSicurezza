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
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import sun.security.util.Password;
import usersManagement.UserManager;

/**
 * @author "Pasquale Verlotta - pasquale.verlotta@gmail.com"
 *
 */
public class RegistrationLayout implements GeneralLayout{

	private Container pane;
    private LayoutControl control;
    private JTextField name, surname, mail, confirm_mail, city, country, country_code, organization,
    					dir_def, dir_in, dir_out;
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
	
	private class InnerPanelRegistrationDirectory extends JPanel implements GeneralLayout{

		private static final long serialVersionUID = 1L;

		@Override
		public void addComponentsToPane() {
			
			int posx= 1, posy= 0;
			this.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.anchor= GridBagConstraints.NORTH;
			c.fill = GridBagConstraints.BOTH;
			c.ipady= 15;
			c.insets= new Insets(10, 5, 10, 5);
			
			JButton button;
			
			//1.0 - open
			c.gridx=posx;c.gridy=posy;c.weightx=1;c.gridwidth=1;
			button= new JButton("OPEN");
			button.setBackground(Color.BLUE);
			button.setForeground(Color.WHITE);
			dir_def= new JTextField("Default directory");
			button.addActionListener(new OpenAction(dir_def));
			this.add(button, c);
			
			//2.0 - def directory
			posx++;
			c.gridx=posx;c.gridy=posy;c.weightx=1;c.gridwidth=1;
			dir_def.addFocusListener(new FocusListener() {
				
		        @Override
		        public void focusGained(FocusEvent e){
		        	String tmp= new String(dir_def.getText());
		        	if(tmp.equals("Default directory"))
		        		dir_def.setText("");
		        }

				@Override
				public void focusLost(FocusEvent arg0) {
					if(dir_def.getText().equals("") )
						dir_def.setText("Default directory");
				}
			});
			this.add(dir_def, c);
			
			//0.1 - open
			posx-=2;posy++;
			c.gridx=posx;c.gridy=posy;c.weightx=1;c.gridwidth=1;
			button= new JButton("OPEN");
			button.setBackground(Color.BLUE);
			button.setForeground(Color.WHITE);
			dir_in= new JTextField("Input directory");
			button.addActionListener(new OpenAction(dir_in));
			this.add(button, c);
			//1.1 - in directory
			posx++;
			c.gridx=posx;c.gridy=posy;c.weightx=1;c.gridwidth=1;
			dir_in.addFocusListener(new FocusListener() {
				
		        @Override
		        public void focusGained(FocusEvent e){
		        	String tmp= new String(dir_in.getText());
		        	if(tmp.equals("Input directory"))
		        		dir_in.setText("");
		        }

				@Override
				public void focusLost(FocusEvent arg0) {
					if(dir_in.getText().equals("") )
						dir_in.setText("Input directory");
				}
			});
			this.add(dir_in, c);
			
			//2.1 - OPEN
			posx++;
			c.gridx=posx;c.gridy=posy;c.weightx=1;c.gridwidth=1;
			button= new JButton("OPEN");
			button.setBackground(Color.BLUE);
			button.setForeground(Color.WHITE);
			dir_out= new JTextField("Output directory");
			button.addActionListener(new OpenAction(dir_out));
			this.add(button, c);
			
			//3.1 - out directory
			posx++;
			c.gridx=posx;c.gridy=posy;c.weightx=1;c.gridwidth=1;
			dir_out.addFocusListener(new FocusListener() {
				
		        @Override
		        public void focusGained(FocusEvent e){
		        	String tmp= new String(dir_out.getText());
		        	if(tmp.equals("Output directory"))
		        		dir_out.setText("");
		        }

				@Override
				public void focusLost(FocusEvent arg0) {
					if(dir_out.getText().equals("") )
						dir_out.setText("Output directory");
				}
			});
			this.add(dir_out, c);
			
		}
		
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
			c.ipady= 15;
			c.insets= new Insets(10, 5, 10, 5);
			
			JButton button;
			
			//0.0 - FIRSTNAME
			c.gridx=posx;c.gridy=posy;c.weightx=1;c.gridwidth=1;
			name= new JTextField("First Name");
			name.addFocusListener(new FocusListener(){
				
		        @Override
		        public void focusGained(FocusEvent e){
		        	if(name.getText().equals("First Name"))
		        		name.setText("");
		        }

				@Override
				public void focusLost(FocusEvent arg0) {
					if(name.getText().equals(""))
						name.setText("First Name");
				}
		    });
			this.add(name, c);
			
			//1.0 - SECONDNAME
			posx++;
			c.gridx=posx;c.gridy=posy;c.weightx=1;c.gridwidth=1;
			surname= new JTextField("Second Name");
			surname.addFocusListener(new FocusListener(){
				
		        @Override
		        public void focusGained(FocusEvent e){
		        	if(surname.getText().equals("Second Name"))
		        		surname.setText("");
		        }

				@Override
				public void focusLost(FocusEvent arg0) {
					if(surname.getText().equals(""))
						surname.setText("Second Name");
				}
		    });
			this.add(surname, c);
			
			//0.1 - label password
			posx--;posy++;
			c.gridx=posx;c.gridy=posy;c.gridwidth=2;
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
			
			//0.4 - country
			posx--;posy++;
			c.gridx=posx;c.gridy=posy;c.weightx=1;c.gridwidth=1;
			country= new JTextField("Country");
			country.addFocusListener(new FocusListener() {
				
		        @Override
		        public void focusGained(FocusEvent e){
		        	String tmp= new String(country.getText());
		        	if(tmp.equals("Country"))
		        		country.setText("");
		        }

				@Override
				public void focusLost(FocusEvent arg0) {
					if(country.getText().equals("") )
						country.setText("Country");
				}
			});
			this.add(country, c);
			
			//1.4 - country code
			posx++;
			c.gridx=posx;c.gridy=posy;c.weightx=1;c.gridwidth=1;
			country_code= new JTextField("Country Code");
			country_code.addFocusListener(new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent arg0) {
					if(country_code.getText().equals("")){
						country_code.setDocument(new JTextFieldLimit(20));
						country_code.setText("Country Code");
					}
				}
				
				@Override
				public void focusGained(FocusEvent arg0) {
					country_code.setDocument(new JTextFieldLimit(2));
					if(country_code.getText().equals("Country Code")){
						country_code.setText("");
					}
				}
			});
			this.add(country_code, c);
			
			//0.5 - city
			posx--;posy++;
			c.gridx=posx;c.gridy=posy;c.weightx=1;c.gridwidth=1;
			city= new JTextField("City");
			city.addFocusListener(new FocusListener() {
				
		        @Override
		        public void focusGained(FocusEvent e){
		        	String tmp= new String(city.getText());
		        	if(tmp.equals("City"))
		        		city.setText("");
		        }

				@Override
				public void focusLost(FocusEvent arg0) {
					if(city.getText().equals("") )
						city.setText("City");
				}
			});
			this.add(city, c);
			
			//1.5 - organization
			posx++;
			c.gridx=posx;c.gridy=posy;c.weightx=1;c.gridwidth=1;
			organization= new JTextField("Organization");
			organization.addFocusListener(new FocusListener() {
				
		        @Override
		        public void focusGained(FocusEvent e){
		        	String tmp= new String(organization.getText());
		        	if(tmp.equals("Organization"))
		        		organization.setText("");
		        }

				@Override
				public void focusLost(FocusEvent arg0) {
					if(organization.getText().equals("") )
						organization.setText("organization");
				}
			});
			this.add(organization, c);
			
			//0.6 - directory chooser
			posx--;posy++;
			c.gridx=posx;c.gridy=posy;c.weightx=1;c.gridwidth=2;
			InnerPanelRegistrationDirectory i_panel_reg_dir= new InnerPanelRegistrationDirectory();
			i_panel_reg_dir.addComponentsToPane();
			i_panel_reg_dir.setBackground(Color.ORANGE);
			this.add(i_panel_reg_dir, c);
			//-------------------------------------------------------------------------------
			//0.8 - REGISTRATION
			posy++;
			c.gridx=posx;c.gridy= posy;c.gridwidth=1;
			c.insets= new Insets(10, 5, 10, 5);
			button= new JButton("REGISTRATION");
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					
					String s_firstname= name.getText();
					String s_secondname= surname.getText();
					String s_password= new String(pwd.getPassword());
					String s_confirm_password= new String(confirm_pwd.getPassword());
					String s_mail= mail.getText();
					String s_confirm_mail= confirm_mail.getText();
					String s_city= city.getText();
					String s_country= country.getText();
					String s_country_code= country_code.getText();
					String s_organization= organization.getText();
					String s_dir_def= dir_def.getText();
					String s_dir_in= dir_in.getText();
					String s_dir_out= dir_out.getText();
					
					String[] registration_data= {s_firstname, s_secondname, s_password, s_confirm_password,
												 s_mail, s_confirm_mail, s_city, s_country, s_country_code, 
												 s_organization, s_dir_def, s_dir_in, s_dir_out};
					
					boolean more= true;
					for(String s : registration_data){ 
						if(s == null || s.equals("")){
							more= false;
							break;
						}
						System.out.print(s + " ");
					}
					System.out.print("\n");
					
					if(more){
						UserManager user_manager= control.getUser_manager();
						//TODO RegistrationLayout: rimuovere i commenti
						/*
						//controllo che la password e la confirm password sia identici
						if(!user_manager.confirmPassword(s_password, s_confirm_password)){
							control.setErrorLayout("confrim password doesn't match password", "REGISTRATION");
						}
						//controllo che la password rispecchi la struttura assegnata
						else if(!user_manager.checkPassword(s_password)){
							control.setErrorLayout("password doesn't respect the correct structure \n"
									+ "it has to contains at least an uppercase char, a number \n"
									+ "and it has to be at least long 8 chars", "REGISTRATION");
						}
						//controllo che la mail e confirm mail sia identici
						else if(!user_manager.confirmMail(s_mail, s_confirm_mail)){
							control.setErrorLayout("confrim mail doesn't match mail", "REGISTRATION");
						}
						//se supera tutti i controlli procedo con la registrazione
						else{
							user_manager.registration(registration_data);
							getControl().setLayout("PRIMARY");//da modificare la schermata successiva alla registrazione
						}*/

						user_manager.registration(registration_data);
					}
					else{
						System.out.println("Alcuni campi sono vuoti, non possono essere vuoti");
					}
				}
			});
			button.setBackground(Color.BLUE);
			button.setForeground(Color.WHITE);
			this.add(button, c);
			
			//1.8 - CANCEL
			posx++;
			c.gridx= posx;c.gridy=posy;
			button= new JButton("CANCEL");
			button.setBackground(Color.BLUE);
			button.setForeground(Color.WHITE);
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					name.setText("User");
					pwd.setText("password");
				}
			});
			this.add(button, c);
			
			//0.9 - BACK
			posx--;posy++;
			c.gridx=posx;c.gridy=posy;
			button= new JButton("BACK");
			button.setBackground(Color.BLUE);
			button.setForeground(Color.WHITE);
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					control.setLayout("HOME");
				}
			});
			this.add(button, c);
		}
	}

	private class OpenAction implements ActionListener{
		
		private JTextField field;
		public OpenAction(JTextField area){
			this.field= area;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser file_chooser= new JFileChooser();
			file_chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int choose= file_chooser.showDialog(null, "apri");
			
			if(choose == JFileChooser.APPROVE_OPTION){
				File file= file_chooser.getSelectedFile();
				String tmp= file.getAbsolutePath();
				System.out.println(tmp);
				this.field.setText(file.getAbsolutePath());
			}
		}
    }	
	
	private class JTextFieldLimit extends PlainDocument {
		private static final long serialVersionUID = 4592979636590560189L;
		private int limit;
		JTextFieldLimit(int limit) {
			super();
			this.limit = limit;
		}

		public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
			if (str == null)
				return;

			if ((getLength() + str.length()) <= limit) {
				super.insertString(offset, str.toUpperCase(), attr);
			}
		}
	}

}

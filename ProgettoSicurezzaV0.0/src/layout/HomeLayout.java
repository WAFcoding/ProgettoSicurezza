/**
 * Questa classe rappresenta il layout visualizzato all'avvio dell'applicazione
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

/**
 * @author "Pasquale Verlotta - pasquale.verlotta@gmail.com"
 *
 */
public class HomeLayout implements GeneralLayout{

    private Container pane;
    private LayoutControl control;
    private JTextField login_firstname;
    private JTextField login_secondname;
    private JPasswordField login_pwd;
    private JTextField login_code;
    
    public HomeLayout(LayoutControl control, Container pane){
    	setControl(control);
    	setPane(pane);
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
		c.insets= new Insets(20, 10, 20, 10);
		
		//0.0
		InnerPanelLogin login= new InnerPanelLogin();
		login.setBackground(Color.ORANGE);
		login.setBorder(BorderFactory.createLineBorder(Color.blue));
		login.addComponentsToPane();
		c.gridx=posx;c.gridy=posy;c.weightx=1;
		c.fill = GridBagConstraints.HORIZONTAL;
		pane.add(login, c);
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
	
	private class InnerPanelLogin extends JPanel implements GeneralLayout{
		
		/**
		 * 
		 */
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
			login_firstname= new JTextField("First Name");
			login_firstname.addFocusListener(new FocusListener(){
				
		        @Override
		        public void focusGained(FocusEvent e){
		        	if(login_firstname.getText().equals("First Name"))
		        		login_firstname.setText("");
		        }

				@Override
				public void focusLost(FocusEvent arg0) {
					if(login_firstname.getText().equals(""))
						login_firstname.setText("First Name");
				}
		    });
			this.add(login_firstname, c);
			
			//1.0 - SECONDNAME
			posx++;
			c.gridx=posx;c.gridy=posy;c.weightx=1;c.gridwidth=1;
			login_secondname= new JTextField("Second Name");
			login_secondname.addFocusListener(new FocusListener(){
				
		        @Override
		        public void focusGained(FocusEvent e){
		        	if(login_secondname.getText().equals("Second Name"))
		        		login_secondname.setText("");
		        }

				@Override
				public void focusLost(FocusEvent arg0) {
					if(login_secondname.getText().equals(""))
						login_secondname.setText("Second Name");
				}
		    });
			this.add(login_secondname, c);
			
			//0.1 - PASSWORD
			posx=0;posy++;
			c.gridx=posx;c.gridy=posy;c.weightx=1;c.gridwidth=2;
			login_pwd= new JPasswordField("Password");
			login_pwd.addFocusListener(new FocusListener(){
				
		        @Override
		        public void focusGained(FocusEvent e){
		        	String tmp= new String(login_pwd.getPassword());
		        	if(tmp.equals("Password"))
		        		login_pwd.setText("");
		        }

				@Override
				public void focusLost(FocusEvent arg0) {
					if(login_pwd.getPassword() == null || login_pwd.getPassword().length == 0 )
							login_pwd.setText("Password");
				}
		    });
			this.add(login_pwd, c);
			
			//0.2 - CODE
			posy++;
			c.gridx=posx;c.gridy=posy;c.weightx=1;c.gridwidth=2;
			login_code= new JTextField("Code");
			login_code.addFocusListener(new FocusListener(){
				
		        @Override
		        public void focusGained(FocusEvent e){
		        	if(login_code.getText().equals("Code"))
		        		login_code.setText("");
		        }

				@Override
				public void focusLost(FocusEvent arg0) {
					if(login_code.getText().equals(""))
						login_code.setText("Code");
				}
		    });
			this.add(login_code, c);
			
			//0.3 - LOGIN
			posy++;
			c.gridy= posy;c.gridwidth=1;
			button= new JButton("LOGIN");
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					//TODO aggiungere la chiamata a UserManager
					control.getUser_manager().login();
					getControl().setLayout("PRIMARY");
				}
			});
			button.setBackground(Color.BLUE);
			button.setForeground(Color.WHITE);
			this.add(button, c);
			
			//1.3 - CANCEL
			posx++;
			c.gridx= posx;
			button= new JButton("CANCEL");
			button.setBackground(Color.BLUE);
			button.setForeground(Color.WHITE);
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					login_firstname.setText("User");
					login_pwd.setText("password");
					login_code.setText("Code");
				}
			});
			this.add(button, c);
			
			//0.4
			/*posx=0;posy=4;
			c.gridx=posx;c.gridy=posy;c.gridwidth=4;
			JLabel label= new JLabel("Clicca login per entrare (ora non fa nulla serve giusto per vedere come viene)");
			this.add(label, c);*/
			
			//0.5
			posx--;posy++;
			c.gridx= posx;c.gridy=posy;c.gridwidth=4;
			c.insets= new Insets(10, 5, 1, 5);
			JLabel label= new JLabel("Se e' il primo avvio registrati");
			label.setForeground(Color.BLUE);
			this.add(label, c);
			
			//0.6 - REGISTRATION
			posy++;
			c.gridy=posy;c.gridwidth=1;
			c.insets= new Insets(1, 5, 10, 5);
			button= new JButton("REGISTRATION");
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					getControl().setLayout("REGISTRATION");
				}
			});
			button.setBackground(Color.BLUE);
			button.setForeground(Color.WHITE);
			this.add(button, c);
			
		}
	}

}

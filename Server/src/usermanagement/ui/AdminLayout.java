package usermanagement.ui;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import layout.GeneralLayout;
import util.CertData;

public class AdminLayout implements GeneralLayout {

	private Container pane;
    private LayoutController control;
    
    private JList<CertData> certList;
    private JTextField nameText;
    private JTextField surnameText;
    private JTextField countryText;
    private JTextField countryCodeText;
    private JTextField cityText;
    private JTextField organizationText;
    private JButton deleteUserButton;
    private JButton addUserButton;
    private JButton sendCertificateButton;
    //private DateTime notBefore;
    //private DateTime notAfter;
    
    public AdminLayout(LayoutController control, Container pane){
    	setControl(control);
    	setPane(pane);
    }
    
	@Override
	public void addComponentsToPane() {
		//TODO:completare
		pane.removeAll();
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor= GridBagConstraints.CENTER;
		
		//-->FORMATO: [NOME] Y:X
		
		//LISTA 0.0
		JLabel lbl0 = new JLabel("Lista Utenti:");
		c.insets= new Insets(0, 10, 0, 10);
		c.gridx= 0;c.gridy= 0;c.weightx = 1;c.ipady=0;c.ipadx=0;
		c.gridwidth = 1;
		pane.add(lbl0, c);
		//---
		certList = new JList<CertData>();
		certList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		c.gridx= 0;c.gridy= 1;c.weightx = 0.5;c.ipady=0;c.ipadx=0;
		c.gridwidth = 3;
		c.insets= new Insets(10, 0, 0, 10);
		pane.add(certList,c);
		
		//deleteButton 0.1
		deleteUserButton = new JButton("Delete");
		c.gridx= 3;c.gridy= 1;c.weightx = 0.5;c.ipady=0;c.ipadx=0;
		c.gridwidth = 1;
		pane.add(deleteUserButton,c);
		
		//NOME 2.0
		JLabel lbl1 = new JLabel("Name:");
		c.insets= new Insets(10, 10, 0, 10);
		c.gridx= 0;c.gridy= 2;c.weightx = 1;c.ipady=0;c.ipadx=0;
		c.gridwidth = 1;
		pane.add(lbl1, c);
		//----
		nameText = new JTextField();
		nameText.setEditable(true);
		c.gridx= 1;c.gridy= 2;c.weightx = 10;c.ipady=0;c.ipadx=0;
		c.gridwidth = 3;
		c.insets= new Insets(10, 10, 0, 10);
		pane.add(nameText, c);
		
		//COGNOME 3.0
		JLabel lbl2 = new JLabel("Surname:");
		c.insets= new Insets(10, 10, 0, 10);
		c.gridx= 0;c.gridy= 3;c.weightx = 1;c.ipady=0;c.ipadx=0;
		c.gridwidth = 1;
		pane.add(lbl2, c);
		//---
		surnameText = new JTextField();
		surnameText.setEditable(true);
		c.gridx= 1;c.gridy= 3;c.weightx = 10;c.ipady=0;c.ipadx=0;
		c.gridwidth = 3;
		c.insets= new Insets(10, 10, 0, 10);
		pane.add(surnameText, c);
		
		//COUNTRY 4.0
		JLabel lbl3 = new JLabel("Country:");
		c.insets= new Insets(10, 10, 0, 10);
		c.gridx= 0;c.gridy= 4;c.weightx = 1;c.ipady=0;c.ipadx=0;
		c.gridwidth = 1;
		pane.add(lbl3, c);
		//---
		countryText = new JTextField();
		countryText.setEditable(true);
		c.gridx= 1;c.gridy= 4;c.weightx = 10;c.ipady=0;c.ipadx=0;
		c.gridwidth = 1;
		c.insets= new Insets(10, 10, 0, 10);
		pane.add(countryText, c);
		
		//COUNTRY CODE 4.2
		JLabel lbl4 = new JLabel("Country[ST]:");
		c.insets= new Insets(10, 10, 0, 10);
		c.gridx= 2;c.gridy= 4;c.weightx = 1;c.ipady=0;c.ipadx=0;
		c.gridwidth = 1;
		pane.add(lbl4, c);
		//---
		countryCodeText = new JTextField(2);
		countryCodeText.setEditable(true);
		countryCodeText.setDocument(new JTextFieldLimit(2));//<--limite dimensione testo
		c.gridx= 3;c.gridy= 4;c.weightx = 2;c.ipady=0;c.ipadx=0;
		c.gridwidth = 1;
		c.insets= new Insets(10, 10, 0, 10);
		pane.add(countryCodeText, c);
		
		//CITY 5.0
		JLabel lbl5 = new JLabel("City:");
		c.insets= new Insets(10, 10, 0, 10);
		c.gridx= 0;c.gridy= 5;c.weightx = 1;c.ipady=0;c.ipadx=0;
		c.gridwidth = 1;
		pane.add(lbl5, c);
		//---
		cityText = new JTextField();
		cityText.setEditable(true);
		c.gridx= 1;c.gridy= 5;c.weightx = 10;c.ipady=0;c.ipadx=0;
		c.gridwidth = 3;
		c.insets= new Insets(10, 10, 0, 10);
		pane.add(cityText, c);
		
		//ORGANIZATION 6.0
		JLabel lbl6 = new JLabel("Organization:");
		c.insets= new Insets(10, 10, 0, 10);
		c.gridx= 0;c.gridy= 6;c.weightx = 1;c.ipady=0;c.ipadx=0;
		c.gridwidth = 1;
		pane.add(lbl6, c);
		//---
		organizationText = new JTextField();
		organizationText.setEditable(true);
		c.gridx= 1;c.gridy= 6;c.weightx = 10;c.ipady=0;c.ipadx=0;
		c.gridwidth = 3;
		c.insets= new Insets(10, 10, 0, 10);
		pane.add(organizationText, c);
		
		//Date TODO
		
		//add & send 8.0 - 8.1
		addUserButton = new JButton("Add User");
		c.gridx= 0;c.gridy= 8;c.weightx = 0.5;c.ipady=0;c.ipadx=0;
		c.gridwidth = 2;
		pane.add(addUserButton,c);
		
		addUserButton = new JButton("Send this Certificate");
		c.gridx= 2;c.gridy= 8;c.weightx = 0.5;c.ipady=0;c.ipadx=0;
		c.gridwidth = 2;
		pane.add(addUserButton,c);

	}
	
    public Container getPane() {
		return pane;
	}

	public void setPane(Container pane) {
		this.pane = pane;
	}

	public LayoutController getControl() {
		return control;
	}

	public void setControl(LayoutController control) {
		this.control = control;
	}
	
	//Limite textfield
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

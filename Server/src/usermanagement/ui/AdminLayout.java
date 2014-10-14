package usermanagement.ui;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import util.CertData;
import layout.GeneralLayout;

public class AdminLayout implements GeneralLayout {

	private Container pane;
    private LayoutController control;
    
    private JList<CertData> certList;
    private JTextField nameText;
    private JTextField surnameText;
    private JTextField coutryText;
    private JTextField contryCodeText;
    private JTextField cityText;
    private JTextField organizationText;
    private JButton deleteUserButton;
    private JButton addUserButton;
    private JButton sendCertificateButton;
    
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
		
		certList = new JList<CertData>();
		certList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		c.gridx= 0;c.gridy= 0;c.weightx = 0.5;c.ipady=0;c.ipadx=0;
		c.insets= new Insets(10, 0, 0, 10);
		pane.add(certList,c);
		
		
		nameText = new JTextField("test");
		nameText.setEditable(true);
		c.gridx= 0;c.gridy= 0;c.weightx = 0.5;c.ipady=0;c.ipadx=0;
		c.insets= new Insets(10, 10, 0, 10);
		pane.add(nameText, c);
		
		
		
		

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

}

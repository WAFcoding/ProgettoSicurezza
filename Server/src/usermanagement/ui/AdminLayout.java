package usermanagement.ui;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import layout.GeneralLayout;
import usermanagement.controller.LayoutController;
import usermanagement.controller.RequestController;
/**
 * TODO: x pasquale
 * 
 * dichiarazioni:
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
 * 
 * costruzione:
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
		JLabel notBefore = new JLabel(new Date().toString());
		notBefore.addMouseListener(new MouseClickListener());
		c.gridx= 0;c.gridy= 7;c.weightx = 10;c.ipady=0;c.ipadx=0;
		c.gridwidth = 3;
		c.insets= new Insets(10, 10, 0, 10);
		pane.add(notBefore,c);
		
		//add & send 8.0 - 8.1
		addUserButton = new JButton("Add User");
		c.gridx= 0;c.gridy= 8;c.weightx = 0.5;c.ipady=0;c.ipadx=0;
		c.gridwidth = 2;
		pane.add(addUserButton,c);
		
		addUserButton = new JButton("Send this Certificate");
		c.gridx= 2;c.gridy= 8;c.weightx = 0.5;c.ipady=0;c.ipadx=0;
		c.gridwidth = 2;
		pane.add(addUserButton,c);
 * 
 *---------------------------------------------------------------------------------
 
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
 *
 */


public class AdminLayout implements GeneralLayout {

	private Container pane;
    private LayoutController control;

    private JTabbedPane panel;
    private JPanel pendingPanel;
    private JPanel acceptedPanel;
    private JPanel rejectedPanel;
    
    /*
     *TODO Componenti pubbliche
     */
    
    
    private static final Integer[] levels = {1,2,3,4,5,6,7};//aggiungi altri eventualmente...
    
    public AdminLayout(LayoutController control, Container pane){
    	setControl(control);
    	setPane(pane);
    }
    
	@Override
	public void addComponentsToPane() {
		pane.removeAll();
		pane.setLayout(new CardLayout());
		
		pendingPanel = new JPanel();
		acceptedPanel = new JPanel();
		rejectedPanel = new JPanel();
		
		panel = new JTabbedPane();
		panel.addTab("Pending", pendingPanel);
		panel.addTab("Accepted", acceptedPanel);
		panel.addTab("Rejected", rejectedPanel);
		
		pendingPanel.setLayout(new GridBagLayout());
		acceptedPanel.setLayout(new GridBagLayout());
		rejectedPanel.setLayout(new GridBagLayout());
		
		buildRequestPanel(pendingPanel);
		buildAcceptedPanel(acceptedPanel);
		buildRejectedPanel(rejectedPanel);
		
		pane.add(panel);
	}
	
	private void buildRequestPanel(JPanel p)  {
		//TODO: aggiungere i listener
		
		p.setLayout(new CardLayout());
		
		//super-pannello
		JPanel superPanel = new JPanel();
		superPanel.setLayout(new BoxLayout(superPanel, BoxLayout.Y_AXIS));
		
		//pannello superiore
		JPanel ptable = new JPanel();
		ptable.setLayout(new BoxLayout(ptable, BoxLayout.Y_AXIS));
		JLabel intestazione = new JLabel("Pending Requests:");
		ptable.add(intestazione);
		
		JTable pendingList = new JTable(RequestController.retrieveRequests(), new String[] {"Name", "Surname", "Country", "Country Code", "Organization"});
		JScrollPane scrolling = new JScrollPane(pendingList);
		scrolling.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		ptable.add(scrolling);
		
		//pannello controlli
		JPanel pbuttons = new JPanel();
		pbuttons.setLayout(new BoxLayout(pbuttons, BoxLayout.LINE_AXIS));
		JButton btnAccept = new JButton("Accept");
		btnAccept.setEnabled(false);
		pbuttons.add(btnAccept);
		
		JButton btnReject = new JButton("Reject");
		btnReject.setEnabled(false);
		pbuttons.add(btnReject);
		pbuttons.add(Box.createRigidArea(new Dimension(15,0)));
		
		JLabel trustLabel = new JLabel("Trust Level:");
		pbuttons.add(trustLabel);
		
		JComboBox<Integer> trustLevel = new JComboBox<Integer>(levels);
		
		trustLevel.setMaximumSize(new Dimension(100,200));
		
		pbuttons.add(trustLevel);
		
		//collega al super-layout
		superPanel.add(ptable);
		superPanel.add(pbuttons);
		
		//aggiungi la card
		p.add(superPanel);
	}
	
	private void buildAcceptedPanel(JPanel p)  {
		p.setLayout(new CardLayout());
		
		//super-pannello
		JPanel superPanel = new JPanel();
		superPanel.setLayout(new BoxLayout(superPanel, BoxLayout.Y_AXIS));
		
		//pannello superiore
		JPanel ptable = new JPanel();
		ptable.setLayout(new BoxLayout(ptable, BoxLayout.Y_AXIS));
		JLabel intestazione = new JLabel("Trusted Users:");
		ptable.add(intestazione);
		
		JTable trustedList = new JTable(RequestController.retrieveAccepted(), new String[] {"Name", "Surname", "Country", "Country Code", "Organization"});
		JScrollPane scrolling = new JScrollPane(trustedList);
		scrolling.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		ptable.add(scrolling);
		
		//pannello controlli
		JPanel pbuttons = new JPanel();
		pbuttons.setLayout(new BoxLayout(pbuttons, BoxLayout.LINE_AXIS));
		JButton btnBlock = new JButton("Block");
		btnBlock.setEnabled(false);
		pbuttons.add(btnBlock);
		
		JButton btnUpdate = new JButton("Update Level");
		btnUpdate.setEnabled(false);
		pbuttons.add(btnUpdate);
		pbuttons.add(Box.createRigidArea(new Dimension(15,0)));
		
		JLabel trustLabel = new JLabel("Trust Level:");
		pbuttons.add(trustLabel);
		
		JComboBox<Integer> trustLevel = new JComboBox<Integer>(levels);
		
		trustLevel.setMaximumSize(new Dimension(100,200));
		
		pbuttons.add(trustLevel);
		
		//collega al super-layout
		superPanel.add(ptable);
		superPanel.add(pbuttons);
		
		//aggiungi la card
		p.add(superPanel);
	}
	
	private void buildRejectedPanel(JPanel p)  {
		p.setLayout(new CardLayout());
		
		//super-pannello
		JPanel superPanel = new JPanel();
		superPanel.setLayout(new BoxLayout(superPanel, BoxLayout.Y_AXIS));
		
		//pannello superiore
		JPanel ptable = new JPanel();
		ptable.setLayout(new BoxLayout(ptable, BoxLayout.Y_AXIS));
		JLabel intestazione = new JLabel("Blocked Users:");
		ptable.add(intestazione);
		
		JTable blockedList = new JTable(RequestController.retrieveBlocked(), new String[] {"Name", "Surname", "Country", "Country Code", "Organization"});
		JScrollPane scrolling = new JScrollPane(blockedList);
		scrolling.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		ptable.add(scrolling);
		
		//pannello controlli
		JPanel pbuttons = new JPanel();
		pbuttons.setLayout(new BoxLayout(pbuttons, BoxLayout.LINE_AXIS));
		
		JButton btnUnblock = new JButton("UnBlock");
		btnUnblock.setEnabled(false);
		pbuttons.add(btnUnblock);
		pbuttons.add(Box.createRigidArea(new Dimension(15,0)));
		
		JLabel trustLabel = new JLabel("Trust Level:");
		pbuttons.add(trustLabel);
		
		JComboBox<Integer> trustLevel = new JComboBox<Integer>(levels);
		
		trustLevel.setMaximumSize(new Dimension(100,200));
		
		pbuttons.add(trustLevel);
		
		//collega al super-layout
		superPanel.add(ptable);
		superPanel.add(pbuttons);
		
		//aggiungi la card
		p.add(superPanel);
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
	
	
	
	private class MouseClickListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}
		
	}

}

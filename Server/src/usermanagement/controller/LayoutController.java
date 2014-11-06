package usermanagement.controller;

import javax.swing.JFrame;

import usermanagement.ui.AdminLayout;
/**
 * Questa classe e' il controllore del layout del server
 * 
 * @author Giovanni Rossi
 */

public class LayoutController {
	
	private final static int WIDTH= 400;
	private final static int HEIGHT= 400;
	
	public JFrame mainFrame;
	private AdminLayout admin_layout;

	
	private enum LAYOUT{
		ADMIN
	}
	
	public LayoutController(){
		mainFrame= new JFrame("Pannello Admin Server");
		mainFrame.setDefaultCloseOperation(/*FIXME:JFrame.DISPOSE_ON_CLOSE*/JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(WIDTH, HEIGHT);
		mainFrame.setVisible(true);
		mainFrame.setLocation(150, 100);
		setLayout("ADMIN");
	}
	
	//=========================================================================
	//=================GESTIONE LAYOUT=========================================
	/**
	 * Imposta il layout da visualizzare
	 * @param layout int 0=Admin
	 */
	public void setLayout(String name_layout){
		int layout= getLayoutByName(name_layout);
		if(layout == 0){
			AdminLayout();
			mainFrame.setSize(WIDTH, HEIGHT);
		}
		
		//mainFrame.pack();
		mainFrame.repaint();
		mainFrame.validate();
	}	
	
	public int getLayoutByName(String name_layout){
		
		LAYOUT lay= LAYOUT.valueOf(name_layout);
		switch (lay){
			case ADMIN:
				return 0;
			default:
				return 0;
		}
	}

	public void AdminLayout(){
		if(admin_layout == null){
			admin_layout= new AdminLayout(this, mainFrame.getContentPane());
			admin_layout.addComponentsToPane();
		}
		else{
			admin_layout.addComponentsToPane();
		}
	}
}

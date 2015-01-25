/**
 * Pasquale Verlotta - pasquale.verlotta@gmail.com
 * ProgettoSicurezzaV0.0 - 22/gen/2015
 */
package layout;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author pasquale
 *
 */
public class ReceiverSettingsLayout implements GeneralLayout, ListSelectionListener {

	private LayoutControl control;
	private Container pane;
	private boolean singleUser;
    private JList<String> list;
    private DefaultListModel<String> list_model;
    private ArrayList<String> list_items;


	public ReceiverSettingsLayout(LayoutControl control, Container pane, boolean singleUser) {
		setControl(control);
		setPane(pane);
		setSingleUser(singleUser);
        
    	list= new JList<String>();
        list_model= new DefaultListModel<String>();
        
        list.setModel(list_model);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.addListSelectionListener(this);
        list.setSelectedIndex(-1);
	}

	@Override
	public void addComponentsToPane() {
		
		//inserimento pulsanti
        pane.removeAll();
		pane.setLayout(new GridBagLayout());
		pane.setSize(800, 600);
		int posx= 0, posy= 0;
		GridBagConstraints c = new GridBagConstraints();
		
		if(isSingleUser()){

			//TODO richiesta al db di un utente specifico, se non presente nel db locale richiedere a quello remoto
			updateList();
			
		}
		else{

			//TODO richiesta al server della chiave di livello
			updateList();
		}
		
		
	}

	public LayoutControl getControl() {
		return control;
	}

	public void setControl(LayoutControl control) {
		this.control = control;
	}

	public Container getPane() {
		return pane;
	}

	public void setPane(Container pane) {
		this.pane = pane;
	}

	public boolean isSingleUser() {
		return singleUser;
	}

	public void setSingleUser(boolean singleUser) {
		this.singleUser = singleUser;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void updateList(){
		if(this.list_items != null){
	        list_model.clear();
	        for(String s : list_items){
	        	list_model.addElement(s);
	        }
		}
	}
	
	public void setListItems(ArrayList<String> items){
		if(this.list_items != null){
			this.list_items.clear();
		}
		this.list_items= items;
	}

}

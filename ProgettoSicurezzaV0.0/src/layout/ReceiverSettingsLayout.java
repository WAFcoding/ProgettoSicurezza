/**
 * Pasquale Verlotta - pasquale.verlotta@gmail.com
 * ProgettoSicurezzaV0.0 - 22/gen/2015
 */
package layout;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import usersManagement.User;
import connection.ConnectionFactory;
import connection.KeyDistributionClient;

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
    private ArrayList<User> all_user;
    private ArrayList<String> all_level_key;
    private JScrollPane scroll_pane;
	public final static String keystore_pwd= "progettoSII";
	public final static String url= "localhost";//TODO UserManager: inserire il url nei settings
    private String list_selected;
    private int pos_list_selected;
    private User actualUser;


	public ReceiverSettingsLayout(LayoutControl control, Container pane) {
		actualUser= control.getUser_manager().getActualUser();
		setControl(control);
		setPane(pane);
		
		all_user= new ArrayList<User>();
		all_level_key= new ArrayList<String>();
        
    	list= new JList<String>();
        list_model= new DefaultListModel<String>();
        
        list.setModel(list_model);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.addListSelectionListener(this);
        list.setSelectedIndex(-1);

		list_items= new ArrayList<String>();
	}

	@Override
	public void addComponentsToPane() {


		String username= actualUser.getName() + "_" + actualUser.getID();
		String pwd= control.getUser_manager().getActualUser().getPassword();
		try (KeyDistributionClient cli = ConnectionFactory.getKeyDistributionServerConnection(url, username, keystore_pwd, pwd)) {
			if(isSingleUser()){
				List<User> users = cli.getAllUsers();
				list_items.clear();
				all_user.clear();
				for(User u : users){
					all_user.add(u);
					list_items.add(u.getName() + " " + u.getSurname() + " - " + u.getTrustLevel());
				}
			}
			else{
				Map<Integer, String> levelKey = cli.getAllAuthorizedLevelKey();
				//TODO ordinare le chiavi della mappa
				control.setKeyLevelMap(levelKey);
				list_items.clear();
				all_level_key.clear();
				Iterator it = levelKey.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pairs = (Map.Entry)it.next();
					list_items.add("[ " +pairs.getKey() + " ] " + pairs.getValue());
					all_level_key.add((String)pairs.getValue());
					it.remove(); // avoids a ConcurrentModificationException
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		updateList();
		
		//inserimento pulsanti
        pane.removeAll();
		pane.setLayout(new GridBagLayout());
		//pane.setSize(800, 600);
        scroll_pane= new JScrollPane(list);
        scroll_pane.setPreferredSize(new Dimension(300, 300));
        JButton button;
		int posx= 0, posy= 0;
		GridBagConstraints c = new GridBagConstraints();
		c.anchor= GridBagConstraints.WEST;
		c.fill = GridBagConstraints.BOTH;
		c.insets= new Insets(10, 10, 10, 10);
		c.gridx=posx;c.gridy=posy;
		c.gridheight=4;c.gridwidth=1;c.weighty= 1;c.weightx=1;
		pane.add(scroll_pane, c);
		//1.0 - BACK
		posx=3;
		c.gridx=posx;c.gridy=posy;
		c.gridheight=1;c.fill= GridBagConstraints.HORIZONTAL;
		c.gridwidth=1;c.weighty= 0;c.weightx=0;
		button= new JButton("BACK");
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				control.setLayout("WRITE");
			}
		});
		pane.add(button, c);
		
		//1.1 - SELECT
		posy++;
		c.gridx=posx;c.gridy=posy;
		c.gridheight=1;
		button= new JButton("SELECT");
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isSingleUser())
					control.setUserForEncryptOrDecrypt(all_user.get(pos_list_selected));
				else
					control.setKeyLevelForEncryptDecrypt(pos_list_selected+1);
				control.setLayout("WRITE");
			}
		});		
		pane.add(button, c);
		
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
		if(!e.getValueIsAdjusting()){
			if(list.getSelectedValuesList().size() > 1){
				System.out.println("selezione multipla ");
				for(String s : list.getSelectedValuesList()){

					System.out.println(s);
				}
			}
			else{
				String selected= list.getSelectedValue();
				int pos_selected= list.getSelectedIndex();
				if(selected != null && pos_selected != -1){
					setList_selected(selected);
					setPos_list_selected(pos_selected);
					System.out.println("selezionato " + selected + ", posizione " + pos_selected);
				}
			}
		}
	}
	public String getList_selected() {
		return list_selected;
	}

	public void setList_selected(String list_selected) {
		this.list_selected = list_selected;
	}
	public int getPos_list_selected() {
		return pos_list_selected;
	}

	public void setPos_list_selected(int pos_list_selected) {
		this.pos_list_selected = pos_list_selected;
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

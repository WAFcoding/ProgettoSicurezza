/**
 * Pasquale Verlotta - pasquale.verlotta@gmail.com
 * ProgettoSicurezzaV0.0 - 10/feb/2015
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
import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author pasquale
 *
 */
public class DecodeLayout implements GeneralLayout, ListSelectionListener{
    private Container pane;
    private LayoutControl control;
    private JList<String> list;
    private DefaultListModel<String> list_model;
    private JScrollPane scroll_pane;
    private ArrayList<String> list_item;
    private String list_selected;
    private int pos_list_selected;
    private JButton btn_image, btn_file;

	public DecodeLayout(LayoutControl control, Container pane, ArrayList<String> items){
    	setPane(pane);
    	setControl(control);
        this.list_item= items;
        
    	list= new JList<String>();
        list_model= new DefaultListModel<String>();
        
        list.setModel(list_model);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.addListSelectionListener(this);
        list.setSelectedIndex(-1);
	}
	
	@Override
	public void addComponentsToPane() {
    	//inizializzazione
        JButton button;
        scroll_pane= new JScrollPane(list);
        scroll_pane.setPreferredSize(new Dimension(300, 300));
        
        updateList();
        
        //inserimento pulsanti
        pane.removeAll();
		pane.setLayout(new GridBagLayout());
		//pane.setSize(800, 600);
		
		int posx= 0, posy= 0;
		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor= GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.insets= new Insets(10, 10, 10, 10);
		
		//0.1 -SCROLL PANE
		posx=0;posy=0;
		c.gridx=posx;c.gridy=posy;c.gridheight=6;c.gridwidth=2;c.weighty= 1;c.weightx=1;
		c.fill = GridBagConstraints.BOTH;
		//c.ipadx=scroll_pane.getSize().width;c.ipady= scroll_pane.getSize().height;
		pane.add(scroll_pane, c);
		//2.1 - DECODE
		posx=2;
		button = new JButton("DECODE");
		c.gridx = posx;c.gridy= posy;c.gridheight=1;c.gridwidth=1;c.ipadx=0;c.ipady= 0;
		c.fill= GridBagConstraints.HORIZONTAL;c.weighty= 0;c.weightx=0;
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		button.addActionListener(new DecodeAction());
		pane.add(button, c);
		//2.2 - AGGIUNGI
		posy++;
		button = new JButton("AGGIUNGI");
		c.gridx = posx;c.gridy=posy;c.gridheight=1;c.ipadx=0;
		button.addActionListener(new AddAction());
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		pane.add(button, c);
		//2.3 - ELIMINA
		posy++;
		button= new JButton("ELIMINA");
		c.gridx = posx;c.gridy = posy;c.gridheight=1;c.ipadx=0;
		button.addActionListener(new RemoveAction());
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		pane.add(button, c);
		//2.4 - BACK
		posy++;
		button= new JButton("BACK");
		c.gridx = posx;c.gridy = posy;c.gridheight=1;c.ipadx=0;
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getControl().setLayout("PRIMARY");
			}
		});
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		pane.add(button, c);
		
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

	/**
	 * @return the list
	 */
	public JList<String> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(JList<String> list) {
		this.list = list;
	}

	/**
	 * @return the list_model
	 */
	public DefaultListModel<String> getList_model() {
		return list_model;
	}

	/**
	 * @param list_model the list_model to set
	 */
	public void setList_model(DefaultListModel<String> list_model) {
		this.list_model = list_model;
	}

	/**
	 * @return the scroll_pane
	 */
	public JScrollPane getScroll_pane() {
		return scroll_pane;
	}

	/**
	 * @param scroll_pane the scroll_pane to set
	 */
	public void setScroll_pane(JScrollPane scroll_pane) {
		this.scroll_pane = scroll_pane;
	}

	/**
	 * @return the list_item
	 */
	public ArrayList<String> getList_item() {
		return list_item;
	}

	/**
	 * @param list_item the list_item to set
	 */
	public void setList_item(ArrayList<String> list_item) {
		this.list_item = list_item;
	}

	/**
	 * @return the list_selected
	 */
	public String getList_selected() {
		return list_selected;
	}

	/**
	 * @param list_selected the list_selected to set
	 */
	public void setList_selected(String list_selected) {
		this.list_selected = list_selected;
	}

	/**
	 * @return the pos_list_selected
	 */
	public int getPos_list_selected() {
		return pos_list_selected;
	}

	/**
	 * @param pos_list_selected the pos_list_selected to set
	 */
	public void setPos_list_selected(int pos_list_selected) {
		this.pos_list_selected = pos_list_selected;
	}

	/**
	 * @return the btn_image
	 */
	public JButton getBtn_image() {
		return btn_image;
	}

	/**
	 * @param btn_image the btn_image to set
	 */
	public void setBtn_image(JButton btn_image) {
		this.btn_image = btn_image;
	}

	/**
	 * @return the btn_file
	 */
	public JButton getBtn_file() {
		return btn_file;
	}

	/**
	 * @param btn_file the btn_file to set
	 */
	public void setBtn_file(JButton btn_file) {
		this.btn_file = btn_file;
	}
	/**
	 * aggiorna la lista di file visualizzata nel layout encode
	 */
	public void updateList(){
        
        list_model.clear();
        for(String s : list_item){
        	list_model.addElement(s);
        }
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}	
	
	private class DecodeAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
	
			if(getList_selected() != null){

				
			}
			else{
				JOptionPane.showMessageDialog(getPane(), "devi selezionare un file");
			}
		}
	}
	//l'azione da compiere alla pressione di aggiungi
	private class AddAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser file_chooser= new JFileChooser(control.getUser_manager().getActualUser().getDir_in());
			int choose= file_chooser.showDialog(null, "apri");
			
			if(choose == JFileChooser.APPROVE_OPTION){
				File file= file_chooser.getSelectedFile();
				
				if(control.isToDecode(file)){
					control.addDecodeChoice(file.getAbsolutePath());
				}
				else{
					JOptionPane.showMessageDialog(getPane(), "Deve essere un file PDF o un'immagine");
				}
			}
		}
	}
	//l'azione da compiere alla pressione di elimina
	private class RemoveAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			control.removeItem(getPos_list_selected());
			updateList();
			System.out.println("rimosso elemento in posizione " + getPos_list_selected());
		}
	}

}

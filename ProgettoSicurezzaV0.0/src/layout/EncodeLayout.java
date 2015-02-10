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
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
/**
 * Questa classe rappresenta il layout della finestra per codificare l'immagine
 * 
 * @author "Pasquale Verlotta - pasquale.verlotta@gmail.com"
 *
 */
public class EncodeLayout implements GeneralLayout, ListSelectionListener{
    private Container pane;
    private LayoutControl control;
    private JList<String> list;
    private DefaultListModel<String> list_model;
    private JScrollPane scroll_pane;
    private ArrayList<String> list_item;
    private String list_selected;
    private int pos_list_selected;
    
    public EncodeLayout(LayoutControl control, Container pane, ArrayList<String> items){
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
		
		//0.0 -SCROLL PANE
		c.gridx=posx;c.gridy=posy;c.gridheight=6;c.gridwidth=2;c.weighty= 1;c.weightx=1;
		c.fill = GridBagConstraints.BOTH;
		//c.ipadx=scroll_pane.getSize().width;c.ipady= scroll_pane.getSize().height;
		pane.add(scroll_pane, c);
		//2.1 - ENCODE
		posx=2;
		button = new JButton("ENCODE");
		c.gridx = posx;c.gridy= posy;c.gridheight=1;c.gridwidth=1;c.ipadx=0;c.ipady= 0;
		c.fill= GridBagConstraints.HORIZONTAL;c.weighty= 0;c.weightx=0;
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		button.addActionListener(new EncodeAction());
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
	//l'azione eseguita alla selezione di un file nella lista
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

	public ArrayList<String> getList_item() {
		return list_item;
	}

	public void setList_item(ArrayList<String> list_item) {
		this.list_item = list_item;
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
	//l'azione da compiere alla pressione encode
	private class EncodeAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
	
			if(getList_selected() != null){

				control.setWriteLayoutCurrentFile(getList_selected());
				control.setLayout("WRITE");
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
				
				if(control.isText(file)){
					control.addEncodeChoice(file.getAbsolutePath());
				}
				else{
					JOptionPane.showMessageDialog(getPane(), "deve essere un file txt");
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

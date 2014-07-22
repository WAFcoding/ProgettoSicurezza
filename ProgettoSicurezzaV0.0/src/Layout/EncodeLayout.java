package Layout;

import java.awt.ComponentOrientation;
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
    
    public EncodeLayout(LayoutControl control, Container pane, ArrayList<String> items){
    	setPane(pane);
    	setControl(control);
        this.list_item= items;
        
    	list= new JList<String>();
        list_model= new DefaultListModel<String>();
        
        list.setModel(list_model);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.addListSelectionListener(this);
    }
    
    @Override
    public void addComponentsToPane() {
    	//inizializzazione
        JButton button;
        JLabel label;
        scroll_pane= new JScrollPane(list);
        scroll_pane.setPreferredSize(new Dimension(200, 200));
        
        
        updateList();
        
        //inserimento pulsanti
        pane.removeAll();
		pane.setLayout(new GridBagLayout());
		
		int posx= 0, posy= 0;
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.anchor= GridBagConstraints.CENTER;
		c.insets= new Insets(10, 10, 10, 10);
		
		//0.0 - LABEL
		c.gridx=posx;c.gridy=posy;
		label= new JLabel("File aggiunti");
		pane.add(label, c);
		//0.1 -SCROLL PANE
		posy++;
		c.gridx=posx;c.gridy=posy;c.gridheight=6;c.weighty= 1;
		c.ipadx=scroll_pane.getSize().width;c.ipady= scroll_pane.getSize().height;
		pane.add(scroll_pane, c);
		//1.1 - ENCODE
		posx++;
		button = new JButton("ENCODE");
		c.gridx = posx;c.gridy= posy;c.weightx = 0.5;c.weighty= 0;c.gridheight=1;c.ipadx=0;c.ipady= 0;
		c.fill= GridBagConstraints.HORIZONTAL;
		pane.add(button, c);
		//1.2 - AGGIUNGI
		posy++;
		button = new JButton("AGGIUNGI");
		c.gridx = posx;c.gridy=posy;c.weightx = 0;c.gridheight=1;c.ipadx=0;
		button.addActionListener(new AddAction());
		pane.add(button, c);
		//1.3 - ELIMINA
		posy++;
		button= new JButton("ELIMINA");
		c.gridx = posx;c.gridy = posy;c.weightx = 0.5;c.weighty=0;c.gridheight=1;c.ipadx=0;
		//button.addActionListener(new RemoveAction());
		pane.add(button, c);
		//1.4 - BACK
		posy++;
		button= new JButton("BACK");
		c.gridx = posx;c.gridy = posy;c.weightx = 0.5;c.weighty=0;c.gridheight=1;c.ipadx=0;
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getControl().setLayout(0);
			}
		});
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
				setList_selected(list.getSelectedValue());
				System.out.println("selezionato " + list.getSelectedValue());
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
	//l'azione da compiere alla pressione encode
	private class EncodeAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
		
	}
	//l'azione da compiere alla pressione di aggiungi
	private class AddAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser file_chooser= new JFileChooser();
			int choose= file_chooser.showDialog(null, "apri");
			
			if(choose == JFileChooser.APPROVE_OPTION){
				File file= file_chooser.getSelectedFile();
				control.addChoice(file.getAbsolutePath());
				updateList();
			}
		}
    }	
}

package Layout;

import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
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
    
    public EncodeLayout(LayoutControl control, Container pane, ArrayList<String> items){
    	setPane(pane);
    	setControl(control);
        this.list_item= items;
        
    	list= new JList<String>();
        list_model= new DefaultListModel<String>();
        /*list_model.addElement("prova1");
        list_model.addElement("prova2");
        list_model.addElement("prova3");
        list_model.addElement("prova4");
        list_model.addElement("prova5");
        list_model.addElement("prova6");
        list_model.addElement("prova7");
        list_model.addElement("prova8");
        list_model.addElement("prova3");
        list_model.addElement("prova4");
        list_model.addElement("prova5");
        list_model.addElement("prova6");
        list_model.addElement("prova7");
        list_model.addElement("prova8");*/
        
        list.setModel(list_model);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        //list.setSelectedIndex(0);
        list.addListSelectionListener(this);
    }
    
    @Override
    public void addComponentsToPane() {
    	//inizializzazione
        JButton button;
        JLabel label;
        scroll_pane= new JScrollPane(list);
        
        list_model.clear();
        for(String s : list_item){
        	list_model.addElement(s);
        }
        //scroll_pane.setSize(new Dimension(200, 200));
        
        //inserimento pulsanti
        pane.removeAll();
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor= GridBagConstraints.CENTER;
		//c.ipady= 40;
		c.insets= new Insets(10, 10, 10, 10);

		c.gridx=0;c.gridy=0;
		label= new JLabel("File aggiunti");
		pane.add(label, c);
		
		c.gridx=0;c.gridy=1;c.gridheight=2;c.ipadx=10;
		pane.add(scroll_pane, c);

		c.fill = GridBagConstraints.NONE;
		button = new JButton("ENCODE");
		c.gridx = 1;c.gridy = 1;c.weightx = 0.5;c.gridheight=1;c.ipadx=0;
		pane.add(button, c);
		
		button= new JButton("BACK");
		c.gridx = 1;c.gridy = 2;c.weightx = 0.5;
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

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(!e.getValueIsAdjusting())
			System.out.println("selezionato " + list.getSelectedValue());
	}

	public ArrayList<String> getList_item() {
		return list_item;
	}

	public void setList_item(ArrayList<String> list_item) {
		this.list_item = list_item;
	}
}

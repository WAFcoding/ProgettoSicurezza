package Layout;

import java.awt.Color;
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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import exceptions.MagickImageNullException;
import magick.MagickException;
import magick.MagickImage;
import util.MagickUtility;
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
    
    private static final String OUTPUT_FOLDER="/home/pasquale/ProgettoSicurezza/";
    
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
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		button.addActionListener(new EncodeAction());
		pane.add(button, c);
		//1.2 - AGGIUNGI
		posy++;
		button = new JButton("AGGIUNGI");
		c.gridx = posx;c.gridy=posy;c.weightx = 0;c.gridheight=1;c.ipadx=0;
		button.addActionListener(new AddAction());
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		pane.add(button, c);
		//1.3 - ELIMINA
		posy++;
		button= new JButton("ELIMINA");
		c.gridx = posx;c.gridy = posy;c.weightx = 0.5;c.weighty=0;c.gridheight=1;c.ipadx=0;
		button.addActionListener(new RemoveAction());
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
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
				control.drawImage(getList_selected(), 1);
				/*MagickImage img= MagickUtility.getImage(getList_selected());
				MagickImage cropped = MagickUtility.cropImage(img, 10, 50, 400, 200);
				MagickImage covered = MagickUtility.coverWithImage(img, cropped, 400, 400);
				MagickUtility.saveImage(covered, OUTPUT_FOLDER + "covered.jpg");
				MagickUtility.saveImage(cropped, OUTPUT_FOLDER + "cropped.jpg");
				
				MagickImage rect = MagickUtility.createRectangleImage(new Color(255, 0, 0), 100, 100);
				MagickImage rectText = MagickUtility.createRectangleImageWithText(new Color(255,0,0), "BELLA X TE!!", new Color(255,255,255), 12.0, 45, 45, 400, 400);
				MagickImage covered2 = MagickUtility.coverWithImage(img, rect, 30, 30);
				MagickImage covered3 = MagickUtility.coverWithImage(covered2, rectText, 60, 60);
				MagickUtility.saveImage(covered2, OUTPUT_FOLDER + "covered2.jpg");
				MagickUtility.saveImage(covered3, OUTPUT_FOLDER + "covered3.jpg");*/
			}
			else{
				JOptionPane.showMessageDialog(getPane(), "You must select a path");
			}
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
				//updateList();
			}
		}
	}
	//l'azione da compiere alla pressione di elimina
	private class RemoveAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			control.removeItem(getPos_list_selected());
			System.out.println("rimosso elemento in posizione " + getPos_list_selected());
		}
	}
}

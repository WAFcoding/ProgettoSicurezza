package Layout;

import javax.swing.JFrame;

public class LayoutControl {
	
	private static final int PRIMARY= 0;
	private static final int ENCODE= 1;
	
	private static JFrame mainFrame;
	
	public void createLayout(){
		
		mainFrame= new JFrame("Progetto SII");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(480, 320);
		mainFrame.setVisible(true);
		setLayout(0);
	}
	
	public static void setLayout(int layout){
		
		
		
		if(layout == 0){
			PrimaryLayout();
		}
		else if(layout == 1){
			EncodeLayout();
		}
		
		mainFrame.pack();
	}

	public static void PrimaryLayout(){
		PrimaryLayout.addComponentsToPane(mainFrame.getContentPane());
	}
	
	public static void EncodeLayout(){
		EncodeLayout.addComponentsToPane(mainFrame.getContentPane());
	}
}

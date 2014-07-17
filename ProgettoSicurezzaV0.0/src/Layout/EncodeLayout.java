package Layout;

import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;

public class EncodeLayout {
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;
    
	 public static void addComponentsToPane(Container pane) {
	        if (RIGHT_TO_LEFT) {
	            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	        }

	        JButton button;
	        pane.removeAll();
			pane.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			
			if (shouldFill) {
				//natural height, maximum width
				c.fill = GridBagConstraints.HORIZONTAL;
			}
			c.anchor= GridBagConstraints.CENTER;
			c.ipady= 40;
			c.insets= new Insets(10, 10, 10, 10);

			button = new JButton("ENCODE");
			if (shouldWeightX) {
				c.weightx = 0.5;
			}
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			c.gridy = 0;
			pane.add(button, c);
	 }
}

package usermanagement.ui;

import java.awt.Container;

import layout.GeneralLayout;

public class AdminLayout implements GeneralLayout {

	private Container pane;
    private LayoutController control;
    
    public AdminLayout(LayoutController control, Container pane){
    	setControl(control);
    	setPane(pane);
    }
    
	@Override
	public void addComponentsToPane() {
		// TODO Auto-generated method stub

	}
	
    public Container getPane() {
		return pane;
	}

	public void setPane(Container pane) {
		this.pane = pane;
	}

	public LayoutController getControl() {
		return control;
	}

	public void setControl(LayoutController control) {
		this.control = control;
	}

}

package gui;

import javax.swing.JPanel;
/**
 * This Class is the Abstract ToolViewerPanel.
 * Use the isactive methods to see if a panel is being viewed. 
 * @author ABI team 37
 * @version 1.0
 *
 */
public abstract class ToolViewer extends JPanel{

	private static final long serialVersionUID = -1828811642515831707L;
	private Boolean active = false;
	
	/**
	 * Sets panel to active.
	 */
	public void active() {
		this.active = true;
	}

	/**
	 * Sets panel to inactive.
	 */
	public void inactive() {
		this.active = false;
	}

	/**
	 * Checks if Panel is active or not.
	 * @return true is active.
	 */
	public boolean isActive() {
		return this.active;
	}
	
	
}

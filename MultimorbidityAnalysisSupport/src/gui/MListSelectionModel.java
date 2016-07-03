package gui;

import javax.swing.DefaultListSelectionModel;

/**
 * Class overrides the selection method of a JList.
 * Normal behavoir requires user to use the Ctrl button to multiselect
 * This makes multiselect the default behavior.
 * @author ABI team 37
 * @version 1.0
 *
 */
public class MListSelectionModel extends DefaultListSelectionModel{

	private static final long serialVersionUID = 3684373867954620479L;

	@Override
	public void setSelectionInterval(int index0, int index1) {
	   if(super.isSelectedIndex(index0)) {
	        super.removeSelectionInterval(index0, index1);
	   }
	   else {
	      super.addSelectionInterval(index0, index1);
	   }
	}

}

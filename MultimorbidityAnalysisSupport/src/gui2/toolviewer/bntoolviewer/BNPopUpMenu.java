package gui2.toolviewer.bntoolviewer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;

public class BNPopUpMenu extends JPopupMenu {
	JMenuItem anItem;
    
	    public BNPopUpMenu(){
	    	JMenuItem anItem_upd_name       = new JMenuItem("Change name");
	    	JMenuItem anItem_upd_direction  = new JMenuItem("Change direction");

	        add(anItem_upd_name);
	        add(anItem_upd_direction);

	    	addSeparator();

	    	JMenuItem anItem_CPT            = new JMenuItem("View CPT");
	    	JMenuItem anItem_showCPT        = new JMenuItem("View CPT");
	    	JMenuItem anItem_upd_CPT        = new JMenuItem("Update CPT");
	        add(anItem_showCPT);
	        add(anItem_upd_CPT);

	    	addSeparator();
	    	JMenuItem anItem_Create_nodeT   = new JMenuItem("Create new node");
	        add(anItem_Create_nodeT);

	        //a submenu
	        addSeparator();
	        JMenu submenu = new JMenu("Evidence");
	        submenu.setMnemonic(KeyEvent.VK_S);
	        add(submenu);
	        
	        JMenuItem sm_enter_Evidence      = new JMenuItem("Enter evidence");
	        submenu.add(sm_enter_Evidence);
	        
	        //a group of radio button menu items
	        addSeparator();
	        ButtonGroup group = new ButtonGroup();
	        JRadioButtonMenuItem rbMenuItem = new JRadioButtonMenuItem("A radio button menu item");
	        rbMenuItem.setSelected(true);
	        //rbMenuItem.setMnemonic(KeyEvent.VK_R);
	        group.add(rbMenuItem);
	        add(rbMenuItem);
	        
	      //a group of check box menu items
	        addSeparator();
	        JCheckBoxMenuItem cbMenuItem = new JCheckBoxMenuItem("A check box menu item");
	        cbMenuItem.setMnemonic(KeyEvent.VK_C);
	        add(cbMenuItem);
	        
	        anItem_upd_name.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	 System.out.println("cliked");
	            }
	        });

	        anItem_Create_nodeT.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	
	            	 System.out.println("cliked");
	            }
	        });
	        
	    
	}
	
	public BNPopUpMenu(NodeLabel dashedLineLabel){
    	JMenuItem anItem_upd_name       = new JMenuItem("Change name");
    	JMenuItem anItem_upd_direction  = new JMenuItem("Change direction");

        add(anItem_upd_name);
        add(anItem_upd_direction);

    	addSeparator();

    	JMenuItem anItem_CPT            = new JMenuItem("View CPT");
    	JMenuItem anItem_showCPT        = new JMenuItem("View CPT");
    	JMenuItem anItem_upd_CPT        = new JMenuItem("Update CPT");
        add(anItem_showCPT);
        add(anItem_upd_CPT);

    	addSeparator();
    	JMenuItem anItem_Create_nodeT   = new JMenuItem("Create new node");
        add(anItem_Create_nodeT);

        //a submenu
        addSeparator();
        JMenu submenu = new JMenu("Evidence");
        submenu.setMnemonic(KeyEvent.VK_S);
        add(submenu);
        
        JMenuItem sm_enter_Evidence      = new JMenuItem("Enter evidence");
        submenu.add(sm_enter_Evidence);
        
        //a group of radio button menu items
        addSeparator();
        ButtonGroup group = new ButtonGroup();
        JRadioButtonMenuItem rbMenuItem = new JRadioButtonMenuItem("A radio button menu item");
        rbMenuItem.setSelected(true);
        //rbMenuItem.setMnemonic(KeyEvent.VK_R);
        group.add(rbMenuItem);
        add(rbMenuItem);
        
      //a group of check box menu items
        addSeparator();
        JCheckBoxMenuItem cbMenuItem = new JCheckBoxMenuItem("A check box menu item");
        cbMenuItem.setMnemonic(KeyEvent.VK_C);
        add(cbMenuItem);
        
        anItem_upd_name.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	 System.out.println("cliked");
            }
        });

        anItem_Create_nodeT.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	 System.out.println("cliked");
            }
        });
        
    }

    
}

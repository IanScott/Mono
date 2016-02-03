package poc2.util;

import java.io.File;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import poc2.domain.BNTool;
import poc2.domain.ToolController;
import poc2.gui.BNFrame;
import poc2.gui.DataviewerFrame;
import poc2.gui.GuiController;
import poc2.gui.StartFrame;


public class Main {
//
	public static void main(String[] args) {
		System.setProperty("java.library.path", "C:\\R-3.2.2\\library\\rJava\\jri\\x64");
		 try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (UnsupportedLookAndFeelException |ClassNotFoundException|InstantiationException|IllegalAccessException e) {
	       // handle exception
	    }
	    
		ToolController tc = new ToolController();
		new GuiController(tc);
		
	}

}

package util;

import domain.SessionHistory;
import domain.ToolController;
import gui2.MainFrame;
import javaGD.BNJavaGD;

import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import data.SessionHistoryMapper;

public class StartApplication2 {

  /**
   * Starts the application.
   * @param args arguments for main method
   */
  public static void main(String[] args) {
    //System.setProperty("java.library.path", "C:\\R-3.2.2\\library\\rJava\\jri\\x64");
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      UIManager.getLookAndFeelDefaults().put("TabbedPane:TabbedPaneTab.contentMargins", new Insets(20, 100, 0, 0));
    } catch (UnsupportedLookAndFeelException
        | ClassNotFoundException
        | InstantiationException
        | IllegalAccessException e) {
      // handle exception
      JOptionPane.showMessageDialog(null,  e.getMessage(),"Error with user interface manager", 
          JOptionPane.ERROR_MESSAGE);
    }
    
    ToolController tc;
    SessionHistory sh = SessionHistoryMapper.loadSessionHistory();
    
    if(sh != null){
    	tc = new ToolController(sh);
    }else{
    	tc = new ToolController();
    }
  
    
    //new MainFrame(tc);
    
    new gui3.MainFrame(tc);
    //f.setVisible(true);
    //new SaveThread(tc).start();
    
  }

}

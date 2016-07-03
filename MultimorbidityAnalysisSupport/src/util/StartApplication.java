package util;

import domain.ToolController;
import gui.ErrorDialog;
import gui.MainFrame;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * This class is the primary Thread of the application.
 * @author ABI team 37
 * @version 1.0
 */
public class StartApplication {
  
  private StartApplication() {

  }
  /**
   * Starts the application.
   * @param args arguments for main method
   */
  public static void main(String[] args) {

    setLook();
    
    ToolController tc = null;
    
    tc = new ToolController();

    if (tc != null) {
      new MainFrame(tc);
      new SaveThread(tc).start();
    }
  }
  
  private static void setLook() {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (UnsupportedLookAndFeelException
        | ClassNotFoundException
        | InstantiationException
        | IllegalAccessException e) {
      new ErrorDialog(null,"Error with user interface manager" );    
    }
  }
  
  
}

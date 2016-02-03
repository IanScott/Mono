package util;

import domain.ToolController;
import guiold.GuiController;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class StartApplication {

  /**
   * Starts the application.
   * @param args arguments for main method
   */
  public static void main(String[] args) {
    //System.setProperty("java.library.path", "C:\\R-3.2.2\\library\\rJava\\jri\\x64");
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (UnsupportedLookAndFeelException
        | ClassNotFoundException
        | InstantiationException
        | IllegalAccessException e) {
      // handle exception
      JOptionPane.showMessageDialog(null,  e.getMessage(),"Error with user interface manager", 
          JOptionPane.ERROR_MESSAGE);
    }

    ToolController tc = new ToolController();
    new GuiController(tc);
  }

}

package util;

import data.SessionHistoryMapper;
import domain.SessionHistory;
import domain.ToolController;

/**
 * Class represents the Thread which runs, polls for changes and saves them.
 * @author ABI team 37
 * @version 1.0
 *
 */
public class SaveThread extends Thread{
  private static final int POLLINGTIME = 5000;
  private SessionHistory sessionHistory;
  
  /**
   * Consturctor.
   * @param toolcontroller the toolcontroller to monitor for changes
   */
  public SaveThread(ToolController toolcontroller) {
    this.sessionHistory = toolcontroller.getDataTool().getSessionHistory();
  }
  
  @Override
  public void run() {
    
    while (true) {
      
      if (sessionHistory.isEditted()) {
        SessionHistoryMapper.saveSessionHistory(sessionHistory);
        sessionHistory.saved();
      }
      try {
        Thread.sleep(POLLINGTIME);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  
  }

}

package util;

import data.SessionHistoryMapper;
import domain.SessionHistory;
import domain.ToolController;

public class SaveThread extends Thread{
	private static final int POLLINGTIME = 5000;
	
	private SessionHistory sessionHistory;
	
	
	public SaveThread(ToolController toolcontroller){
		this.sessionHistory = toolcontroller.getSessionHistory();
	}
	
	public void run() {
		
		while(true){
			
			if(sessionHistory.isEditted()){
				SessionHistoryMapper.saveSessionHistory(sessionHistory);
				System.out.println("savedSession");
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

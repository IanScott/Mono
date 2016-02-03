package temp;

import org.rosuda.JRI.RMainLoopCallbacks;
import org.rosuda.JRI.Rengine;

class CallbackListener implements RMainLoopCallbacks {
	  
		public void rBusy(Rengine arg0, int arg1) { }
		public String rReadConsole(Rengine arg0, String arg1, int arg2) { return null; }
		public void rShowMessage(Rengine arg0, String arg1) { }
		public String rChooseFile(Rengine arg0, int arg1) { return null; }
		public void rFlushConsole(Rengine arg0) { }
		public void rSaveHistory(Rengine arg0, String arg1) { }
		public void rLoadHistory(Rengine arg0, String arg1) { }
		@Override
		public void rWriteConsole(Rengine arg0, String arg1, int arg2) {
			System.out.print(arg1);	
		}
	}


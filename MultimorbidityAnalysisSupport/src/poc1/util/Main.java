package poc1.util;

import poc1.domain.BNTool;
import poc1.gui.BNFrame;

public class Main {

	public static void main(String[] args) {
		BNTool bt = new BNTool();
		new BNFrame(bt);
		
	}

}

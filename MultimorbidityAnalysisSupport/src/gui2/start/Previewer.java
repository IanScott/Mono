package gui2.start;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;

public class Previewer {
	public static DefaultTableModel preview(File file, String sep, Boolean head){
		
		BufferedReader br = null;
		String line;
		
		String[] header = {};
		String[][] values = new String[1][1];
		int i = 0;
		
		try {
			br = new BufferedReader(new FileReader(file));
			boolean firstline = true;
			
			
			if(head && (line = br.readLine()) !=null){
				header = line.split(sep);
				if(firstline){
					values = new String[10][header.length];
					firstline = false;
				}
			}
			
			while ((line = br.readLine()) != null && i <10) {
				if(firstline){
					values = new String[10][line.split(sep).length];
					firstline = false;
					
					int j = values[0].length;
					String[] empty = new String[j];
					for(int u = 0; u<j; u++){
						empty[u] = "";
					}
					header = empty;
				}
			    // use comma as separator
				String[] val = line.split(sep);
				values[i] = val;
				i++;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		if(i<10){
			if(values[0] != null){
				int j = values[0].length;
				String[] empty = new String[j];
				for(int u = 0; u<j; u++){
					empty[u] = "";
				}
				while( i < 10){
					values[i] = empty;
					i++;
				}
			}
		}
		
		
		return new DefaultTableModel(values,header);	
		
		
	}
}

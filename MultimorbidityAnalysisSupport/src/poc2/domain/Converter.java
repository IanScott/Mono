package poc2.domain;

import java.util.Vector;

import org.rosuda.JRI.REXP;
import org.rosuda.JRI.RFactor;
import org.rosuda.JRI.RVector;

public class Converter {
	
	/**
	 * Method for converting a REXP object into a DataSet object
	 * @param data the REXP object to be converted
	 * @return a DataSet object
	 */
	public static DataSet toDataSet(REXP data){
		
		String[] columnames = {};
		String[][] values ={};
		
		
		if(data.getType() == 16){
			RVector rvec = data.asVector();
			
			//Get Columnnames
			@SuppressWarnings("unchecked")
			Vector<String> vnames= rvec.getNames();
			columnames = new String[vnames.size()];
			for(int i =0; i<vnames.size(); i++){
				columnames[i] = vnames.get(i);
			}
					
			//Get dimensions values
			int size = rvec.at(0).asFactor().size();
			values = new String[size][columnames.length];
			
			for(int j =0; j<rvec.size();j++){
				REXP rexp2 = rvec.at(j);
				//System.out.println(rexp2);
				if(true){
					RFactor rfac = rexp2.asFactor();
					for(int i = 0; i<rfac.size();i++){
						values[i][j] = rfac.at(i);
					}
				}
			}
			
		}
		
		
		return new DataSet(columnames,values);
	}
	
}

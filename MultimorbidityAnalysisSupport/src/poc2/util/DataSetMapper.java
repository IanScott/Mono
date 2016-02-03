package poc2.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import poc2.domain.DataSet;

public class DataSetMapper {
	
	public static void saveDataSet(DataSet dataset, String name){
		dataset.setName(name);
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(  
			new FileOutputStream(new File("TEMP/"+name+".ser")));
			oos.writeObject(dataset);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(oos !=null){
				oos.close();}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static DataSet loadDataSet(File file){
		FileInputStream fis;
		DataSet dataset = null;
		try {
			fis = new FileInputStream(file);
			ObjectInputStream oin = new ObjectInputStream(fis);
			dataset = (DataSet) oin.readObject();
			oin.close();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataset;
	}
}

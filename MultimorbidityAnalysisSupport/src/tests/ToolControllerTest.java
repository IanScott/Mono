package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.rosuda.JRI.REXP;
import util.MultimorbidityException;
import domain.ToolController;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class ToolControllerTest {
  
  private static ToolController tc;
  private boolean print = true;
  
  public void setPrint(boolean print) {
    this.print = print;
  }

  @BeforeClass
  public static void setUp() throws Exception {
    tc = new ToolController();
  }
  
  @Test
  public void testToolController() throws NoSuchMethodException, SecurityException, 
    IllegalAccessException, IllegalArgumentException, InvocationTargetException {

    if (print) {
      System.out.println("*** testToolController() ***");
    }
    //Check if Rengine object exists after method connectR()
    assertNotNull("No Rengine object found: test failed", tc.getRengine());
    
    //Check if arbitrary R function(s) can be called
    tc.getRengine().eval("x=c(1:10)");
    double mean = tc.getRengine().eval("mean(x)").asDouble();
    assertEquals(5.5, mean, 0.000001);
    
    //Check if required libraries are loaded
    String[] libraries = tc.getRengine().eval("(.packages())").asStringArray();
    if (print) {
      Arrays.sort(libraries);
      String lastlibrary = libraries[libraries.length - 1];
      System.out.print("The loaded libraries are: ");
      for (String library: libraries) {
        if (!lastlibrary.equals(library)) {
          System.out.print(library + ", ");
        } else {
          System.out.println(library + ".");
        }
      }
    }
    assertTrue(Arrays.asList(libraries).contains("bnlearn"));
    assertTrue(Arrays.asList(libraries).contains("JavaGD"));
    
    //Check if tools are added
    Object[] toolobjects = tc.getTools().keySet().toArray();
    String[] tools = new String[toolobjects.length];
    for (int i = 0; i < toolobjects.length; i++) {
      tools[i] = toolobjects[i].toString();
    }
    if (print) {
      String lasttool = tools[tools.length - 1];
      System.out.print("The initialized tools are: ");
      for (String tool: tools) {
        if (!lasttool.equals(tool)) {
          System.out.print(tool + ", ");
        } else {
          System.out.println(tool + ".");
        }
      }
    }
    assertTrue(Arrays.asList(tools).contains("BNTOOL"));
    assertTrue(Arrays.asList(tools).contains("COUNTTOOL"));
  }

  @Test
  public void testImportDataSet() throws MultimorbidityException {
    if (print) {
      System.out.println("*** testImportDataSet() ***");
    }
    //Import the file from resources folder
    String url = "resources/data1.csv";
    File file = new File(url);
    tc.importDataSet(file, ",", true);
    
    //Test the columnnames of the dataset
    String[] columns = tc.getActiveDataSet().getColumnnames();
    if (print) {
      String lastcolumn = columns[columns.length - 1];
      System.out.print("The columnnames of the dataset " + url + " are: ");
      for (String columnname: columns) {
        if (!lastcolumn.equals(columnname)) {
          System.out.print(columnname + ", ");
        } else {
          System.out.println(columnname + ".");
        }
      }
    }
    assertEquals("Auto_immune_disease", columns[0]);
    assertEquals("Age", columns[1]);
    assertEquals("Hypertension", columns[2]);
    assertEquals("Myocardial_infarction", columns[3]);
    
    //Test the length of the dataset
    int nrOfRows = tc.getActiveDataSet().getColumnValues(columns[0]).length;
    if (print) {
      System.out.println("The number of rows in dataset " + url + " is: " + nrOfRows);
    }
    assertEquals(30000, nrOfRows);
    
    //Test the factors of column 'Age'
    String[] factors = tc.getActiveDataSet().getFactors("Age");
    Arrays.sort(factors);
    String [] compare = {"N/A", "middleaged", "old", "young"};
    if (print) {
      String lastfactor = factors[factors.length - 1];
      System.out.print("The factors of column 'Age' are: ");
      for (String factor: factors) {
        if (!lastfactor.equals(factor)) {
          System.out.print(factor + ", ");
        } else {
          System.out.println(factor + ".");
        }
      }
    }
    assertArrayEquals(compare, factors);
  }

  @Test
  public void testSetValues() throws MultimorbidityException {
    if (print) {
      System.out.println("*** testSetValues() ***");
    }
    //Import the file from resources folder
    String url = "resources/data1.csv";
    //int nrOfColumns = tc.getActiveDataSet().getColumnnames().length;
    //int nrOfRows = tc.getActiveDataSet().getColumnValues(tc.getActiveDataSet().getColumnnames()[0]).length;
    File file = new File(url);
    tc.importDataSet(file, ",", true);
    
    //Test setNa for column 'Hypertension' to value 'check'
    //String[][] values = new String[nrOfColumns][nrOfRows];
    String[] values = tc.getActiveDataSet().getColumnValues("Hypertension");
    int naBefore = 0;
    int checkBefore = 0;
    for (int i = 0; i < values.length; i++) {
      if (values[i].equals("N/A")) {
        naBefore++;
      }
      if (values[i].equals("check")) {
        checkBefore++;
      }
    }
    tc.setNa("Hypertension", "check");
    //Gaat fout na setNa: eerst logging verder afwerken
    String [] valuesAfter = tc.getActiveDataSet().getColumnValues("Hypertension");
    int naAfter = 0;
    int checkAfter = 0;
    for (int i = 0; i < valuesAfter.length; i++) {
      if (valuesAfter[i].equals("N/A")) {
        naAfter++;
      }
      if (valuesAfter[i].equals("check")) {
        checkAfter++;
      }
    }

    if (print) {
      System.out.println("Number of 'N/A' values setNa: " + naBefore);
      System.out.println("Number of 'check' values setNa: " + checkBefore);
      System.out.println("Number of 'N/A' values setNa: " + naAfter);
      System.out.println("Number of 'check' values setNa: " + checkAfter);
    }
    
  }



  /*@Test
  public void testExportDataSet() {
    fail("Not yet implemented");
  }

  @Test
  public void testLoadSDataSet() {
    fail("Not yet implemented");
  }

  @Test
  public void testGetDataSetController() {
    fail("Not yet implemented");
  }

  @Test
  public void testGetDataSet() {
    fail("Not yet implemented");
  }

  @Test
  public void testExecuteToolString() {
    fail("Not yet implemented");
  }

  @Test
  public void testExecuteToolStringObject() {
    fail("Not yet implemented");
  }

  @Test
  public void testCleanData() {
    fail("Not yet implemented");
  }

  @Test
  public void testSetNa() {
    fail("Not yet implemented");
  }

  @Test
  public void testOmitNa() {
    fail("Not yet implemented");
  }

  @Test
  public void testNaToMean() {
    fail("Not yet implemented");
  }

  @Test
  public void testNaToMedian() {
    fail("Not yet implemented");
  }

  @Test
  public void testGetRengine() {
    fail("Not yet implemented");
  }*/

}

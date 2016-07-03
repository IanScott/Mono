package uat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ProcessBuilder.Redirect;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;

/**
 * Class used for Acceptance Testing. The class is NOT Finished and needs to be tested more.
 * The static exec() method is used to start a new Process.
 * Because the application needs System variable to be set a new
 * Process is needed.
 * @author ABI team 37
 * @version 1.0
 *
 */
public final class JavaProcess {
	
    private static InputStream inputStream;
    private static String path ="";
    private static String rhome = "";
    private static String library="";
    
    /**
     * Private Constructor
     */
    private JavaProcess() {} 
    
    @SuppressWarnings("rawtypes")
	public static int exec(Class klass) throws IOException,
                                               InterruptedException {
        
    	
    	getConfig();
    	setSystemLibrary();
    	String javaHome = System.getProperty("java.home");
        String javaBin = javaHome +
                File.separator + "bin" +
                File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        String className = klass.getCanonicalName();
        
        
        
        ProcessBuilder builder = new ProcessBuilder(
                javaBin, "-cp", classpath, className);
        
        builder.redirectErrorStream(true);
        builder.redirectOutput(Redirect.INHERIT);
        builder.redirectError(Redirect.INHERIT);
        Map<String,String> env = builder.environment();
        //String path = "C:\\R-3.2.3\\library\\rJava\\jri\\x64;C:\\R-3.2.3\\bin\\x64";
        env.put("Path", path);
        //String rhome = "C:\\R-3.2.3";
        env.put("R_HOME", rhome);
        
        Process process = builder.start();
        process.waitFor();
        return process.exitValue();
    }
    
    private static void getConfig(){
    	try {
			Properties prop = new Properties();
			String propFileName = "configuration/JRI.properties";
 
			//inputStream = JavaProcess.class.getClassLoader().getResourceAsStream(propFileName);
			File file = new File(propFileName);
			inputStream = new FileInputStream(file);
			//inputStream.close();
			
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
			inputStream.close();
			
			// get the property values
			
			String jri_dll = prop.getProperty("jri_dll");
			String r_dll = prop.getProperty("r_dll");
			
			rhome =prop.getProperty("r_install");
			library = jri_dll;
			path = jri_dll+";"+r_dll;
				
			
		} catch (Exception e) {
			
		}
    }
    
    private static void setSystemLibrary(){
  	  System.setProperty("java.library.path", library);
  	    Field fieldSysPath =null;
  		try {
  			fieldSysPath = ClassLoader.class.getDeclaredField( "sys_paths" );
  		} catch (NoSuchFieldException e2) {
  			e2.printStackTrace();
  		} catch (SecurityException e2) {
  			e2.printStackTrace();
  		}
  	    fieldSysPath.setAccessible( true );
  	    try {
  			fieldSysPath.set( null, null );
  		} catch (IllegalArgumentException e1) {
  			e1.printStackTrace();
  		} catch (IllegalAccessException e1) {
  			e1.printStackTrace();
  		}
    }
}
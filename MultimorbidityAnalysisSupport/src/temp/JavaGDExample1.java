package temp;

import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;
import org.rosuda.javaGD.JavaGD;

public class JavaGDExample1 {
	 
	public static void main(String[] args) {
	 Rengine re;
	 String[] dummyArgs = new String[1];
	 dummyArgs[0] = "--vanilla";
	 re = new Rengine(dummyArgs, false, new CallbackListener());
	 re.eval("library(JavaGD)");
	 //re.eval("install.packages('bnlearn')");
	 re.eval("library(bnlearn)");
	 
	 // This is the critical line: Here, we tell R that the JavaGD() device that
	 // it is supposed to draw to is implemented in the class MyJavaGD. If it were
	 // in a package (say, my.package), this should be set to
	 // my/package/MyJavaGD1.
	 //re.eval("Sys.putenv('JAVAGD_CLASS_NAME'='gui.MyJavaGD1')");
	 //re.eval("Sys.setenv('JAVAGD_CLASS_NAME'='guis/MyJavaGD1')");
	 //MyJavaGD1 jgd = new MyJavaGD1();
	 //re.eval("JavaGD()");
	 //jgd.gdOpen(250,250);
	 re.eval("MyData <- read.csv(file=\"D:/OU_ABI/Db/data1.csv\", header=TRUE, sep=\",\")");
	 re.eval("bn.gs <- gs(MyData)");
	 re.eval("bn.gs");
	 re.eval("plot(bn.gs)");
	 REXP rex = re.eval("bn.fit(bn.gs,MyData)");
	 
	 System.out.println(rex.toString());
	 
	 //re.eval("plot(c(1,5,3,8,5), type='l', col=2)");
	 re.end();
	 }
}

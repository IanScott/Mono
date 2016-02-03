package temp;

import org.rosuda.JRI.Rengine;
import org.rosuda.JRI.REXP;

public class HelloRWorld {
   Rengine rengine; // initialized in constructor or autowired

   public void helloRWorld() {
      rengine.eval(String.format("greeting%s'", "Hello R World"));
      REXP result = rengine.eval("greeting");
      System.out.println("Greeting R: "+result.asString());
   }
}

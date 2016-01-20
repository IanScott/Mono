package novadoc;

	import com.filenet.api.constants.RefreshMode;
	import com.filenet.api.core.Document;
	import com.filenet.api.core.Folder;
	import com.filenet.api.core.IndependentObject;
	import com.filenet.api.core.ReferentialContainmentRelationship;
	import com.filenet.api.engine.EventActionHandler;
	import com.filenet.api.events.FileEvent;
	import com.filenet.api.events.ObjectChangeEvent;
	import com.filenet.api.exception.EngineRuntimeException;
	import com.filenet.api.util.Id;


	public class FolderFileEventHandler implements EventActionHandler {

	   public void onEvent(ObjectChangeEvent event, Id subId) throws EngineRuntimeException {

	      
	     IndependentObject o = event.get_SourceObject();
	      if (o instanceof ReferentialContainmentRelationship) {

	         ReferentialContainmentRelationship rcr = (ReferentialContainmentRelationship) o;

	         IndependentObject head = rcr.get_Head();
	         if (head instanceof Document) {
	            Document doc = (Document) head;
	            Folder fol = (Folder) rcr.get_Tail();
	   
	            if (event instanceof FileEvent){
	               Folder value = doc.get_SecurityFolder();
	               if (value == null){
	                  
	                  
	                 doc.set_SecurityFolder(fol);
	                  doc.save(RefreshMode.REFRESH);
	               }
	            }
	         }
	      }
	   }
	
}

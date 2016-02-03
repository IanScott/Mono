package domain;

import java.util.LinkedList;

/**
 * Class representing a Queue with a maximum size.
 * If maximum size has been reached the oldest object in the Queue is deleted.
 * @author Ian van Nieuwkoop
 * @version 0.1
 * @param <E> the Class of object to be stored.
 */
public class SizedQueue<E> extends LinkedList<E>{
	
	private static final long serialVersionUID = 2914649548639509218L;
	private int limit;
	
	/**
	 * Constructor
	 * @param limit an int representing the maximum size.
	 */
    public SizedQueue(int limit) {
        this.limit = limit;
    }

    @Override
    public boolean add(E o) {
        super.add(o);
        while (size() > limit) { super.remove(); }
        return true;
    }
    
    /**
     * Getter that return the maximum size.
     * @return an int representing the maximum size.
     */
    public int limit(){
    	return limit;
    }
}

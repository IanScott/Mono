package domain;

import java.util.Stack;

/**
 * Class representing a Stack with a maximum size.
 * If maximum size has been reached the oldest object in the Stack is deleted.
 * @author Ian van Nieuwkoop
 * @version 0.1
 * @param <T> the Class of Object to be stored.
 */
public class SizedStack<T> extends Stack<T> {
    
	private static final long serialVersionUID = -2921138804956230643L;
	private int maxSize;
	
	/**
	 * Contructor
	 * @param size the maximum size of the Stack.
	 */
    public SizedStack(int size) {
        super();
        this.maxSize = size;
    }

    @Override
    public T push(T object) {
        //If the stack is too big, remove elements until it's the right size.
        while (this.size() >= maxSize) {
            this.remove(0);
        }
        return super.push((T) object);
    }
    
    /**
     * Getter that returns the maximum size of the Stack
     * @return an int representing the maximum size.
     */
    public int maxSize(){
    	return maxSize;
    }
}
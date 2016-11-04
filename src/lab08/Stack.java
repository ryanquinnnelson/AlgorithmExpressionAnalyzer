/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab08;

/**
 * Collection of generic objects that are inserted and removed according to the last-in, first-out principle.
 * @author ryan.quinn.nelson and michael.kleinsasser
 * @param <E> represents generic object type
 */
public interface Stack<E> 
{
    /**
     * Returns the number of objects in the stack.
     * @return number of objects in the stack
     */
    public abstract int size();
    
    /**
     * Checks whether stack has any objects in it.
     * @return true if stack has zero objects, false otherwise
     */
    public abstract boolean isEmpty();
    
    /**
     * Adds an object to the top of the stack.
     * @param e generic object to add to the stack
     */
    public abstract void push(E e);
    
    /**
     * Returns but does not remove the object at the top of the stack.
     * @return generic object at the top of the stack (null if stack is empty)
     */
    public abstract E top();
    
    /**
     * Removes and returns the object at the top of the stack.
     * @return generic object at the top of the stack (null if stack is empty)
     */
    public abstract E pop();
}

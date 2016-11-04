/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab08;

/**
 * Implements generic Stack interface using singly linked list and adapter pattern.
 * Class constructs hidden SinglyLinkedList object and adapts its methods to interface.
 * @author ryan.quinn.nelson and michael.kleinsasser
 * @param <B> represents generic type of object
 */
public class LinkedStack<B> implements Stack<B>
{
    private SinglyLinkedList<B> list;
    
    /**
     * Constructs LinkedStack object with empty singly linked list.
     */
    public LinkedStack() 
    {
        list = new SinglyLinkedList<B>();
    }
    
    /**
     * Returns the number of objects in the stack.
     * @return number of objects in the stack
     */
    @Override
    public int size() 
    {
        return list.size();
    }

    /**
     * Checks whether stack has any objects in it.
     * @return true if stack has zero objects, false otherwise
     */
    @Override
    public boolean isEmpty() 
    {
        return list.isEmpty();
    }

    /**
     * Adds an object to the top of the stack.
     * @param e generic object to add to the stack
     */
    @Override
    public void push(B e) 
    {
        list.addFirst(e);
    }

    /**
     * Returns but does not remove the object at the top of the stack.
     * @return generic object at the top of the stack (null if stack is empty)
     * 
     */
    @Override
    public B top() 
    {
        return list.first();
    }

    /**
     * Removes and returns the object at the top of the stack.
     * @return generic object at the top of the stack (null if stack is empty)
     * 
     */
    @Override
    public B pop() 
    {
        return list.removeFirst();
    } 
    
    /**
     * 
     * @return String representation of LinkedStack object
     */
    @Override
    public String toString()
    {
        String name = this.getClass().getSimpleName() + "<";
        String classImplementation = (this.isEmpty() ? "" : this.top().getClass().getSimpleName());
        
        return name + classImplementation + "> Size: " + size() + ", Top Index: " + top();
    }
    
    /**
     * Tests whether two LinkedStack objects are equal.
     * @param o object to compare with this
     * @return true if objects are equal, false otherwise
     */
    public boolean equals(Object o)
    {
        if(!(o instanceof LinkedStack))
        {
            return false;
        }
        else
        {
            LinkedStack other = (LinkedStack<B>) o;
            
            //check whether stacks have same number of objects in them
            if(this.size() != other.size())
            {
                return false;
            }
            
            return this.top().equals(other.top());
        }
    }
}

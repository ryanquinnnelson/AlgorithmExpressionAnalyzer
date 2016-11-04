package lab08;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * An abstract base class providing some functionality of the BinaryTree interface.
 * 
 * @author ryan.quinn.nelson and michael.kleinsasser
 * @param <E> generic type to be implemented
 */
public abstract class AbstractBinaryTree<E> extends AbstractTree<E> implements BinaryTree<E>
{
    
     //--------------------nested ElementIterator class ------------------ //
    /**
     * Adapts iteration produced by positions() to return elements.
     */
    private class ElementIterator implements Iterator<E>
    {
        
        Iterator<Position<E>> iter = positions().iterator();        //constructs Position iterator

                
        /**
         * Checks whether Iterator has another element.
         * @return true if Iterator has another element, false otherwise
         */
        @Override
        public boolean hasNext() 
        {
            return iter.hasNext();
        }

        /**
         * Returns next element in Iterator since most recent hasNext() method call.
         * @return next element in Iterator since most recent hasNext() method call
         */
        @Override
        public E next() {
            return iter.next().getElement();
        }
        /**
         * Removes element that was returned from next() method.
         */
        @Override
        public void remove()
        {
            iter.remove();
        }
        
    }//--------------------end of nested ElementIterator class ------------------ //
    
    //accessor methods
    /**
     * Returns the number of children of given Position.
     * @param p Position to check
     * @return the number of children of given Position
     * @throws IllegalArgumentException if Position is invalid
     */
    @Override
    public int numChildren(Position<E> p) throws IllegalArgumentException 
    {
        int counter = 0;
        
        if(left(p) != null) //p has a left child
        {
            counter++;
        }
        
        if(right(p) != null) //p has a right child
        {
            counter++;
        }
        
        return counter;
    }
    
    
    /**
     * Returns the Position of p's sibling (or null if no sibling exists).
     * @param p Position to check
     * @return the Position of p's sibling (or null if no sibling exists)
     * @throws IllegalArgumentException if Position is invalid
     */
    @Override
    public Position<E> sibling(Position<E> p) throws IllegalArgumentException
    {
        Position<E> parent = parent(p); //gets parent of position
        
        if(parent == null)          //p is root
        {
            return null;
        }
        
        if(p == left(parent))       //p is the left child of parent
        {
            return right(parent);   //return the matching child of the same parent
        }
        else                        //p is the right child of parent
        {
            return left(parent);    //return the matching child of the same parent
        }
        
    }
    

    //additional methods
    
    
    /**
     * 
     * Returns an iterator for all elements in the tree.
     * Ensures tree itself is iterable.
     * @return an iterator for all elements in the tree
     */
    
    @Override
    public Iterator<E> iterator()
    {
        return new ElementIterator();
    }
    
    
    
    // ----------------------------Traversal Algorithms --------------------------- //
    /**
     * Returns an iterable collection of the Positions representing p's children.
     * @param p Position to check
     * @return an iterable collection of the Positions representing p's children.
     * @throws IllegalArgumentException if Position is invalid
     */
    @Override
    public Iterable<Position<E>> children(Position<E> p) throws IllegalArgumentException 
    {
        List<Position<E>> snapshot = new ArrayList<>(2);    //constructs empty List that is iterable with capacity of 2
        
        if(left(p) != null) //p has a left child
        {
            snapshot.add(left(p)); //add it to the list
        }
        
        if(right(p) != null) //p has a right child
        {
            snapshot.add(right(p)); //add it to the list
        }
        
        return snapshot; //return the List
    }
    
    /**
     * Adds positions of the subtree rooted at given Position to the given iterable collection.
     * @param p Position to check
     * @param snapshot represents an iterable collection of positions of subtree
     */
    private void inorderSubtree(Position<E> p, List<Position<E>> snapshot)
    {
        if(left(p) != null) //if p has a left child
        {
            inorderSubtree(left(p), snapshot);  //recursively call method on left child
        }
        snapshot.add(p);    //adds p to List during second visit
        
        if(right(p) != null)    //if p has a right child
        {
            inorderSubtree(right(p), snapshot); //recursively call method on right child
        }
    }
    
    /**
     * Returns an iterable collection of positions of the tree, reported in order.
     * @return an iterable collection of positions of the tree, reported in in order
     */
    public Iterable<Position<E>> inorder()
    {
        List<Position<E>> snapshot = new ArrayList<>();
        
        if(!isEmpty())  //if Tree contains elements
        {
            inorderSubtree(root(), snapshot);
        }
        return snapshot;
    }
    
    
    
    /**
     * Performs Euler tour traversal of a subtree rooted at position p.
     * @param T Tree to traverse
     * @param p root of the Tree
     */
    public void eulerTourBinary(BinaryTree<E> T, Position<E> p)
    {
        if(!isEmpty())  //if Tree contains elements
        {
            eulerTourBinarySubtree(T, p);
        }
    }
    
    
    /**
     * Prints parenthesized arithmetic expression of subtree using Euler tour traversal.
     * @param T Tree to traverse
     * @param p root of the Tree
     */
    private void eulerTourBinarySubtree(BinaryTree<E> T, Position<E> p)
    {

        //perform pre-visit action for position p
        if(T.isInternal(p))                                                     //do we need T. ??
        {
            System.out.print("( ");    //if p is internal, part of an expression and needs opening paren
        }
        
        if(T.left(p) != null)//p has left child
        {
            eulerTourBinarySubtree(T, T.left(p));  //recursive call to method
        }
        
        //perform in-visit for position p
        System.out.print(p.getElement() + " "); 
        
        if(T.right(p) != null)//p has right child
        {
            eulerTourBinarySubtree(T, T.right(p));  //recursive call to method
        }
                     
        //perform post-visit action for position p
        if(T.isInternal(p))                                                     //do we need T. ??
        {
            System.out.print(") "); //if p is internal, part of an expression and needs closing paren
        }
        
    }
    
    
    
    
    
    
    /**
     * Returns an iterable Collection of all Positions of the tree.
     * @return an iterable Collection of all Positions of the tree.
     */
    @Override
    public Iterable<Position<E>> positions(){
        return inorder();
    }
    
    
    
    
    
    
    // ------------------- unimplemented methods ---------------------//
    //abstract accessor methods
    @Override
    public abstract Position<E> root();

    @Override
    public abstract Position<E> parent(Position<E> p) throws IllegalArgumentException;

    @Override
    public abstract int size();

    @Override
    public abstract Position<E> left(Position<E> p) throws IllegalArgumentException;
    
    @Override
    public abstract Position<E> right(Position<E> p) throws IllegalArgumentException;
       
}

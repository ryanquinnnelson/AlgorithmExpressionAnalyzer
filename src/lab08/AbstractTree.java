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
 * An abstract base class providing some functionality of the Tree interface.
 * @author ryan.quinn.nelson and michael.kleinsasser
 * @param <E> generic type to be implemented
 */
public abstract class AbstractTree<E> implements Tree<E>
{    
    //query methods
    /**
     * Tests whether tree contains any Positions (and therefore elements).
     * @return true if tree doesn't contain any Positions, false otherwise
     */
    @Override
    public boolean isEmpty() 
    {
        return size() == 0;
    }
    
    /**
     * Tests whether given Position has at least one child.
     * @param p Position to check
     * @return true if given Position has at least one child, false otherwise
     * @throws IllegalArgumentException if Position isn't valid 
     */
    @Override
    public boolean isInternal(Position<E> p) throws IllegalArgumentException 
    {
        return numChildren(p) > 0;      //Position has at least one child
    }

    /**
     * Tests whether given Position has any children.
     * @param p Position to check
     * @return true if given Position does not have children, false otherwise
     * @throws IllegalArgumentException if Position isn't valid  
     */
    @Override
    public boolean isExternal(Position<E> p) throws IllegalArgumentException 
    {
        return numChildren(p) == 0;      
    }

    /**
     * Tests whether given Position is the root of the tree.
     * @param p Position to check
     * @return true if given Position is the root, false otherwise
     * @throws IllegalArgumentException if Position isn't valid 
     */
    @Override
    public boolean isRoot(Position<E> p) throws IllegalArgumentException 
    {
        return p == root(); //p has same reference as root
    }
    
    
    //additional methods
    /**
     * Returns the number of levels separating given Position from the root.
     * @param p Position to check
     * @return the number of levels separating given Position from the root
     */
    public int depth(Position<E> p)
    {
        if(isRoot(p)) //base case - p is the root
        {
            return 0;
        }
        else //recursive case - p is not the root of the tree and at least 1 level separates p from root
        {
            return 1 + depth(parent(p));    //call the same method on the parent of p
        }
    }
    
    /**
     * Returns the height of the sub-tree rooted at given Position.
     * 
     * @param p Position to set as root
     * @return the height of the sub-tree rooted at given Position
     */
    public int height(Position<E> p)
    {
        int height = 0;     //base case - if p is a leaf, height of p is 0
        
        for(Position<E> child : children(p))    //iterates through all children of p
        {
            //recursive case - if p is a root, the height of p is one more than the maximum of the heights of p's children
            height = Math.max(height, 1 + height(child)); 
        }
        
        return height;
    }
    
    // ----------------------------Traversal Algorithms --------------------------- //
    /**
     * Adds positions of the subtree rooted at given Position to the given iterable collection.
     * @param p Position to check
     * @param snapshot represents an iterable collection of positions of subtree
     */
    private void preorderSubtree(Position<E> p, List<Position<E>> snapshot)
    {
        snapshot.add(p);        //add Position to iterator
        
        for(Position<E> c : children(p))        //traverse all children of p
        {   
            preorderSubtree(c, snapshot);   //recursively call method to add each of their Positions to iterator
        }
    }
    
    /**
     * Returns an iterable collection of positions of the tree, reported in pre-order.
     * @return an iterable collection of positions of the tree, reported in pre-order
     */
    public Iterable<Position<E>> preorder()
    {
        List<Position<E>> snapshot = new ArrayList<>();     //constructs empty iterable collection
        
        if(!isEmpty())  //if tree has elements
        {
            preorderSubtree(root(), snapshot);  //adds all Positions in list to iterable collection
        }
        return snapshot;
    }
    
    
    /**
     * Adds positions of the subtree rooted at given Position to the given iterable collection.
     * @param p Position to check
     * @param snapshot represents an iterable collection of positions of subtree 
     */
    private void postorderSubtree(Position<E> p, List<Position<E>> snapshot)
    {
        for(Position<E> c : children(p))        //traverse all children of p
        {   
            postorderSubtree(c, snapshot);   //recursively call method to add each of their Positions to iterator
        }
        
        snapshot.add(p);        //add Position to iterator
    }
    
    /**
     * Returns an iterable collection of positions of the tree, reported in postorder.
     * @return an iterable collection of positions of the tree, reported in postorder
     */
    public Iterable<Position<E>> postorder()
    {
        List<Position<E>> snapshot = new ArrayList<>();     //constructs empty iterable collection
        
        if(!isEmpty())  //if tree has elements
        {
            postorderSubtree(root(), snapshot);  //adds all Positions in list to iterable collection
        }
        return snapshot;
    }
    
    /**
     * Returns an iterable collection of positions of the tree, reported in breadth-first order.
     * @return an iterable collection of positions of the tree, reported in breadth-first order
     */
    public Iterable<Position<E>> breadthFirst()
    {
        List<Position<E>> snapshot = new ArrayList<>(); //construct empty iterable list object
        
        if(!isEmpty())  //tree contains elements
        {
            Queue<Position<E>> level = new LinkedQueue<>(); //construct empty queue
            level.enqueue(root());  //add root to queue
            
            while(!level.isEmpty()) //queue contains elements
            {
                Position<E> p = level.dequeue();    //remove one element from front of queue
                snapshot.add(p);        //add that element to iterable list object
                        
                for(Position<E> c : children(p))    //add children of root to queue
                {
                    level.enqueue(c);
                }
            }
        }
        return snapshot;
    }
    
    /**
     * Prints all Positions of the tree in indented pre-order.
     * @param <E> generic type to be implemented
     * @param T Tree to traverse    
     * @param p Position to start at
     * @param d number of spaces to indent at this level
     */
    public static <E> void printPreorderIndent(Tree<E> T, Position<E> p, int d)
    {
        System.out.println(spaces(d, new StringBuilder()) + p.getElement());    //prints result of spaces() method, then prints element stored at p
        
        for(Position<E> c : T.children(p))  //iterates through all p's children
        {
            printPreorderIndent(T, c, d+1); //recursively calls method on them, adding 1 space each time
        }
    }
    
    
    
    /**
     * Returns a String of n spaces.
     * @param n number of spaces to add to String
     * @param sb represents efficient way to build String
     * @return String of n spaces
     */
    private static String spaces(int n, StringBuilder sb)
    {
        if(n == 0) return sb.toString(); //Base case, append nothing
        else
        {
            return sb.append(" ").toString() + spaces(n - 1, sb); //append one space every level down tree
        }
    }
    
    /**
     * Prints out all positions of tree in fully parenthesized pre-order.
     * @param <E> generic type to be implemented
     * @param T tree to be traversed
     * @param p node of tree to start at
     */
    public static <E> void preorderParenthesize(Tree<E> T, Position<E> p)
    {
        System.out.print(p.getElement()); //print element
        if(T.isInternal(p)){ //if internal
            boolean firstTime = true; //set first traversal to true
            for(Position<E> c : T.children(p)) //for each child
            {
                System.out.print((firstTime ? "(" : ","));//print parenthese or comma
                firstTime = false;//set firstTime false
                preorderParenthesize(T, c);//recursive call
            }
            System.out.print(")");//closing parenthese
        }
        
    }
    
  
    
    // ------------------- unimplemented methods ---------------------//
    //abstract accessor methods
    @Override
    public abstract int size();
    
    @Override
    public abstract int numChildren(Position<E> p) throws IllegalArgumentException;

    @Override
    public abstract Position<E> root();

    @Override
    public abstract Position<E> parent(Position<E> p) throws IllegalArgumentException;

    
    //abstract additional methods
    @Override
    public abstract Iterable<Position<E>> children(Position<E> p) throws IllegalArgumentException;

    @Override
    public abstract Iterable<Position<E>> positions();
    
    @Override
    public abstract Iterator<E> iterator();                                          
}

package lab08;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Iterator;

/**
 * An interface for a tree data structure where nodes can have an arbitrary number of children.
 * @author ryan.quinn.nelson and michael.kleinsasser
 * @param <E> generic type to be implemented
 */
public interface Tree<E> extends Iterable<E>
{
    //accessor methods
    /**
     * Returns the Position of the root of the tree.
     * @return the Position of the root of the tree
     */
    Position<E> root();
    
    /**
     * Returns the Position of the parent of given Position.
     * @param p Position to check
     * @return the Position of the parent of given Position (or null if p is the root). 
     * @throws IllegalArgumentException if Position isn't valid
     */
    Position<E> parent(Position<E> p) throws IllegalArgumentException;
    
    /**
     * Returns the number of children of given Position.
     * @param p Position to check
     * @return the number of children of given Position
     * @throws IllegalArgumentException if Position isn't valid
     */
    int numChildren(Position<E> p) throws IllegalArgumentException;

    /**
     * Returns the number of positions (and therefore elements) that are contained in tree.
     * @return the number of positions (and therefore elements) that are contained in tree
     */
    int size();
    
    
    //query methods
    /**
     * Tests whether given Position has at least one child.
     * @param p Position to check
     * @return true if given Position has at least one child, false otherwise
     * @throws IllegalArgumentException if Position isn't valid 
     */
    boolean isInternal(Position<E> p) throws IllegalArgumentException;
    
    /**
     * Tests whether given Position has any children.
     * @param p Position to check
     * @return true if given Position does not have children, false otherwise
     * @throws IllegalArgumentException if Position isn't valid  
     */
    boolean isExternal(Position<E> p) throws IllegalArgumentException;
    
    /**
     * Tests whether given Position is the root of the tree.
     * @param p Position to check
     * @return true if given Position is the root, false otherwise
     * @throws IllegalArgumentException if Position isn't valid 
     */
    boolean isRoot(Position<E> p) throws IllegalArgumentException;
    
    /**
     * Tests whether tree contains any Positions (and therefore elements).
     * @return true if tree doesn't contain any Positions, false otherwise
     */
    boolean isEmpty();
    
    
    
    //additional methods
    /**
     * Returns an iterable Collection containing the children of given Position.
     * @param p Position to check
     * @return an iterable Collection containing the children of given Position
     * @throws IllegalArgumentException if Position isn't valid
     */
    Iterable<Position<E>> children(Position<E> p) throws IllegalArgumentException;
    
    /**
     * Returns an iterable Collection of all Positions of the tree.
     * @return an iterable Collection of all Positions of the tree.
     */
    Iterable<Position<E>> positions();  
    
    /**
     * Returns an iterator for all elements in the tree.
     * Ensures tree itself is iterable.
     * @return an iterator for all elements in the tree
     */
    @Override
    Iterator<E> iterator();                                                      
}

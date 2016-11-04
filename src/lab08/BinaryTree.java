package lab08;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * An interface for a binary tree in which each node has at most two children.
 * @author ryan.quinn.nelson and michael.kleinsasser
 * @param <E> generic type to be implemented
 */
public interface BinaryTree<E> extends Tree<E>
{
    //accessor methods
    /**
     * Returns the Position of p's left child (or null if no child exists).
     * @param p Position to check
     * @return the Position of p's left child (or null if no child exists).
     * @throws IllegalArgumentException if Position is invalid
     */
    Position<E> left(Position<E> p) throws IllegalArgumentException;
    
    /**
     * Returns the Position of p's right child (or null if no child exists).
     * @param p Position to check
     * @return the Position of p's right child (or null if no child exists).
     * @throws IllegalArgumentException if Position is invalid
     */
    Position<E> right(Position<E> p) throws IllegalArgumentException;
    
    
    /**
     * Returns the Position of p's sibling (or null if p doesn't have a sibling).
     * @param p Position to check
     * @return the Position of p's sibling (or null if p doesn't have a sibling)
     * @throws IllegalArgumentException if Position is invalid
     */
    Position<E> sibling(Position<E> p) throws IllegalArgumentException;
}

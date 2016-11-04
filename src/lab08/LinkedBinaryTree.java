package lab08;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import lab08.AbstractBinaryTree;
import java.util.Iterator;

/**
 * Concrete implementation of a binary tree using a node-based, linked structure.
 * @author ryan.quinn.nelson and michael.kleinsasser
 * @param <E> generic type to be implemented
 */
public class LinkedBinaryTree<E> extends AbstractBinaryTree<E>
{
    //--------------------nested Node class ------------------ //
    /**
     * Implements Position interface and represents a node in the tree.
     * @param <E> generic type to be implemented 
     */
    private static class Node<E> implements Position<E>
    {
        private E element;          //element stored at this node
        private Node<E> parent;     //parent of this node
        private Node<E> left;       //left child of this node
        private Node<E> right;      //right child of this node

        /**
         * Constructs a node with given element and neighbors.
         * @param element element stored at this node
         * @param parent parent of this node
         * @param left left child of this node
         * @param right right child of this node
         */
        public Node(E element, Node<E> parent, Node<E> left, Node<E> right) {
            this.element = element;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        //accessor methods
        /**
         * Returns the parent of this node (null if parent doesn't exist).
         * @return the parent of this node (null if parent doesn't exist).
         */
        public Node<E> getParent() {
            return parent;
        }

        /**
         * Returns the left child of this node (null if child doesn't exist).
         * @return the left child of this node (null if child doesn't exist). 
         */
        public Node<E> getLeft() {
            return left;
        }
        
        /**
         * Returns the right child of this node (null if child doesn't exist).
         * @return the right child of this node (null if child doesn't exist).  
         */
        public Node<E> getRight() {
            return right;
        }
        
        /**
         * Returns the element stored at this node.
         * @return the element stored at this node.
         * @throws IllegalStateException if position is no longer valid
         */
        @Override
        public E getElement() throws IllegalStateException 
        {
            return element;
        }
        
        
        //update methods
        /**
         * Assigns the parent of this node.
         * @param parent the parent of this node.
         */
        public void setParent(Node<E> parent) {
            this.parent = parent;
        }

        /**
         * Assigns the left child of this node.
         * @param left the left child of this node.
         */
        public void setLeft(Node<E> left) {
            this.left = left;
        }

        /**
         * Assigns the right child of this node.
         * @param right the right child of this node.
         */
        public void setRight(Node<E> right) {
            this.right = right;
        }
                       
        /**
         * Assigns given element to this node.
         * @param e element to store at this node
         */
        public void setElement(E e)
        {
            element = e;
        }
        
        /**
         * Returns a String representation of Node object.
         * @return a String representation of Node object
         */
        @Override
        public String toString()
        {
            return element.toString();
        }
        
        /**
         * Tests whether this is equal to given object.
         * @param o object to compare with this
         * @return true if objects are equal, false otherwise
         */
        @Override
        public boolean equals(Object o)
        {
            if(!(o instanceof Node))
            {
                return false;
            }
            else
            {
                Node<E> other = (Node<E>) o;
                
                boolean b1 = this.element.equals(other.element);
                boolean b2 = this.parent == other.parent;
                boolean b3 = this.left == other.left;
                boolean b4 = this.right == other.right;
                
                return b1 && b2 && b3 && b4;
            }
        }
        
    }//--------------------end of nested Node class ------------------ //
    
    protected Node<E> root = null;      //represents the root of this tree
    private int size = 0;               //number of Positions (and therefore elements) this tree contains
    
    /**
     * Constructs empty tree.
     */
    public LinkedBinaryTree()
    {
        //empty on purpose
    }
    
    // ----------------------------Nonpublic Utility Methods --------------------------- //
    /**
     * Factory function to create a new node storing given element with given neighbors.
     * @param e element to store at this node
     * @param parent parent of this node
     * @param leftChild left child of this node
     * @param rightChild right child of this node
     * @return Node object
     */
    protected Node<E> createNode(E e, Node<E> parent, Node<E> leftChild, Node<E> rightChild)
    {
        return new Node<>(e, parent, leftChild, rightChild);
    }
    
    /**
     * Tests whether given Position is a Node object and returns Node if it is valid.
     * @param p Position to return as a Node
     * @return Node object representing given Position
     * @throws IllegalArgumentException if given Position is not a valid type 
     * @throws IllegalArgumentException if given Position is no longer in tree
     */
    protected Node<E> validate(Position<E> p) throws IllegalArgumentException
    {
        if(!(p instanceof Node))    
        {
            throw new IllegalArgumentException("not valid position type");
        }
        else
        {
            Node<E> node = (Node<E>) p;     //narrowing cast
            
            if(node.getParent() == node)    //convention for a defunct node
            {
                throw new IllegalArgumentException("p is no longer in tree");
            }
            
            return node;
        }
    }
    
    
    // ---------------------------- Accessor Methods --------------------------- //
    /**
     * Returns the number of positions (and therefore elements) that are contained in tree.
     * @return the number of positions (and therefore elements) that are contained in tree
     */
    @Override
    public int size()
    {
        return size;
    }
    
    /**
     * Returns the Position of the root of the tree.
     * @return the Position of the root of the tree
     */
    @Override
    public Position<E> root()
    {
        return root;
    }
    
    /**
     * Returns the Position of the parent of given Position.
     * @param p Position to check
     * @return the Position of the parent of given Position (or null if p is the root). 
     * @throws IllegalArgumentException if Position isn't valid 
     */
    @Override
    public Position<E> parent(Position<E> p) throws IllegalArgumentException
    {
        Node<E> node = validate(p);
        
        return node.getParent();
    }
    
    /**
     * Returns the Position of p's left child (or null if no child exists).
     * @param p Position to check
     * @return the Position of p's left child (or null if no child exists).
     * @throws IllegalArgumentException if Position is invalid
     */
    @Override
    public Position<E> left(Position<E> p) throws IllegalArgumentException 
    {
        Node<E> node = validate(p);
        
        return node.getLeft();
    }

    /**
     * Returns the Position of p's right child (or null if no child exists).
     * @param p Position to check
     * @return the Position of p's right child (or null if no child exists).
     * @throws IllegalArgumentException if Position is invalid 
     */
    @Override
    public Position<E> right(Position<E> p) throws IllegalArgumentException 
    {
        Node<E> node = validate(p);
        
        return node.getRight();
    }
    
    
    
    
    // ---------------------------- Update Methods --------------------------- //
    /**
     * Places given element at the root of an empty tree and returns its new Position.
     * @param e element to store at root
     * @return Position of root
     * @throws IllegalStateException if tree is not empty
     */
    public Position<E> addRoot(E e) throws IllegalStateException
    {
        if(!isEmpty())  //tree already has a root
        {
            throw new IllegalStateException("tree is not empty");
        }
        
        root = createNode(e, null, null, null); //constructs Node object
        
        size = 1;       //reset size
        
        return root;
    }
    
    /**
     * Creates a new left child of given Position that store given element, and returns its Position.
     * @param p Position to create a new left child for
     * @param e element to store at given Position
     * @return Position of new left child of given Position
     * @throws IllegalArgumentException if given Position already has a left child
     */
    public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException
    {
        Node<E> parent = validate(p);
        
        if( parent.getLeft() != null) //parent already has a left child
        {
            throw new IllegalArgumentException("p already has left child");
        }
        
        Node<E> child = createNode(e, parent, null, null);
        
        parent.setLeft(child);  //links parent with left child
        size++;
        
        return child;
    }
    
    /**
     * Creates a new right child of given Position that store given element, and returns its Position.
     * @param p Position to create a new right child for
     * @param e element to store at given Position
     * @return Position of new right child of given Position
     * @throws IllegalArgumentException if given Position already has a right child
     */
    public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException
    {
        Node<E> parent = validate(p);
        
        if( parent.getRight() != null) //parent already has a right child
        {
            throw new IllegalArgumentException("p already has right child");
        }
        
        Node<E> child = createNode(e, parent, null, null);
        
        parent.setRight(child); //links parent with right child
        size++;
        
        return child;
    }
    
    /**
     * Replaces the element at given Position with given element, returns replaced element.
     * @param p Position at which to place new element
     * @param e element to store at given Position
     * @return element replaced at given Position
     * @throws IllegalArgumentException if Position is not valid
     */
    public E set(Position<E> p, E e) throws IllegalArgumentException
    {
        Node<E> node = validate(p);
        
        E temp = node.getElement(); //temporarily store
        
        node.setElement(e); //store new element
        
        return temp;
    }
    
    /**
     * Attaches given trees t1 and t2 as left and right sub-trees of external p, respectively.
     * @param p Position to attach to
     * @param t1 represents the left sub-tree of p
     * @param t2 represents the right sub-tree of p
     * @throws IllegalArgumentException if p is not external
     */
    public void attach(Position<E> p, LinkedBinaryTree<E> t1, LinkedBinaryTree<E> t2) throws IllegalArgumentException
    {
        Node<E> node = validate(p);
        
        if(isInternal(p)) //p is not external
        {
            throw new IllegalArgumentException("p must be a leaf");
        }
        
        size += t1.size() + t2.size();      //combines number of Positions of t1 and t2 with this tree
        
        if(!t1.isEmpty())   //t1 contains nodes
        {
            t1.root.setParent(node);    //link the root of t1 to node
            
            node.setLeft(t1.root);      //assign t1 as left child of node
            
            t1.root = null;     //assist garbage collection                     //??why??
            t1.size = 0;        //reset count   
        }
        
        if(!t2.isEmpty())       //t2 contains nodes
        {
            t2.root.setParent(node);    //link the root of t2 to node
            
            node.setRight(t2.root);     //assign t2 as the right child of node
            
            t2.root = null;
            t2.size = 0;
        }    
    }
    
    /**
     * Removes the node at given Position, replaces it with its child, and returns the element stored at node that was removed.
     * @param p Position to remove from the tree
     * @return element stored at Position that was removed
     * @throws IllegalArgumentException if given Position has two children
     */
    public E remove(Position<E> p) throws IllegalArgumentException
    {
        Node<E> node = validate(p);
        
        if(numChildren(p) == 2) //p has 2 children and JVM can't determine which to link to p's parent
        {
            throw new IllegalArgumentException("p has two children");
            
        }

        Node<E> child;
        
        if(node.getLeft() != null)  //p has a left child
        {
            child = node.getLeft();
        }
        else        // p has a right child
        {
            child = node.getRight();
        }
        
        if(child != null)   //p has a child to replace p in tree
        {
            child.setParent(node.getParent());  //links p's parent to child
        }
        
        if(node == root) //p is the root
        {
            root = child;   //child is now the root; if node was root, child is null
        }
        else    //p is not the root and must be linked to parent as either left or right child
        {
            Node<E> parent = node.getParent();  //gets parent of p
            
            if(node == parent.getLeft())    //p was left child of p's parent
            {
                parent.setLeft(child);  //set child as left child of p's parent
            }
            else    //p was right child of p's parent
            {
                parent.setRight(child); //set child as right child of p's parent
            }
        }
        
        size--;
        
        E removed = node.getElement();  //store to return
        
        node.setElement(null);  //assist garbage collection
        node.setLeft(null);
        node.setRight(null);
        node.setParent(node);   //convention for defunct node that is no longer in tree
        
        return removed;
    }
    
    
    
    
    
    // ---------------------------- Additional Methods --------------------------- //
    
    
    /**
     * Returns a String representation of LinkedBinaryTree object.
     * @return a String representation of LinkedBinaryTree object
     */
    @Override
    public String toString()
    {
        if(isEmpty())
        {
            return "Empty Tree.";
        }
        
        StringBuilder answer = new StringBuilder();

        //gets the Simple name of the class that implements ArrayStack
        String implementedClass = root().getClass().getSimpleName();
        
        //creates standard header for class toString()
        String header = this.getClass().getSimpleName() + "<" + root().getElement().getClass().getSimpleName() + ">: {";
        
        answer.append(header);
        
        
        Iterator<E> iter = iterator();
        
        while(iter.hasNext())
        {
            answer.append(iter.next().toString());
            
            if(iter.hasNext() == true)
            {
                answer.append(", ");
            }
        }
        
        answer.append("}");
        return answer.toString();
    }
    
    /**
     * Tests whether this is equal to given object.
     * @param o object to compare with this
     * @return true if objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof LinkedBinaryTree))
        {
            return false;
        }
        else
        {
            LinkedBinaryTree<E> other = (LinkedBinaryTree<E>) o;
            
            //check whether trees have same number of elements in them
            if(this.size() != other.size())
            {
                return false;
            }
            
            Iterator<E> iter = iterator();
            Iterator<E> otherIter = other.iterator();
            
            while(iter.hasNext())
            {                              
                if(!iter.next().equals(otherIter.next()))
                {
                    return false;
                }
            }
            return true;
        }
    
    }
 
}

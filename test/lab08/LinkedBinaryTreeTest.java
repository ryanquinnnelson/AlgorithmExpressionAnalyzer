/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab08;


import forTesting.Player;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for LinkedBinaryTree class. Remember to add try / catch for all exceptions.
 * @author Ryan
 */
public class LinkedBinaryTreeTest {
    
    Player one;
    Player two;
    Player three;
    Player four;
    Player five;
    Player six;
    Player seven;
    Player eight;
    Player nine;
    
    LinkedBinaryTree<Player> emptyTree;
    LinkedBinaryTree<Player> onlyRoot;
    LinkedBinaryTree<Player> oneLevel;
    LinkedBinaryTree<Player> twoLevel;
    
    Position<Player> rootOfOnlyRoot;
    Position<Player> rootOfOneLevel;
    Position<Player> rootOfTwoLevel;

    Position<Player> threePosition;
    Position<Player> fourPosition;

    Position<Player> sixPosition;
    Position<Player> sevenPosition;
    
    Position<Player> eightPosition;
    Position<Player> ninePosition;
    
    
    public LinkedBinaryTreeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() 
    {
        one     = new Player("John", "Rhythm Guitar", 55);
        two     = new Player("Ringo", "Drums", 15);
        three   = new Player("Paul", "Bass", 44);
        four    = new Player("George", "Lead Guitar", 13);
        five    = new Player("Elvis", "Guitar", 88);
        six     = new Player("Yoko", "Citar", 7);
        seven   = new Player("Scott", "Marimba", 3);
        eight   = new Player("Johnny", "Harmonica", 43);
        nine    = new Player("Martha", "Banjo", 1);
        
        emptyTree       = new LinkedBinaryTree<>();
        
        onlyRoot        = new LinkedBinaryTree<>();
        rootOfOnlyRoot  = onlyRoot.addRoot(one);
        
        oneLevel        = new LinkedBinaryTree<>();
        rootOfOneLevel  = oneLevel.addRoot(two);
        threePosition    = oneLevel.addLeft(rootOfOneLevel, three);
        fourPosition    = oneLevel.addRight(rootOfOneLevel, four);
        
        twoLevel        = new LinkedBinaryTree<>();
        rootOfTwoLevel  = twoLevel.addRoot(five);
        sixPosition     = twoLevel.addLeft(rootOfTwoLevel, six);
        sevenPosition   = twoLevel.addRight(rootOfTwoLevel, seven);
        eightPosition   = twoLevel.addLeft(sixPosition, eight);
        ninePosition    = twoLevel.addRight(sixPosition, nine);
    }
    
    @After
    public void tearDown() {
    }

    //---------------------------------------Accessor methods -------------------------------------------
    /**
     * Test of numChildren method, of class AbstractBinaryTree.
     */  
    @Test
    public void testNumChildrenRootOnly() 
    {
        int expected = 0;
        int actual = onlyRoot.numChildren(rootOfOnlyRoot);
        assertEquals(expected, actual);
    }
    
    
    @Test
    public void testNumChildrenMultiple() 
    {
        int expected = 2;
        int actual = oneLevel.numChildren(rootOfOneLevel);
        
        assertEquals(expected, actual);
    }
    
    
    
     /**
     * Test of size method of class LinkedBinaryTree.
     */
    @Test
    public void testSizeEmptyTree() 
    {
        int expected = 0;
        int actual = emptyTree.size();
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void testSizeRootOnly() 
    {
        int expected = 1;
        int actual = onlyRoot.size();
        assertEquals(expected, actual);
    }
    
    
    @Test
    public void testSizeMultiple() 
    {
        int expected = 3;
        int actual = oneLevel.size();
        
        assertEquals(expected, actual);
    }
    
    
    
    
    /**
     * Test of root method of class LinkedBinaryTree.
     */
    @Test
    public void testRootRootOfOneLevel()
    {
        Position<Player> expected = rootOfOneLevel;
        Position<Player> actual = oneLevel.root();
        
        assertEquals(expected, actual);
    }
    
    
    
    
    /**
     * Test of parent method of class LinkedBinaryTree.
     */
    @Test
    public void testParentOnlyRoot()
    {
        Position<Player> expected = null;
        Position<Player> actual = onlyRoot.parent(rootOfOnlyRoot);
        
        assertEquals(expected, actual);
    }
    
    
    @Test
    public void testParentOneLevelLeaf()
    {
        Position<Player> expected = rootOfOneLevel;
        Position<Player> actual = onlyRoot.parent(threePosition);
        
        assertEquals(expected, actual);
    }
    
    
    
    
    /**
     * Test of left method of class LinkedBinaryTree.
     */
    @Test
    public void testLeftOnlyRoot()
    {
        Position<Player> expected = null;
        Position<Player> actual = onlyRoot.left(rootOfOnlyRoot);
        
        assertEquals(expected, actual);
    }
    
    
    @Test
    public void testLeftOneLevelCorrect()
    {
        Position<Player> expected = threePosition;
        Position<Player> actual = oneLevel.left(rootOfOneLevel);
        
        assertEquals(expected, actual);
    }
    
    
    
    
    /**
     * Test of right method of class LinkedBinaryTree.
     */
    @Test
    public void testRightOnlyRoot()
    {
        Position<Player> expected = null;
        Position<Player> actual = onlyRoot.right(rootOfOnlyRoot);
        
        assertEquals(expected, actual);
    }
    
    
    @Test
    public void testRightOneLevelCorrect()
    {
        Position<Player> expected = fourPosition;
        Position<Player> actual = oneLevel.right(rootOfOneLevel);
        
        assertEquals(expected, actual);
    }
    
  
    
    /**
     * Test of sibling method of class AbstractBinaryTree.
     */
    @Test
    public void testSiblingOnlyRoot()
    {
        Position<Player> expected = null;
        Position<Player> actual = onlyRoot.sibling(rootOfOnlyRoot);
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void testSiblingOneLevelLeaf()
    {
        Position<Player> expected = threePosition;
        Position<Player> actual = oneLevel.sibling(fourPosition);
        
        assertEquals(expected, actual);
    }
    
    
    
    
    
    //---------------------------------------Query methods -------------------------------------------
    /**
     * Test of isEmpty method of class Abstract Tree.
     */
    @Test
    public void testIsEmptyEmptyTree()
    {
        boolean expected = true;
        boolean actual = emptyTree.isEmpty();
        
        assertEquals(expected, actual);
    }
    @Test
    public void testIsEmptyOnlyRoot()
    {
        boolean expected = false;
        boolean actual = onlyRoot.isEmpty();
        
        assertEquals(expected, actual);
    }
    
    
    /**
     * Test of isExternal method of class Abstract tree.
     */
    @Test
    public void testIsExternalOnlyRoot()
    {
        boolean expected = true;
        boolean actual = onlyRoot.isExternal(rootOfOnlyRoot);
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void testIsExternalOneLevelRoot()
    {
        boolean expected = false;
        boolean actual = onlyRoot.isExternal(rootOfOneLevel);
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void testIsExternalOneLevelLeaf()
    {
        boolean expected = true;
        boolean actual = onlyRoot.isExternal(threePosition);
        
        assertEquals(expected, actual);
    }
    
    
    
    
    
    
    /**
     * Test of isInternal method of class Abstract tree.
     */
    @Test
    public void testIsInternalOnlyRoot()
    {
        boolean expected = false;
        boolean actual = onlyRoot.isInternal(rootOfOnlyRoot);
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void testIsInternalOneLevelRoot()
    {
        boolean expected = true;
        boolean actual = onlyRoot.isInternal(rootOfOneLevel);
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void testIsInternalOneLevelLeaf()
    {
        boolean expected = false;
        boolean actual = onlyRoot.isInternal(threePosition);
        
        assertEquals(expected, actual);
    }
    
    
    
    
    
    
    /**
     * Test of isRoot method of class AbstractTree.
     */
    @Test
    public void testIsRootRootOfOnlyRoot()
    {
        boolean expected = true;
        boolean actual = onlyRoot.isRoot(rootOfOnlyRoot);
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void testIsRootRootOfOneLevel()
    {
        boolean expected = true;
        boolean actual = oneLevel.isRoot(rootOfOneLevel);
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void testIsRootLeafOfOneLevel()
    {
        boolean expected = false;
        boolean actual = oneLevel.isRoot(threePosition);
        
        assertEquals(expected, actual);
    }
    
    
   
    
    
    //---------------------------------------Update methods -------------------------------------------
    
    /**
     * Test of addLeft method of class LinkedBinaryTree.
     */
    @Test
    public void testOfAddLeft()
    {
        Position<Player> inserted = onlyRoot.addLeft(rootOfOnlyRoot, nine);
        Position<Player> retrieved = onlyRoot.left(rootOfOnlyRoot);
        
        assertEquals(inserted, retrieved);
    }
    
    
    /**
     * Test of addRight method of class LinkedBinaryTree.
     */
    @Test
    public void testOfAddRight()
    {
        Position<Player> inserted = onlyRoot.addRight(rootOfOnlyRoot, nine);
        Position<Player> retrieved = onlyRoot.right(rootOfOnlyRoot);
        
        assertEquals(inserted, retrieved);
    }
    
    
    /**
     * Test of addRoot method of class LinkedBinaryTree.
     */
    @Test
    public void testOfAddRootEmptyTree()
    {
        Position<Player> inserted = emptyTree.addRoot(nine);
        Position<Player> retrieved = emptyTree.root();
        
        assertEquals(inserted, retrieved);
    }
    
    
    /**
     * Test of attach method of class LinkedBinaryTree.
     */
    @Test
    public void testAttachOneLevelToOnlyRoot()
    {
        int expected = 9;
        
        onlyRoot.attach(rootOfOnlyRoot, oneLevel, twoLevel);
        
        
        int actual = onlyRoot.size();
        
        assertEquals(expected, actual);
    }
    
    
    /**
     * Test of remove method of class LinkedBinaryTree.
     */
    @Test
    public void testRemoveOnlyRoot()
    {
        Player expected = new Player("John", "Rhythm Guitar", 55);
        
        Player actual = onlyRoot.remove(rootOfOnlyRoot);
        
        assertEquals(expected, actual);
    }
    
    
    @Test
    public void testRemoveOneLevelLeaf()
    {
        Player expected = new Player("Paul", "Bass", 44);
        
        Player actual = oneLevel.remove(threePosition);
        
        assertEquals(expected, actual);
    }
    
    
    
    @Test
    public void testSetOneLevelLeaf()
    {
        Player expected = new Player("Paul", "Bass", 44);
        
        Player actual = oneLevel.set(threePosition, nine);
        
        assertEquals(expected, actual);
    }
    
    
    
    //---------------------------------------Additional methods -------------------------------------------
    
    /**
     * Test of children method of class AbstractBinaryTree.
     */
    @Test
    public void testChildrenOnlyRoot()
    {
        List<Position<Player>> expectedList = new ArrayList<>(2);
        
        
        
        Iterable<Position<Player>> actualList = onlyRoot.children(rootOfOnlyRoot);
        
        assertEquals(expectedList, actualList);
    }
    
    
    @Test
    public void testChildrenOneLevel()
    {
        List<Position<Player>> expectedList = new ArrayList<>(2);
        expectedList.add(threePosition);
        expectedList.add(fourPosition);
        
        
        Iterable<Position<Player>> actualList = oneLevel.children(rootOfOneLevel);
        
        assertEquals(expectedList, actualList);
    }
    
    
    /**
     * Test of toString method of class LinkedBinaryTree.
     */
    @Test
    public void testToStringEmptyTree()
    {
        String expected = "Empty Tree.";
        String actual = emptyTree.toString();
        
        assertEquals(expected, actual);
    }
    
    
    @Test
    public void testToStringOnlyRoot()
    {
        String expected = "LinkedBinaryTree<Player>: {(John, Rhythm Guitar, 55)}";
        String actual = onlyRoot.toString();
        
        assertEquals(expected, actual);
    }
    
    /**
     * Test of equals method of class LinkedBinaryTree.
     */
    @Test
    public void testEqualsOnlyRoots()
    {
        LinkedBinaryTree<Player> onlyRootB = new LinkedBinaryTree<>();
        onlyRootB.addRoot(one);
        
        boolean expected = true;
        
        boolean actual = onlyRoot.equals(onlyRootB);
        
        assertEquals(expected, actual);
    }
    
    
    //---------------------------------------AbstractTree class methods -------------------------------------------
    
    /**
     * Test of depth method of class AbstractTree.
     */
    @Test
    public void testDepthOnlyRoot()
    {
        int expected = 0;
        int actual = onlyRoot.depth(rootOfOnlyRoot);
        
        assertEquals(expected, actual);
    }
    
    
    @Test
    public void testDepthOneLevelRoot()
    {
        int expected = 0;
        int actual = oneLevel.depth(rootOfOneLevel);
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void testDepthOneLevelLeaf()
    {
        int expected = 1;
        int actual = oneLevel.depth(threePosition);
        
        assertEquals(expected, actual);
    }
    
    
    @Test
    public void testDepthTwoLevelLeaf()
    {
        int expected = 2;
        int actual = twoLevel.depth(ninePosition);
        
        assertEquals(expected, actual);
    }
    
    
    /**
     * Test of height method of class AbstractTree.
     */
    @Test
    public void testHeightOnlyRoot()
    {
        int expected = 0;
        int actual = onlyRoot.height(rootOfOnlyRoot);
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void testHeightOneLevelRoot()
    {
        int expected = 1;
        int actual = oneLevel.height(rootOfOneLevel);
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void testHeightTwoLevelRoot()
    {
        int expected = 2;
        int actual = twoLevel.height(rootOfTwoLevel);
        
        assertEquals(expected, actual);
    }
    
    
    
    //---------------------------------------Node class methods -------------------------------------------
    
    
}



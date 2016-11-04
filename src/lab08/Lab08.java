/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab08;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 * Reads fully parenthesized arithmetic expressions from a file and performs multiple operations on them.
 * @author ryan.quinn.nelson and michael.kleinsasser
 */
public class Lab08 
{
    //private utilities
                                                                                //NEED TO CHECK IF OPERATORS / PARENTHESIS PROPERLY LOCATED
    /**
     * Converts fully parenthesized pre-order arithmetic expression into a postorder expression and returns as a String.
     * Uses ShuntingYard algorithm to convert:
     * Takes a String of input tokens representing integers, parentheses, and operands and performs the following operations:
     * - If token is an integer, puts it in a queue. 
     * - If token is a left paren, puts it in a stack.
     * - If token is a right paren, searches stack for left paren. Both the left paren and the right paren that triggered the search are deleted.
     * - If token is an operator, checks operator precedence before adding to stack.
     * 
     * Continue until no tokens remain. 
     * 
     * @param shuntingLine String to process
     * @return String of arithmetic expression in postorder
     * @throws IllegalArgumentException if expression is empty
     * @throws IllegalArgumentException if unknown operator is encountered (i.e. "**")
     * @throws IllegalArgumentException if operators are not separated on both sides by integers in shunting line (i.e. "3 + 4 -"  or "3 + - 4")
     * @throws IllegalArgumentException if expression contains a single token that is not a number (i.e. "2^3" rather than "23" or "2 ^ 3")
     * @throws IllegalArgumentException if parenthesis are mismatched (stack is empty while searching 
     * for a left paren or left paren remaining in stack after shunting line has been added to queue) (i.e. "( ( 3 + 4 ) / 5" or "( 3 + 4 ) / 5 )")
     */
    private static Queue<String> toPostorder(String shuntingLine) throws IllegalArgumentException
    {
        Queue<String> holdingQueue = new LinkedQueue<>();   //queue to hold post-order expression
        Stack<String> holdingStack = new LinkedStack<>();   //stack to hold operands and parentheses
        boolean operatorNext = false;   //flag to detect if operators are not separated on both sides by integers
        
        if(shuntingLine.isEmpty())  //nothing to process
        {
            throw new IllegalArgumentException("Empty expression.");
        }
        
        
        Scanner scan = new Scanner(shuntingLine); 
        while(scan.hasNext())
        {           
            if(scan.hasNextInt())       //if token is an integer, goes to end of holdingQueue
            {
                holdingQueue.enqueue(scan.next());
                operatorNext = true;    //operator should be next in shunting line
                
                continue;     //skips remainder of loop and performs next iteration of loop
            }
            
            //token is not an integer and further checks must be done to determine what to do
            String current = scan.next(); 
            
            if(current.equals(")")) 
            {                            
                findLeftParen(holdingQueue, holdingStack);//searches for left paren in holdingStack, adding elements above it to holdingQueue
                //right paren is removed by not storing current  
            }
            else if(!current.equals("("))   //token is an operator 
            {
                if(!current.matches("\\p{Punct}")) //if token doesn't match single character operators
                {
                    throw new IllegalArgumentException("Operator not recognized: (" + current + ")");
                }
                
                
                if(!operatorNext) //operator shouldn't be next in shunting line
                {
                    throw new IllegalArgumentException("Operator isn't separated by operands.");
                }
                
                
                
                checkPrecedence(current, holdingQueue, holdingStack);   //check precedence order in case expression wasn't fully parenthesized
                operatorNext = false; //after adding operator to holdingStack, next token should be a number
            }
            else //token is a left paren
            {
                holdingStack.push(current); //it is added to the top of holdingStack
            }
        } 
        //after processing all tokens in shunting line, if last token in shunting line was an operator, expression is invalid
        if(!operatorNext) 
        {
            throw new IllegalArgumentException("Missing final operand.");
        }
        
        
        //after processing all tokens in shunting line, add remaining elements in holdingStack to holdingQueue
        while(!holdingStack.isEmpty())
        {
            String top = holdingStack.pop();
            
            if(top.equals("(")) //if any left paren is left in the stack, expression is invalid
            {
                throw new IllegalArgumentException("Unmatched left paren.");
            }
            
            holdingQueue.enqueue(top);
        }
        
        

        return holdingQueue;
    }
    
    /**
     * Searches given stack for left paren, adding elements above it to given queue.
     * Removes elements from stack and puts those elements in the queue until it finds the left paren. Then found left paren is removed.
     * @param q queue to add elements to
     * @param s stack to search for left paren
     */
    private static void findLeftParen(Queue<String> q, Stack<String> s) throws IllegalArgumentException
    {        
        
        if(s.isEmpty()) //there is no left paren to match to the right and expression is invalid
        {
            throw new IllegalArgumentException("Unmatched right paren.");
        }
        
        String top = s.pop(); 
        
        while(!top.equals("("))//move elements from stack to queue until there is a left paren
        {
            q.enqueue(top);
            
            if(s.isEmpty()) //there is no left paren to match to the right and expression is invalid
            {
                throw new IllegalArgumentException("Unmatched right paren.");
            }

            top = s.pop();
        }
        //left paren is removed by popping it and not enqueuing it
    }
    
    /**
     * Ensures arithmetic precedence order is preserved while converting to post-order.
     * If a second operator is encountered while converting String to post-order,
     * checks the precedence of current operator in shunting line against operators in holdingStack.
     * 
     * - If shunting line operator has greater precedence than top of holdingStack,
     * adds shunting line operator to top of holdingStack.
     * 
     * - If shunting line operator has equal or lower precedence than top of holdingStack, 
     * removes the top of holdingStack and adds it to end of holdingQueue.
     * 
     * Continue this process until shunting line operator has greater precedence
     * than top of holdingStack (and has been added to holdingStack) or until holdingStack has run out of elements.
     * In the case that holdingStack has run out of elements, add shunting line operator to top of holdingStack.
     * 
     * @param current current operator in shunting line
     * @param q queue to add elements to
     * @param s stack to search for left paren
     */
    private static void checkPrecedence(String current, Queue<String> q, Stack<String> s) throws IllegalArgumentException
    {       
        int stackOperatorValue; //represents precedence of operator in holdingStack
        int lineOperatorValue;  //represents precedence of operator in shunting line
        
        boolean addedToStack = false;   //flag to represent if token has already been added to stack to signal utility to end

        while(!s.isEmpty()) //compare operators from stack until stack is empty or operator from shunting line has been added to stack
        {
            String peek = s.top();  //get element at top of holdingStack
            
            if(peek.equals("("))    //if element is not an operator, add shunting line token to stack and skip rest of method
            {
                s.push(current);
                addedToStack = true;
                break; //precedence order has been preserved, so utility is finished
            }
            
            //determine precedence of operator in holdingStack (higher number has greater precedence)
            if(peek.equals("^"))
            {
                stackOperatorValue = 2;
            }
            else if(peek.equals("*") || peek.equals("/"))
            {
                stackOperatorValue = 1;
            }
            else //operator is + or -
            {
                stackOperatorValue = 0;
            }

            //determine precedence of operator in shunting line
            if(current.equals("^"))
            {
                lineOperatorValue = 2;
            }
            else if(current.equals("*") || current.equals("/"))
            {
                lineOperatorValue = 1;
            }
            else //operator is + or -
            {
                lineOperatorValue = 0;
            }


            
            if(lineOperatorValue > stackOperatorValue) //if operator in shunting line has higher precedence than operator in holdingStack 
            {                
                s.push(current);//add shunting line operator to top of holdingStack
                addedToStack = true;
                break; //precedence order has been preserved, so utility is finished
            }
            else //shunting line has lower than or equal precedence
            {
               q.enqueue(s.pop());  //shift operator from holdingStack to holdingQueue 
            }
        }//repeat for next operator in holdingStack until operator in shunting line has been moved or stack runs out of operators
        
        
        if(s.isEmpty() && !addedToStack) //if stack ran out of operators and shunting line operator hasn't been added to stack
        {
            s.push(current); //add operator in shunting line to top of holdingStack
        }
    } //precedence order has been preserved, so utility is finished
    
    /**
     * Constructs binary expression tree from post-order tokens.
     * @param postorderQueue list of tokens in post-order
     * @return binary expression tree representing arithmetic expression
     */
    private static LinkedBinaryTree<String> buildBinaryTree(Queue<String> postorderQueue)
    {      
        Stack<LinkedBinaryTree<String>> expressionStack = new LinkedStack<>(); //create expression stack of node pointers
        
        
        while(!postorderQueue.isEmpty()) //while postfix list has token (process postfix from left to right)
        {
            String current = postorderQueue.dequeue(); //get token
            
            if(!current.matches("\\p{Punct}")) //if token is operand (number), create a one node tree and push it onto the expression stack
            {
                LinkedBinaryTree<String> T = new LinkedBinaryTree<>();
                T.addRoot(current);
                expressionStack.push(T);
            }
            else//token is operator, create new tree with operator as root and attach subtrees in stack to new tree as children
            {
                LinkedBinaryTree<String> T = new LinkedBinaryTree<>();
                Position<String> root =  T.addRoot(current);
                
                LinkedBinaryTree<String> right = expressionStack.pop();//pop node off expression stack and add as right child
                LinkedBinaryTree<String> left = expressionStack.pop();//pop node off expression stack and add as left child
                
                T.attach(root,left, right);
                
                expressionStack.push(T);//push new tree pointer onto expression stack  
            } 
        }//when postfix list is empty, expression stack should have one entry which is a node pointer to the root of the expression tree
 
        return expressionStack.pop();
    }
    
    /**
     * Traverses given expression tree in post-order and constructs queue of tokens.
     * @param T tree to traverse
     * @return post order queue of expression tree
     */
    private static Queue<String> buildPostorderQueue(LinkedBinaryTree<String> T)
    {
        LinkedQueue<String> q = new LinkedQueue<>();
        
        for(Position<String> p : T.postorder()) //traverses tree one position at a time
        {
            q.enqueue(p.getElement());
        }
        return q;
    }
    
    /**
     * Evaluates given queue of tokens as an arithmetic expression and prints the result to the console.
     * Uses the following algorithm:
     * - if String token is number, put on stack
     * - if String token is operator, pull two numbers from the stack
     * - top String token (number) from stack is right value
     * - second String token (number) from stack is left value
     * - converts the String tokens to double values then evaluates based on given operator
     * - places result back in Stack
     * - process continues until queue is empty
     * 
     * @param postorderQueue post-order expression to evaluate
     */
    private static void evaluate(Queue<String> expressionQueue)
    {
        
        Stack<String> evaluatingStack = new LinkedStack<>();
        
        double result = Double.valueOf(expressionQueue.first());    //stores first number from expressionQueue in case queue has a single token

        while(!expressionQueue.isEmpty())   
        {
            String current = expressionQueue.dequeue();
            
            if(!(current.matches("\\p{Punct}")))  //token is a number and should be added to the evaluatingStack           
            {
                evaluatingStack.push(current);
            }
            else //token is an valid operator
            {
                //pull top two numbers from the evaluating stack
                String op = evaluatingStack.pop();  //top of stack is righthand number in expression
                double right = Double.valueOf(op);  //convert to double to make arithmetic evaluation easier
                
                String op2 = evaluatingStack.pop(); //second number in stack is lefthand number in expression
                double left = Double.valueOf(op2);  //convert to double to make arithmetic evaluation easier
                
                switch(current)
                {
                    case "^": result = Math.pow(left, right);
                        break;
                    case "*": result = left * right;
                        break;
                    case "/": result = left / right;
                        break;
                    case "+": result = left + right;
                        break;
                    case "-": result = left - right;
                        break;
                }
                
                evaluatingStack.push(String.valueOf(result)); //puts result back on the evaluatingStack  
            }  
        }

        System.out.println("The expression evaluates to: " + result + "\n");
        
    }
    
    /**
     * Prints out a message followed by a collection of Positions of type String representing a particular style of tree traversal.
     * @param message describes the style of tree traversal that will be shown
     * @param iter represents a collection of Positions of type String of a particular style of tree traversal
     */
    private static void printTraversal(String message, Iterable<Position<String>> collection)
    {
        System.out.println(message);
        
        for(Position<String> p : collection){
            System.out.print(p.getElement() + " ");
        }
        System.out.println("\n");
    }
    
    
    
    //public additional methods
     /**
     * Prompts user for File and requires that File exists before completing method.
     * @return File object
     */
    public static File getFile() 
    {
        File f;
        
        while (true) //loops until valid File object is given or user quits the program
        {
            String input = JOptionPane.showInputDialog("Enter absolute path and filename of a file that contains list of arithmetic expressions.");
            
            if(input == null)   //user hits cancel or close button
            {
                System.exit(0);
            }
            
            if(input.isEmpty()) //user left input box blank
            {
                JOptionPane.showMessageDialog(null, "Invalid input");
                continue;   //skips over remainder of loop, and performs next iteration of loop
            }
            
            f = new File(input);    //constructs File object from input
            
            try //check to make sure File object exists
            {
                Scanner scan = new Scanner(f);
                break; //File object exists
            } 
            catch (FileNotFoundException fnfe) 
            {
                JOptionPane.showMessageDialog(null, "File not found at: " + input);
            }
        }
        return f;
    }
    
    /**
     * Processes given File line by line and returns a Queue of contents.
     * @param f File object to process
     * @return Queue of String objects
     */
    public static Queue<String> readFile(File f)
    {
        Scanner scan;
        Queue<String> inputQueue = new LinkedQueue<>();
        
        
        try
        {
            scan = new Scanner(f);
            while(scan.hasNextLine())
            {
                String s = scan.nextLine();
                
                if(s.isEmpty()) //doesn't add empty Strings to queue
                {
                    continue;
                }
                inputQueue.enqueue(s.trim());    //removes whitespace characters at front and back and adds line to end of Queue
            }
            scan.close();
        }
        catch(Exception e)  //f already passed the try-catch method of getFile(); shouldn't throw FileNotFoundException
        {
            JOptionPane.showMessageDialog(null, "Processing error. Program terminated.");   //something else went wrong
            System.exit(0);
        }
        return inputQueue;
    }
    
    /**
     * Processes each token in given Queue.
     * Prints each token that was read from file.
     * Determines if the token is a valid arithmetic expression by attempting to convert it to a postorder queue.
     * 
     * For each invalid expression, prints out an error message.
     * For each valid expression, performs the following:
     * - Constructs a binary expression tree
     * - Evaluates the expression and displays the results
     * - Displays the contents of the tree using pre-order, in-order, post-order, and eulerTourBinary traversals
     * 
     * @param preorderQueue Queue to process
     */
    public static void processLine(Queue<String> preorderQueue)
    {
        Queue<String> postorderQueue;
        
        while(!preorderQueue.isEmpty()) //processes every token in preorderQueue
        {
            //phase 1 - attempt to build postorder queue of expression
            String line = preorderQueue.dequeue();
            System.out.println("-------------------------------------------------------------------------------------------------");
            System.out.println("Expression from file: " + line + "\n");     //print expression that was read from file
            
            try //determine if the expression is valid by trying to convert to post-order
            {
                postorderQueue = toPostorder(line);
            } 
            catch (IllegalArgumentException iae) //expression is not valid, triggers error messages
            {
                JOptionPane.showMessageDialog(null, line + "\n\nCurrent expression is invalid: " + iae.getMessage() + "\nProcessing next expression.");
                System.out.println("Current expression is invalid: " + iae.getMessage() +  " Processing next expression.");
                continue;   //skips remainder of while loop and performs next iteration
            }
            
            
//            System.out.println("Postorder: " + postorderQueue +"\n");           //TEMP TEMP TEMP TEMP TEMP TEMP TEMP TEMP 
            
            
            //phase 2 - expression is valid and postorder queue of expression has been constructed
            LinkedBinaryTree<String> expression = buildBinaryTree(postorderQueue);  //constructs expression tree
            
            Queue<String> expressionQueue = buildPostorderQueue(expression);    //builds an expression queue for evaluation
            
            evaluate(expressionQueue);  //evaluates and prints expression
            
            printTraversal("Pre-order traversal:", expression.preorder());
            printTraversal("In-order traversal:", expression.inorder());
            printTraversal("Post-order traversal:", expression.postorder());
            
            System.out.println("Euler Binary Tour traversal:");
            expression.eulerTourBinary(expression, expression.root());
            System.out.println("");
        } //has processed every token   
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.println("End of File.");
    }
    
    
    
    
    //main method
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Welcome to Arithmetic Expression Analyzer.\n"
                + "The Analyzer takes a File and evaluates each of its lines as an arithmetic expression.");
        
        Queue<String> outputByLine = readFile(getFile());
        processLine(outputByLine);
        System.out.println("Quitting Program. Good-bye.");
    }   
}

/**********************************************************************************************
 * @file : BST.java
 * @description : Binary Search Tree class, including a root, left, right, and Stack values.
 *                Public methods are insert(), search(), and remove().
 * @author : Ella Shipman
 * @date : September 18, 2025
 *********************************************************************************************/


import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

class Node<E extends Comparable<E>> implements Comparable<Node<E>> {
    private E value;
    private Node<E> left;
    private Node<E> right;

    //Default Constructor
    public Node() {
        value = null;
        left = null;
        right = null;
    }

    //Copy Constructor (Node)
    public Node(Node<E> node) {value = node.getValue();}

    //Copy Constructor (Value)
    public Node(E value) {this.value = value;}

    //Get and set value
    public E getValue() { return value; }
    public void setValue(E newValue) {this.value = newValue;}

    //Get and set left
    public Node<E> getLeft() {return left;}
    public void setLeft(Node<E> nextNode) {left = nextNode;}

    //Get and set right
    public Node<E> getRight() {return right;}
    public void setRight(Node<E> nextNode) {right = nextNode;}

    //Comparable method
    @Override
    public int compareTo(Node<E> newNode) {
        return getValue().compareTo(newNode.getValue());
    }
}



public class BST<E extends Comparable<E>> {
    Node<E> root;
    Node<E> left;
    Node<E> right;
    Stack<Node<E>> nodeStack;
    int size;

    //Default Constructor
    BST() {
        root = null;
        left = null;
        right = null;
        nodeStack = new Stack<>();
    }

    //Clears the BST from all information
    public void clear() {root = null; size = 0;}

    //Returns the size of the BST
    public int size() { return size; }

    //Setter and getter for root
    public void setRoot(Node<E> rt) { root = rt; }
    public Node<E> getRoot() { return root; }

    //Returns whether the BST is empty
    public boolean isEmpty() {return (size == 0);}

    /*  --------------------------------------------------------------------------------------
     *   findLeftTreeMax - Returns the largest value in the left subtree
     *   Node<E> rt, Node<E> target : the node to be removed from the BST
     */
    public Node<E> findLeftTreeMax(Node<E> rt, Node<E> target) {
        Node <E> max = null;
        if (rt == null) { System.out.println("Missing root"); return null; }
        if (target.getValue() == null) {System.out.println("Missing target"); return null;}
        if (rt.getRight() != null && rt.getLeft() != null) {
            if (rt.getRight().compareTo(target) > 0) {        //If right side is greater than target
                max = findLeftTreeMax(rt.getLeft(), target);
            } else {
                max = findLeftTreeMax(rt.getRight(), target);
            }
        }
        else { return rt; }
        System.out.println("Max found: " + max.getValue());
        return max;
    }

    /*  --------------------------------------------------------------------------------------
     *   insert - Inserts a node into the BST
     *   Node<E> rt : root of BST
     *   Node<E> target : the inserted node
     */
    public void insert (Node<E> rt, Node<E> insertedNode) {
        if (rt == null) { root = insertedNode;      //No root, insert
            //System.out.println("Setting root");
            size++;
            return; }
        if (rt.compareTo(insertedNode) > 0) {       //Inserted node is less than root, left side
            if (rt.getLeft() == null) {
                //System.out.println("Inserted " + insertedNode.getValue() + " at left");
                rt.setLeft(insertedNode);
                size++;
            } else {
                insert(rt.getLeft(), insertedNode);
            }
        } else if (rt.compareTo(insertedNode) < 0) {        //Inserted node is greater than root, right side
            if (rt.getRight() == null) {
                //System.out.println("Inserted " + insertedNode.getValue() + " at right");
                rt.setRight(insertedNode);
                size++;
            } else {
                insert(rt.getRight(), insertedNode);
            }
        } else if (rt.compareTo(insertedNode) == 0) {       //Already exists in tree
            //System.out.println(insertedNode.getValue() + " is already in the tree");
            return;
        }
    }

    /*  --------------------------------------------------------------------------------------
     *   remove - Returns true if a given value is removed from the BST
     *   Node<E> target : the removed node
     */
    public Node<E> remove(Node<E> target) {
        fillStack(root);
        return removeHelper(target);
    }
    private Node<E> removeHelper(Node<E> target) {
        if (nodeStack.isEmpty()) {
            return null;
        }
        Node<E> currNode = nodeStack.pop();
        Node<E> parentNode = null;
        if (currNode.compareTo(target) == 0) {      //target found
            parentNode = findParent(target);
            if (currNode.getLeft() == null && currNode.getRight() == null) {            //leaf node
                simpleRemoveNode(parentNode, currNode, null);
                size--;
                return currNode;
            } else if (currNode.getLeft() != null && currNode.getRight() != null) {     //both children
                Node<E> max = findLeftTreeMax(currNode, currNode);    //find left tree max
                Node<E> maxParent = findParent(max);
                if (target.compareTo(root) == 0) {      //if target is root
                    simpleRemoveNode(maxParent, max, max.getLeft());
                } else {
                    simpleRemoveNode(maxParent, max, null);         //delete left tree max
                }
                Node<E> temp = new Node<E>(currNode.getValue());
                currNode.setValue(max.getValue());          //set target to left tree max
                size--;
                return temp;
            } else if (currNode.getLeft() != null) {                                    //left child only
                simpleRemoveNode(parentNode, currNode, currNode.getLeft());
                size--;
                return currNode;
            } else if (currNode.getRight() != null){                                                                    //right child only
                simpleRemoveNode(parentNode, currNode, currNode.getRight());
                size--;
                return currNode;
            }
        }
        //System.out.println(currNode.getValue() + " is not the target");
        return removeHelper(target);        //target not found, move along in stack
    }

    /*  --------------------------------------------------------------------------------------
     *   insert - Replaces the child node with the newChild node
     *   Node<E> parent : the parent of child node
     *   Node<E> child : the child of parent node
     *   Node<E> newChild : the value to replace child
     */
    private void simpleRemoveNode(Node<E> parent, Node<E> child, Node<E> newChild) {
        if (parent.getLeft() == null && parent.getRight() == null) {
            System.out.println("Find parent faulty - simple remove");
        } else if (parent.getLeft() == null) {            //right child is removed
            if (parent.getRight().compareTo(child) == 0) {
                parent.setRight(newChild);
            }
        } else if (parent.getRight() == null){            //left child is removed
            if (parent.getLeft().compareTo(child) == 0) {
                parent.setLeft(newChild);
            }
        } else {            //both children present
            if (parent.getLeft().compareTo(child) == 0) {
                parent.setLeft(newChild);
            } else if (parent.getRight().compareTo(child) == 0) {
                parent.setRight(newChild);
            }
        }
    }

    /*  --------------------------------------------------------------------------------------
     *   findParent - Returns the parent node of a given child not in the BST.
     *                Stack will not be cleared.
     *   Node<E> child : the node to find the parent of
     */
    public Node<E> findParent(Node<E> child) {
        fillStack(root);
        return findParentHelper(child);
    }
    private Node<E> findParentHelper(Node<E> child) {
        if (child == null) { return null; }
        //stack is empty, fill stack or return
        Node<E> parent = null;
        try {
            parent = nodeStack.pop();
        } catch (Exception e) {
            if (root != null) { fillStack(root); fillStackHelper(child);} else {return null;}
        }
        if (child.compareTo(root) == 0) { return child; }
        if (parent.getLeft() != null && parent.getRight() != null) {        //both children present, return parent if one child is the target
            if (parent.getLeft().compareTo(child) == 0 || parent.getRight().compareTo(child) == 0) {    //search for parent
                return parent;
            }else {
                return findParentHelper(child);
            }
        } else if (parent.getLeft() == null && parent.getRight() == null) { //leaf node, move to next in stack
            return findParentHelper(child);
        } else if (parent.getLeft() == null) {     // left child is null, check right child
            if (parent.getRight().compareTo(child) == 0) {
                nodeStack.push(parent);
                return parent;
            } else {
                return findParentHelper(child);
            }
        } else {
            if (parent.getRight() == null) {             //right child is null, check left child
                if (parent.getLeft().compareTo(child) == 0) {
                    nodeStack.push(parent);
                    return parent;
                } else {
                    return findParentHelper(child);
                }
            }
        }
        return findParentHelper(child);
    }

    /*  --------------------------------------------------------------------------------------
     *   insert - Returns a given value if it's been found in the BST
     *   Node<E> target : the searched node
     */
    public Node<E> search(Node<E> target) {
        fillStack(root);
        return searchHelper(target);
    }
    private Node<E> searchHelper(Node<E> target) {
        if (nodeStack.isEmpty()) {
            return null;
        }
        Node<E> currNode = new Node<E>(nodeStack.pop());
        if (currNode.compareTo(target) == 0) {      //target found
            return currNode;
        }
        return searchHelper(target);
    }

    /*  --------------------------------------------------------------------------------------
     *   fillStack - Populates the nodeStack based on the BST
     *   Node<E> rt : the root of the BST
     */
    public void fillStack(Node<E> rt) {
        clearStack();
        fillStackHelper(rt);
    }
    private void fillStackHelper(Node<E> rt) {
        if (rt == null) { //System.out.println("null root");
            return; }
        //System.out.println("In fill stack helper: ");
        //System.out.println(rt.getValue()); //FOR TESTING
        nodeStack.push(rt);
        //System.out.println(nodeStack.peek().getValue()); //fpr testing
        fillStackHelper(rt.getLeft());
        fillStackHelper(rt.getRight());
    }

    //Clears nodeStack
    public void clearStack() {
        nodeStack.clear();
    }

    //Prints the BST into a file (ascending order)
    public void ascendingOrderToFile(Node<E> rt, FileWriter writer) {
        fillStack(root);
        if (rt == null) { return; }
        try {
            if (rt.getLeft() != null) { ascendingOrderToFile(rt.getLeft(), writer); }
            writer.write(rt.getValue().toString() + " \n");
            if (rt.getRight() != null) { ascendingOrderToFile(rt.getRight(), writer); }
        } catch (IOException e) {
            System.out.println("Cannot print in file.");
        }
    }

    //Prints the BST into a file (pre-order)
    public void preOrderToFile(FileWriter writer) {
        fillStack(root);
        Node<E> currNode = null;
        String output = "";
        while (!nodeStack.isEmpty()) {
            currNode = nodeStack.pop();
            output = currNode.getValue().toString() + " -> " + output;
        }
        try {
            System.out.print(output);
            writer.write(output + "\n");
        } catch (IOException e) {
            System.out.println("Cannot print in file.");
        }
    }

}



// Implement the iterator method


// Implement the BSTIterator class
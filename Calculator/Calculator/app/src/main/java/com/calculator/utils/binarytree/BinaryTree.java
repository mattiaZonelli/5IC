package com.calculator.utils.binarytree;

/**
 * Created by rainstorm on 1/13/17.
 */

public class BinaryTree {

    // Define the root node.
    private BinaryNode root;

    public BinaryTree(){
        this.root=null;
    }
    /**
     * Insert into the tree.
     * @param value
     */
    public void insert(Object value, boolean right){

        // If we don't have a root node, make one.
        if(root == null){

            // Make the root node.
            root = new BinaryNode(value);

        } else {

            // Set the current node.
            BinaryNode current = root;

            // Find the proper spot.
            while(current != null){

                // Compare values.
                if(!right){
                    // Insert on the left.
                    if(current.getLeft() == null){
                        current.setLeft(new BinaryNode(value));
                        return;
                    } else {
                        current = current.getLeft(); // Go to next left.
                    }
                } else {
                    // Insert on the right.
                    if(current.getRight() == null){
                        current.setRight(new BinaryNode(value));
                        return;
                    } else {
                        current = current.getRight(); // Go to next right.
                    }
                }

            }

        }

    }


    /**
     * Print the binary tree in ascending order of value.
     * @param n
     */
    public void print(BinaryNode n){

        if(n == null){
            return;
        } else {
            print(n.getLeft());
            System.out.println(n.getValue());
            print(n.getRight());
        }

    }

    public void print(){
        print(root);
    }

    /**
     * Return the number of nodes in the tree.
     * @return
     */
    public int size(BinaryNode n){

        // If the node is null, return 0.
        if(n == null){
            return 0;
        } else {
            // We have at least one element. Get the size of it's children.
            return 1 + size(n.getLeft()) + size(n.getRight());
        }

    }

    public int size(){
        return size(root);
    }

    /**
     * Return the (max) depth of the tree.
     * @return
     */
    public int depth(BinaryNode n){

        // Base case.
        if(n == null){
            return 0;
        } else {
            // Get the depth of the left side.
            int leftSideDepth = depth(n.getLeft());
            // Get the depth of the right size.
            int rightSideDepth = depth(n.getRight());
            // Return the maximum depth of the two.
            return 1 + Math.max(leftSideDepth, rightSideDepth);
        }

    }

    public int depth(){
        return depth(root);
    }

}

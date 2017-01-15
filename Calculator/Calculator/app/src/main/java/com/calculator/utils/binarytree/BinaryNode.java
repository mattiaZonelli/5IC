package com.calculator.utils.binarytree;

/**
 * Created by rainstorm on 1/13/17.
 */

public class BinaryNode {

    // References to left and right nodes.
    private BinaryNode left;
    private BinaryNode right;

    // The value of the node.
    private Object value;

    // Constructors.
    public BinaryNode(Object value){
        this.value = value;
    }
    public BinaryNode(Object value, BinaryNode nL, BinaryNode nR){
        this.value = value;
        left = nL;
        right = nR;
    }

    // Getter & setter for the value.
    public Object getValue(){
        return value;
    }
    public void setValue(Object value){
        this.value = value;
    }

    // Getters & setters for left & right nodes.
    public BinaryNode getLeft(){
        return left;
    }
    public BinaryNode getRight(){
        return right;
    }
    public void setLeft(BinaryNode n){
        left = n;
    }
    public void setRight(BinaryNode n){
        right = n;
    }

}
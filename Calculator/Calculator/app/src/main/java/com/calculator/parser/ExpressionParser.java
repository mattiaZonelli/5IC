package com.calculator.parser;

import com.calculator.utils.binarytree.BinaryTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by rainstorm on 1/15/17.
 */

public class ExpressionParser {

    private String toParse;

    private Set<String> operators;

    private Set<String> brackets;

    private Set<String> digits;

    private Set<String>interpunction;


    public ExpressionParser(String toParse){
        this.toParse=toParse;
        operators= new HashSet<>(4);
        operators.add("+");operators.add("-");operators.add("*");operators.add("/");
        brackets=new HashSet<>(6);
        brackets.add("(");brackets.add(")");brackets.add("[");brackets.add("]");brackets.add("{");brackets.add("}");
        digits=new HashSet<>(10);
        digits.add("0");digits.add("1");digits.add("2");digits.add("3");digits.add("4");digits.add("5");digits.add("6");digits.add("7");digits.add("8");digits.add("9");
        interpunction=new HashSet<>(2);
        interpunction.add(",");interpunction.add(".");

    }

    public BinaryTree parse(){

    }

    private void inspect() {

        for (int i=0;i<toParse.length();i++){
            if(""+toParse.charAt(i))
        }
    }
}

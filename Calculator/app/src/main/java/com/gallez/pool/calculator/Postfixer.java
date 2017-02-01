package com.gallez.pool.calculator;

import android.util.ArrayMap;

import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

/**
 * Created by pool on 17/01/17.
 */

public class Postfixer {

    private enum Operator
    {
        ADD(1), SUBTRACT(2), MULTIPLY(3), DIVIDE(4);
        final int precedence;
        Operator(int p) { precedence = p; }
    }

    private static Map<String, Operator> ops = new HashMap<String, Operator>() {{
        put("+", Operator.ADD);
        put("-", Operator.SUBTRACT);
        put("*", Operator.MULTIPLY);
        put("/", Operator.DIVIDE);
    }};

    private static boolean isHigerPrec(String op, String sub)
    {
        return (ops.containsKey(sub) && ops.get(sub).precedence >= ops.get(op).precedence);
    }

    public static boolean verifyExpr(String expr){

        return expr.split("\\s").length %2 != 0;
    }

    public static String postfix(String infix)
    {
        StringBuilder output = new StringBuilder();
        Deque<String> stack  = new LinkedList<>();


        for (String token : infix.split("\\s")) {
            // operator
            if (ops.containsKey(token)) {
                while ( ! stack.isEmpty() && isHigerPrec(token, stack.peek()))
                    output.append(stack.pop()).append(' ');
                stack.push(token);

                // left parenthesis
            } else if (token.equals("(")) {
                stack.push(token);

                // right parenthesis
            } else if (token.equals(")")) {
                while ( ! stack.peek().equals("("))
                    output.append(stack.pop()).append(' ');
                stack.pop();

                // digit
            } else {
                output.append(token).append(' ');
            }
        }

        while ( ! stack.isEmpty())
            output.append(stack.pop()).append(' ');

        return output.toString();
    }

    public static String prepareInfix(String infix){
        String preparedInfix = "";
        int lastOp = 0;
        for(int i = 0; i < infix.length();i++){
            if(ops.containsKey(infix.charAt(i)+"")) {
                preparedInfix += (infix.substring(lastOp,i)+" "+infix.charAt(i)+" ");
                lastOp = i+1;
            }
        }
        return preparedInfix+infix.substring(lastOp,infix.length());
    }

    public static String removeCommas(String expr){
        return expr.replaceAll(",",".");
    }

    public static Double calculate(String postfix){
        Stack <Double> tmp = new Stack<>();
        String [] splittedPostfix = postfix.split(" ");
        for(int i = 0; i<splittedPostfix.length;i++){
            if(splittedPostfix[i].charAt(0) >= '0' && splittedPostfix[i].charAt(0) <= '9')
                tmp.push(Double.parseDouble(splittedPostfix[i]));
            else {
                double op2 = tmp.pop().doubleValue();
                double op1 = tmp.pop().doubleValue();
                switch (splittedPostfix[i].charAt(0)) {
                    case '+': tmp.push(op1+op2);
                        break;
                    case '-': tmp.push(op1-op2);
                        break;
                    case '*': tmp.push(op1*op2);
                        break;
                    case '/': tmp.push(op1/op2);
                        break;
                }
            }
        }
        return tmp.pop();
    }
}

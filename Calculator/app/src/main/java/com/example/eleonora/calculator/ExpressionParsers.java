package com.example.eleonora.calculator;

/**
 * Created by Eleonora on 25/01/2017.
 */

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;



public class ExpressionParsers {

    private String toParse;



    private Set<String> operators;

    private Set<String> brackets;

    private Set<String> digits;

    private Set<String> interpunction;

    private Set<String> junction;


    public ExpressionParsers(String toParse) {
        this.toParse = toParse;

        operators = new HashSet<>(4);
        operators.add("+");
        operators.add("-");
        operators.add("\u00d7");
        operators.add("\u00f7");

        brackets = new HashSet<>(6);
        brackets.add("(");
        brackets.add(")");
        brackets.add("[");
        brackets.add("]");
        brackets.add("{");
        brackets.add("}");
        digits = new HashSet<>(10);
        digits.add("0");
        digits.add("1");
        digits.add("2");
        digits.add("3");
        digits.add("4");
        digits.add("5");
        digits.add("6");
        digits.add("7");
        digits.add("8");
        digits.add("9");
        interpunction = new HashSet<>(2);
        interpunction.add(",");
        interpunction.add(".");
        junction = new HashSet<>();
        junction.addAll(operators);
        junction.addAll(brackets);
        junction.addAll(digits);
        junction.addAll(interpunction);

    }

    private void addSpaces() {
        String temp = "";
        for (int i = 0; i < toParse.length(); i++) {
            if (operators.contains("" + toParse.charAt(i))) {
                temp += " " + toParse.charAt(i) + " ";
            } else {
                temp += toParse.charAt(i);
            }
        }
        toParse = temp;
    }

    public String infixToPostfix() {
        addSpaces();
        String ops = "-+\u00f7\u00d7^√";
        StringBuilder output = new StringBuilder();
        Stack<Integer> s = new Stack<>();
        for (String token : toParse.split("\\s")) {
            if(token.equals("\u03c0")){
                token=""+Math.PI;
            }else if(token.equals("\u2107")){
                token=""+Math.E;
            }
            if (!token.isEmpty()) {
                char c = token.charAt(0);
                int idx = ops.indexOf(c);

                // check for operator
                if (idx!=-1) {
                    if (s.isEmpty())
                        s.push(idx);

                    else {
                        while (!s.isEmpty()) {
                            int prec2 = s.peek() / 2;
                            int prec1 = idx / 2;
                            if (prec2 > prec1 || (prec2 == prec1 && c != '^' && c != '\u03C0'))
                                output.append(ops.charAt(s.pop())).append(' ');
                            else break;
                        }
                        s.push(idx);
                    }
                } else if (c == '(') {
                    s.push(-2); // -2 stands for '('
                } else if (c == ')') {
                    // until '(' on stack, pop operators.
                    while (s.peek() != -2)
                        output.append(ops.charAt(s.pop())).append(' ');
                    s.pop();
                } else {
                    output.append(token).append(' ');
                }
            }
        }
        while (!s.isEmpty())
            output.append(ops.charAt(s.pop())).append(' ');
        return output.toString();
    }

    public Number evalPf(String str) {
        System.out.println(str);
        Scanner sc = new Scanner(str);
        Stack<Number> stack = new Stack<>();
        while (sc.hasNext()) {
            if (sc.hasNextDouble()) {
                stack.push(sc.nextDouble());
            } else if (sc.hasNextInt()) {
                stack.push(sc.nextInt());
            } else {
                Number b = stack.isEmpty() ? null : stack.pop();
                Number a = stack.isEmpty() ? null : stack.pop();
                char op = sc.next().charAt(0);
                if (op == '+')
                    stack.push(a.doubleValue() + b.doubleValue());
                else if (op == '-')
                    stack.push(a.doubleValue() - b.doubleValue());
                else if (op == '\u00d7')
                    stack.push(a.doubleValue() * b.doubleValue());
                else if (op == '\u00f7')
                    stack.push(a.doubleValue() / b.doubleValue());

            }
            System.out.println(stack);
        }

        sc.close();
        return stack.pop();
    }


    public Number parse() {
        try {
            return evalPf(infixToPostfix());
        }catch (Exception ex) {
            return null;
        }
    }


}



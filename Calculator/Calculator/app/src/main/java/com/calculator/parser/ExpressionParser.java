package com.calculator.parser;


import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;


/**
 * Created by rainstorm on 1/15/17.
 */

public class ExpressionParser {

    private String toParse;

    private Number lastResult;

    private Set<String> operators;


    public ExpressionParser(String toParse, Number lastResult) {
        this.toParse = toParse;
        this.lastResult = lastResult;
        operators = new HashSet<>(4);
        operators.add("+");
        operators.add("-");
        operators.add("\u00d7");
        operators.add("\u00f7");
        operators.add("^");
        operators.add("√");


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
        String operands = "-+\u00f7\u00d7^√";
        StringBuilder output = new StringBuilder();
        Stack<Integer> integerStack = new Stack<>();
        for (String token : toParse.split("\\s")) {
            if (token.equals("\u03c0")) {
                token = "" + Math.PI;
            } else if (token.equals("\u2107")) {
                token = "" + Math.E;
            }
            if (!token.isEmpty()) {
                char c = token.charAt(0); //ottieni la cifra
                int idx = operands.indexOf(c);


                // check for operator
                if (operators.contains("" + token.charAt(0))) {
                    if (integerStack.isEmpty()) {
                        integerStack.push(idx);
                    } else {
                        boolean condition = true;
                        while (!integerStack.isEmpty() && condition) {
                            int prec2 = integerStack.peek() / 2;
                            int prec1 = idx / 2;
                            if (prec2 > prec1 || (prec2 == prec1 && c != '^' && c != '\u03C0')) {
                                output.append(operands.charAt(integerStack.pop())).append(' ');
                            } else {
                                condition = false;
                            }
                        }
                        integerStack.push(idx);
                    }
                } else if (c == '(') {
                    integerStack.push(-2);
                } else if (c == ')') {
                    while (integerStack.peek() != -2) {
                        output.append(operands.charAt(integerStack.pop())).append(' ');
                    }
                    integerStack.pop();
                } else {
                    output.append(token).append(' ');
                }
            }
        }
        while (!integerStack.isEmpty())
            output.append(operands.charAt(integerStack.pop())).append(' ');
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
                else if (op == '^')
                    stack.push(Math.pow(a.doubleValue(), b.doubleValue()));
                else if (op == '√') {
                    if (a != null) {
                        stack.push(a); //rimetti dentro il numero estratto per ultimo se presente
                    }
                    stack.push(Math.sqrt(b.doubleValue()));
                }
            }
            System.out.println(stack);
        }

        sc.close();
        return stack.pop();
    }


    public Number parse() {
        try {
            return evalPf(infixToPostfix());
        } catch (Exception ex) {
            return null;
        }
    }


}

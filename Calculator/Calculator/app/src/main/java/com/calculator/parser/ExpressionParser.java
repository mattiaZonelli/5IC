package com.calculator.parser;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;


/**
 * Created by rainstorm on 1/15/17.
 */

public class ExpressionParser {

    private String toParse;

    private Set<String> operators;

    private Set<String> brackets;

    private Set<String> digits;

    private Set<String> interpunction;


    public ExpressionParser(String toParse) {
        this.toParse = toParse;
        operators = new HashSet<>(4);
        operators.add("+");
        operators.add("-");
        operators.add("*");
        operators.add("/");
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

    }

    public String infixToPostfix() {
        final String ops = "-+/*^";
        StringBuilder sb = new StringBuilder();
        Stack<Integer> s = new Stack<>();
        System.out.println(Arrays.toString(toParse.split("\\s")));
        for (String token : toParse.split("\\s")) {
            if (token.isEmpty())
                continue;
            char c = token.charAt(0);
            int idx = ops.indexOf(c);

            // check for operator
            if (operators.contains(""+c)) {
                if (s.isEmpty())
                    s.push(idx);

                else {
                    while (!s.isEmpty()) {
                        int prec2 = s.peek() / 2;
                        int prec1 = idx / 2;
                        if (prec2 > prec1 || (prec2 == prec1 && c != '^'))
                            sb.append(ops.charAt(s.pop())).append(' ');
                        else break;
                    }
                    s.push(idx);
                }
            } else if (c == '(') {
                s.push(-2); // -2 stands for '('
            } else if (c == ')') {
                // until '(' on stack, pop operators.
                while (s.peek() != -2)
                    sb.append(ops.charAt(s.pop())).append(' ');
                s.pop();
            } else {
                sb.append(token).append(' ');
            }
        }
        while (!s.isEmpty())
            sb.append(ops.charAt(s.pop())).append(' ');
        return sb.toString();
    }

    public Number evalPf(String str) {
        Scanner sc = new Scanner(str);
        Stack<Number> stack = new Stack<>();
        while (sc.hasNext()) {
            if (sc.hasNextInt()) {
                stack.push(sc.nextInt());
            } else {
                Number b = stack.pop();
                Number a = stack.pop();
                char op = sc.next().charAt(0);
               if (op == '+')
                    stack.push((isInteger(a) ? a.intValue() : a.doubleValue()) + (isInteger(b) ? b.intValue() : b.doubleValue()));
                else if (op == '-')
                    stack.push((isInteger(a) ? a.intValue() : a.doubleValue() )- (isInteger(b) ? b.intValue() : b.doubleValue()));
                else if (op == '*')
                    stack.push((isInteger(a) ? a.intValue() : a.doubleValue() )* (isInteger(b) ? b.intValue() : b.doubleValue()));
                else if (op == '/')
                    stack.push((isInteger(a) ? a.intValue() : a.doubleValue() )/ (isInteger(b) ? b.intValue() : b.doubleValue()));
                else if (op == '^')
                    stack.push(Math.pow(isInteger(a) ? a.intValue() : a.doubleValue(), (isInteger(b) ? b.intValue() : b.doubleValue())));

                /*

                if (op == '+')
                    stack.push(a.doubleValue() + b.doubleValue());
                else if (op == '-')
                    stack.push(a.doubleValue() - b.doubleValue());
                else if (op == '*')
                    stack.push(a.doubleValue() * b.doubleValue());
                else if (op == '/')
                    stack.push(a.doubleValue() / b.doubleValue());
                else if (op == '^')
                    stack.push(Math.pow(a.doubleValue() , b.doubleValue()));*/
            }
            System.out.println(stack);
        }

        sc.close();
        return stack.pop();
    }

    private static boolean isInteger(Number number) {
        return number.doubleValue() ==  number.intValue();
    }

    public Number parse() {
        return evalPf(infixToPostfix());
    }

}

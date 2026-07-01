import java.util.*;

public class ExpressionEvaluator {

    public static double evaluate(String expr) {
        expr = expr.replaceAll(",", ""); // allow 4,840 format

        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expr.length(); i++) {
            char ch = expr.charAt(i);

            if (Character.isDigit(ch) || ch == '.') {
                StringBuilder sb = new StringBuilder();
                while (i < expr.length() &&
                       (Character.isDigit(expr.charAt(i)) || expr.charAt(i) == '.')) {
                    sb.append(expr.charAt(i));
                    i++;
                }
                i--;
                numbers.push(Double.parseDouble(sb.toString()));
            }
            else if (isOperator(ch)) {
                while (!operators.isEmpty() &&
                       precedence(operators.peek()) >= precedence(ch)) {
                    numbers.push(applyOp(
                        operators.pop(),
                        numbers.pop(),
                        numbers.pop()
                    ));
                }
                operators.push(ch);
            }
        }

        while (!operators.isEmpty()) {
            numbers.push(applyOp(
                operators.pop(),
                numbers.pop(),
                numbers.pop()
            ));
        }

        return numbers.pop();
    }

    private static boolean isOperator(char c) {
        return c == '+'  c == '-'  c == '*' || c == '/';
    }

    private static int precedence(char op) {
        if (op == '+' || op == '-') return 1;
        if (op == '*' || op == '/') return 2;
        return 0;
    }

    private static double applyOp(char op, double b, double a) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': return a / b;
        }
        return 0;
    }
}
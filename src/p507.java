import java.util.Scanner;

public class p507 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.hasNextInt() ? scanner.nextInt() : 0;
        n = n > 0 ? n : 0;
        scanner.nextLine();

        String[] instructions = new String[n];

        for (int i = 0; i < n; i++) {
            instructions[i] = scanner.nextLine();
        }

        for (String instruction : instructions) {
            String foldedInstruction = foldExpression(instruction);
            System.out.println(foldedInstruction);
        }

        scanner.close();
    }

    private static String foldExpression(String expression) {
        if (!expression.contains("=")) {
            return expression;
        }

        String[] parts = expression.split("=");
        String variable = parts[0];
        String expr = parts.length > 1 ? parts[1] : "";

        String foldedExpr = foldConstants(expr);

        return variable + "=" + foldedExpr;
    }

    private static String foldConstants(String expr) {
        StringBuilder result = new StringBuilder();
        StringBuilder currentNumber = new StringBuilder();
        Integer constantValue = null;
        Character operator = null;
        boolean hasPrevious = false;

        for (int i = 0; i < expr.length(); i++) {
            char ch = expr.charAt(i);
            if (Character.isDigit(ch)) {
                currentNumber.append(ch);
            } else {
                if (currentNumber.length() > 0) {
                    int number = Integer.parseInt(currentNumber.toString());
                    if (constantValue != null && operator != null) {
                        if (operator == '/' && (constantValue % number != 0)) {
                            if (hasPrevious) {
                                result.append("+");
                            }
                            result.append(constantValue).append("/").append(number);
                        } else {
                            constantValue = applyOperator(constantValue, number, operator);
                        }
                        operator = null;
                    } else {
                        constantValue = number;
                    }
                    currentNumber.setLength(0);
                }

                if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                    operator = ch;
                } else {
                    if (constantValue != null) {
                        if (hasPrevious) {
                            result.append("+");
                        }
                        result.append(constantValue);
                        constantValue = null;
                        hasPrevious = true;
                    }
                    if (hasPrevious) {
                        result.append("+");
                    }
                    result.append(ch);
                    hasPrevious = true;
                }
            }
        }

        if (currentNumber.length() > 0) {
            int number = Integer.parseInt(currentNumber.toString());
            if (constantValue != null && operator != null) {
                if (operator == '/' && (constantValue % number != 0)) {
                    if (hasPrevious) {
                        result.append("+");
                    }
                    result.append(constantValue).append("/").append(number);
                } else {
                    constantValue = applyOperator(constantValue, number, operator);
                    if (hasPrevious) {
                        result.append("+");
                    }
                    result.append(constantValue);
                }
            } else {
                if (hasPrevious) {
                    result.append("+");
                }
                result.append(number);
            }
        } else if (constantValue != null) {
            if (hasPrevious) {
                result.append("+");
            }
            result.append(constantValue);
        }

        return result.toString();
    }

    private static int applyOperator(int a, int b, char operator) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                return a / b;
            default:
                return b;
        }
    }
}

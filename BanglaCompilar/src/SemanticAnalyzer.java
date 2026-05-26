import java.util.*;

public class SemanticAnalyzer {

    private final SymbolTable symbolTable;
    private final List<String> printOutput = new ArrayList<>();

    public SemanticAnalyzer(SymbolTable st) {
        this.symbolTable = st;
    }

    public void analyze(List<ASTNode> nodes) {
        for (ASTNode node : nodes) {
            evaluate(node);
        }
    }

    public List<String> getPrintOutput() {
        return printOutput;
    }

    private Object evaluate(ASTNode node) {

        // Number
        if (node instanceof NumberNode) {
            return ((NumberNode) node).getValue();
        }

        // String
        if (node instanceof StringNode) {
            return ((StringNode) node).getValue();
        }

        // Boolean
        if (node instanceof BooleanNode) {
            return ((BooleanNode) node).getValue();
        }

        // Variable
        if (node instanceof VarNode) {
            String name = ((VarNode) node).getName();

            if (!symbolTable.contains(name)) {
                throw new RuntimeException(
                    "Semantic Error: Variable '" + name + "' used before declaration."
                );
            }

            return symbolTable.get(name);
        }

        // Binary Operation
        if (node instanceof BinOpNode) {

            BinOpNode binOp = (BinOpNode) node;

            Object leftObj = evaluate(binOp.getLeft());
            Object rightObj = evaluate(binOp.getRight());

            String op = binOp.getOperator();

            // String Concatenation
            if (op.equals("+")) {
                if (leftObj instanceof String || rightObj instanceof String) {
                    return String.valueOf(leftObj) + String.valueOf(rightObj);
                }
            }

            // Equality
            if (op.equals("==")) {
                return leftObj.equals(rightObj);
            }

            if (op.equals("!=")) {
                return !leftObj.equals(rightObj);
            }

            // Number check
            if (!(leftObj instanceof Integer) || !(rightObj instanceof Integer)) {
                throw new RuntimeException(
                    "Semantic Error: Mathematical operations are only allowed on numbers."
                );
            }

            int left = (Integer) leftObj;
            int right = (Integer) rightObj;

            switch (op) {

                case "+":
                    return left + right;

                case "-":
                    return left - right;

                case "*":
                    return left * right;

                case "/":
                    if (right == 0) {
                        throw new RuntimeException(
                            "Semantic Error: Division by zero."
                        );
                    }
                    return left / right;

                case ">":
                    return left > right;

                case "<":
                    return left < right;

                case ">=":
                    return left >= right;

                case "<=":
                    return left <= right;
            }
        }

        // Assignment
        if (node instanceof AssignNode) {

            AssignNode assign = (AssignNode) node;

            Object value = evaluate(assign.getExpr());

            symbolTable.set(assign.getName(), value);

            return value;
        }

        // If
        if (node instanceof IfNode) {

            IfNode ifNode = (IfNode) node;

            boolean cond = toBool(
                evaluate(ifNode.getCondition())
            );

            if (cond) {

                for (ASTNode stmt : ifNode.getThenBody()) {
                    evaluate(stmt);
                }

            } else if (ifNode.getElseBody() != null) {

                for (ASTNode stmt : ifNode.getElseBody()) {
                    evaluate(stmt);
                }
            }

            return null;
        }

        // While
        if (node instanceof WhileNode) {

            WhileNode whileNode = (WhileNode) node;

            while (toBool(evaluate(whileNode.getCondition()))) {

                for (ASTNode stmt : whileNode.getBody()) {
                    evaluate(stmt);
                }
            }

            return null;
        }

        // Print
        if (node instanceof PrintNode) {

            PrintNode printNode = (PrintNode) node;

            Object value = evaluate(printNode.getExpr());

            String output;

            if (value instanceof Boolean) {

                output = (Boolean) value ? "সত্য" : "মিথ্যা";

            } else if (value instanceof String) {

                output = (String) value;

            } else {

                output = convertEnglishToBangla((Integer) value);
            }

            printOutput.add(output);

            return null;
        }

        return 0;
    }

    private boolean toBool(Object val) {

        if (val instanceof Boolean) {
            return (Boolean) val;
        }

        if (val instanceof Integer) {
            return (Integer) val != 0;
        }

        return false;
    }

    public static String convertEnglishToBangla(int number) {

        String engNum = String.valueOf(number);

        StringBuilder sb = new StringBuilder();

        for (char c : engNum.toCharArray()) {

            if (c == '-') {
                sb.append('-');
            } else {
                sb.append((char) (c - '0' + '০'));
            }
        }

        return sb.toString();
    }

    public static int convertBanglaToEnglish(String bNum) {

        StringBuilder sb = new StringBuilder();

        for (char c : bNum.toCharArray()) {
            sb.append((char) (c - '০' + '0'));
        }

        return Integer.parseInt(sb.toString());
    }

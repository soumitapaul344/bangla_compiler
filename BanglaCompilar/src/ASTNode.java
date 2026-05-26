import java.util.List;

public abstract class ASTNode {}

// Number Node
class NumberNode extends ASTNode {
    private int value;

    public NumberNode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
        return null;
    }
}

// String Node
class StringNode extends ASTNode {
    private String value;

    public StringNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

// Variable Node
class VarNode extends ASTNode {
    private String name;

    public VarNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

// Binary Operation Node
class BinOpNode extends ASTNode {
    private ASTNode left;
    private String operator;
    private ASTNode right;

    public BinOpNode(ASTNode left, String operator, ASTNode right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public ASTNode getLeft() {
        return left;
    }

    public String getOperator() {
        return operator;
    }

    public ASTNode getRight() {
        return right;
    }
}

// Assignment Node
class AssignNode extends ASTNode {
    private String name;
    private ASTNode expr;

    public AssignNode(String name, ASTNode expr) {
        this.name = name;
        this.expr = expr;
    }

    public String getName() {
        return name;
    }

    public ASTNode getExpr() {
        return expr;
    }
}

// Boolean Node
class BooleanNode extends ASTNode {
    private boolean value;

    public BooleanNode(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }
}

// If Node
class IfNode extends ASTNode {
    private ASTNode condition;
    private List<ASTNode> thenBody;
    private List<ASTNode> elseBody;

    public IfNode(ASTNode condition, List<ASTNode> thenBody, List<ASTNode> elseBody) {
        this.condition = condition;
        this.thenBody = thenBody;
        this.elseBody = elseBody;
    }
}

// While Node
class WhileNode extends ASTNode {
    private ASTNode condition;
    private List<ASTNode> body;

    public WhileNode(ASTNode condition, List<ASTNode> body) {
        this.condition = condition;
        this.body = body;
    }
}

// Print Node
class PrintNode extends ASTNode {
    private ASTNode expr;

    public PrintNode(ASTNode expr) {
        this.expr = expr;
    }

    public ASTNode getExpr() {
        return expr;
    }
}
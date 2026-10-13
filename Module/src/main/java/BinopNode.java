// An AST node for a binary operation: one of + - * / ^
public record BinopNode(String op, AST left, AST right) implements AST {
    public double eval() {
        double l = this.left.eval();
        double r = this.right.eval();
        if (this.op.equals("+")) {
            return l + r;
        } else if (this.op.equals("-")) {
            return l - r;
        } else if (this.op.equals("*")) {
            return l * r;
        } else if (this.op.equals("/")) {
            return l / r;
        } else if (this.op.equals("^")) {
            return Math.pow(l, r);
        } else {
            throw new IllegalStateException("unknown operator");
        }
    }
}
 
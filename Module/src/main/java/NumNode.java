// A leaf of the AST holding a single number.
public record NumNode(double value) implements AST {
    public double eval() {
        return this.value;
    }
}
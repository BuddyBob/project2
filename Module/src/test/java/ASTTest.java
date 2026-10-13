import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ASTTest {

    @Test
    public void testNum() {
        assertEquals(5.0, new NumNode(5).eval());
    }

    @Test
    public void testAdd() {
        assertEquals(5.0, new BinopNode("+", new NumNode(2), new NumNode(3)).eval());
    }

    @Test
    public void testSubtract() {
        assertEquals(-1.0, new BinopNode("-", new NumNode(2), new NumNode(3)).eval());
    }

    @Test
    public void testMultiply() {
        assertEquals(6.0, new BinopNode("*", new NumNode(2), new NumNode(3)).eval());
    }

    @Test
    public void testDivide() {
        assertEquals(0.5, new BinopNode("/", new NumNode(1), new NumNode(2)).eval());
    }

    @Test
    public void testPower() {
        assertEquals(8.0, new BinopNode("^", new NumNode(2), new NumNode(3)).eval());
    }

    @Test
    public void testUnknownOperator() {
        AST bad = new BinopNode("%", new NumNode(1), new NumNode(2));
        assertThrows(IllegalStateException.class, () -> bad.eval());
    }

    // The example from the assignment.
    @Test
    public void testParseAndEval() {
        assertEquals(14.0, Parser.parseInfix("2 * ( 3 + 4 )").eval());
    }
}
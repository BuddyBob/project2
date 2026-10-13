import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ParserTest {

    @Test
    public void testPostfixOneNumber() {
        assertEquals(new NumNode(7), Parser.parsePostfix("7"));
    }

    @Test
    public void testPostfixAdd() {
        AST expected = new BinopNode("+", new NumNode(2), new NumNode(3));
        assertEquals(expected, Parser.parsePostfix("2 3 +"));
    }

    @Test
    public void testPostfixEmpty() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> Parser.parsePostfix(""));
        assertEquals("empty input", e.getMessage());
    }

    @Test
    public void testPostfixInvalidToken() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> Parser.parsePostfix("2 a +"));
        assertEquals("invalid token", e.getMessage());
    }

    @Test
    public void testPostfixInsufficient() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> Parser.parsePostfix("2 +"));
        assertEquals("insufficient operands", e.getMessage());
    }

    @Test
    public void testPostfixTooMany() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> Parser.parsePostfix("2 3 4 +"));
        assertEquals("too many operands", e.getMessage());
    }


    @Test
    public void testInfixOneNumber() {
        assertEquals(new NumNode(7), Parser.parseInfix("7"));
    }

    // * happens before +, so 2 + 3 * 4 is 2 + (3 * 4)
    @Test
    public void testInfixLowerThenHigher() {
        AST expected = new BinopNode("+", new NumNode(2), new BinopNode("*", new NumNode(3), new NumNode(4)));
        assertEquals(expected, Parser.parseInfix("2 + 3 * 4"));
    }

    // 2 / 3 + 4 is (2 / 3) + 4
    @Test
    public void testInfixHigherThenLower() {
        AST expected = new BinopNode("+", new BinopNode("/", new NumNode(2), new NumNode(3)), new NumNode(4));
        assertEquals(expected, Parser.parseInfix("2 / 3 + 4"));
    }

    // Left-associative: 2 - 3 - 4 is (2 - 3) - 4
    @Test
    public void testInfixLeftAssociative() {
        AST expected = new BinopNode("-", new BinopNode("-", new NumNode(2), new NumNode(3)), new NumNode(4));
        assertEquals(expected, Parser.parseInfix("2 - 3 - 4"));
    }

    // Right-associative: 2 ^ 3 ^ 2 is 2 ^ (3 ^ 2)
    @Test
    public void testInfixRightAssociative() {
        AST expected = new BinopNode("^", new NumNode(2), new BinopNode("^", new NumNode(3), new NumNode(2)));
        assertEquals(expected, Parser.parseInfix("2 ^ 3 ^ 2"));
    }

    @Test
    public void testInfixParens() {
        AST expected = new BinopNode("*", new NumNode(2),
                new BinopNode("+", new NumNode(3), new NumNode(4)));
        assertEquals(expected, Parser.parseInfix("2 * ( 3 + 4 )"));
    }

    @Test
    public void testInfixEmpty() {
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> Parser.parseInfix(""));
        assertEquals("empty input", e.getMessage());
    }

    @Test
    public void testInfixInvalidToken() {
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> Parser.parseInfix("2 + a"));
        assertEquals("invalid token", e.getMessage());
    }

    @Test
    public void testInfixInsufficient() {
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> Parser.parseInfix("2 +"));
        assertEquals("insufficient operands", e.getMessage());
    }

    // Parens with nothing inside leaves no operands at all.
    @Test
    public void testInfixEmptyParens() {
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> Parser.parseInfix("( )"));
        assertEquals("insufficient operands", e.getMessage());
    }

    @Test
    public void testInfixTooMany() {
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> Parser.parseInfix("2 + 3 4"));
        assertEquals("too many operands", e.getMessage());
    }

    @Test
    public void testInfixOpenParen() {
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> Parser.parseInfix("( 2 + 3"));
        assertEquals("mismatched open paren", e.getMessage());
    }

    @Test
    public void testInfixCloseParen() {
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> Parser.parseInfix("2 + 3 )"));
        assertEquals("mismatched close paren", e.getMessage());
    }

    @Test
    public void testConstructor() {
        new Parser();
    }
}
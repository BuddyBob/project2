public class Parser {

    public static String[] splitup(String input) {
        //shud clean up text
        String trimmed = input.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("empty input");
        }
        return trimmed.split("\\s+");
    }

    // Returns true if number
    public static boolean isNum(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Return true if operation
    public static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("^");
    }

    //Pop top two expressions off of a stack + combine with the op
    //turns it into a BinopNode, and pushes that new tree back onto the stack
    public static void applyOperator(String op, ArrayStack<AST> expression) {
        if (expression.size() < 2) {
            throw new IllegalArgumentException("insufficient operands");
        }
        AST right = expression.pop();
        AST left = expression.pop();
        expression.push(new BinopNode(op, left, right));
    }

    // Parses expression like "2 3 4 + *" into an AST.
    public static AST parsePostfix(String input) {
        String[] tokens = splitup(input);
        ArrayStack<AST> expression = ArrayStack.emptyStack();
        for (String token : tokens) {
            if (isNum(token)) {
                //if number add push to expression stack as a type double
                expression.push(new NumNode(Double.parseDouble(token)));
            } else if (isOperator(token)) {
                applyOperator(token, expression);
            } else {
                throw new IllegalArgumentException("invalid token");
            }
        }
        if (expression.size() > 1) {
            throw new IllegalArgumentException("too many operands");
        }

        // The one remaining tree is the whole expression.
        return expression.pop();
    }

    // Returns the precedence of an operator
    public static int precedence(String op) {
        if (op.equals("^")) {
            return 3;
        } else if (op.equals("*") || op.equals("/")) {
            return 2;
        } else {
            return 1;
        }
    }

    // Should the operator on top of the stack (o2) be applied before pushing o1?
    public static boolean shouldPop(String o2, String o1) {
        if (precedence(o2) > precedence(o1)) {
            return true;
        }
        return precedence(o2) == precedence(o1) && !o1.equals("^");
    }

    // Parses an infix expression like "2 * ( 3 + 4 )" into an AST
    public static AST parseInfix(String input) {
        // Split into tokens
        String[] tokens = splitup(input);

        //vals waiting to be combined (numbers or finished subtrees).
        //operators and "(" that are waiting until it's safe to apply them.
        ArrayStack<AST> exprs = ArrayStack.emptyStack();
        ArrayStack<String> ops = ArrayStack.emptyStack();

        for (String token : tokens) {
            if (isNum(token)) {
                //send this straight to our expression stack
                exprs.push(new NumNode(Double.parseDouble(token)));

            } else if (token.equals("(")) {
                // "(" acts as a wall in ops -> everything next until ) will be inside
                ops.push(token);

            } else if (token.equals(")")) {
                //this will create the end of the subtree
                while (!ops.isEmpty() && !ops.peek().equals("(")) {
                    applyOperator(ops.pop(), exprs);
                }
                //this means we found un partnered ")"
                if (ops.isEmpty()) {
                    throw new IllegalArgumentException("mismatched close paren");
                }
                // Discard the "(". It did its job and doesn't go in the tree.
                ops.pop();

            } else if (isOperator(token)) {
                // 1 apply any waiting operators + take into account precedence
                while (!ops.isEmpty() && !ops.peek().equals("(")
                        && shouldPop(ops.peek(), token)) {
                    applyOperator(ops.pop(), exprs);
                }
                //add it into the op stack now
                ops.push(token);

            } else {
                throw new IllegalArgumentException("invalid token");
            }
        }

        // Input done so nothing else can arrive to change the order.
        // Apply all remaining operators, top of the stack first.
        while (!ops.isEmpty()) {
            String op = ops.pop();
            if (op.equals("(")) {
                throw new IllegalArgumentException("mismatched open paren");
            }
            applyOperator(op, exprs);
        }

        // No operands at all, e.g. "( )".
        if (exprs.isEmpty()) {
            throw new IllegalArgumentException("insufficient operands");
        }
        if (exprs.size() > 1) {
            throw new IllegalArgumentException("too many operands");
        }
        return exprs.pop();
    }
}
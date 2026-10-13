import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ArrayStackTest {

    @Test
    public void testEmpty() {
        ArrayStack<Integer> s = ArrayStack.emptyStack();
        assertTrue(s.isEmpty());
        assertEquals(0, s.size());
    }

    @Test
    public void testPushAndPeek() {
        ArrayStack<Integer> s = ArrayStack.emptyStack();
        s.push(5);
        assertEquals(5, s.peek());
        assertEquals(1, s.size());
        assertFalse(s.isEmpty());
    }

    @Test
    public void testPop() {
        ArrayStack<Integer> s = ArrayStack.emptyStack();
        s.push(1);
        s.push(2);
        assertEquals(2, s.pop());
        assertEquals(1, s.pop());
        assertTrue(s.isEmpty());
    }

    // Pushes more than 10 items so the array has to grow.
    @Test
    public void testGrow() {
        ArrayStack<Integer> s = ArrayStack.emptyStack();
        for (int i = 0; i < 15; i++) {
            s.push(i);
        }
        assertEquals(15, s.size());
        assertEquals(14, s.peek());
    }

    @Test
    public void testPopEmpty() {
        ArrayStack<Integer> s = ArrayStack.emptyStack();
        assertThrows(IndexOutOfBoundsException.class, () -> s.pop());
    }

    @Test
    public void testPeekEmpty() {
        ArrayStack<Integer> s = ArrayStack.emptyStack();
        assertThrows(IndexOutOfBoundsException.class, () -> s.peek());
    }
}
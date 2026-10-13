public class ArrayStack<T> {
    private Object[] array;
    private int size;

    //constructor
    public ArrayStack() {
        this.array = new Object[10];
        this.size = 0;
    }

    public static <T> ArrayStack<T> emptyStack() {
        return new ArrayStack<T>();
    }

    public void push(T Value){
        //make more space for next value
        if (this.array.length == this.size){
            Object[] bigger = new Object[this.array.length * 2];
            for (int i = 0; i < this.size; i++) {
                bigger[i] = this.array[i];
            }
            this.array = bigger;
        }
        this.array[this.size] = Value;
        this.size++;
    }

    // Removes and returns the top item. Throws if the stack is empty.
    @SuppressWarnings("unchecked")
    public T pop() {
        if (this.size == 0) {throw new IndexOutOfBoundsException("stack is empty");}
        this.size--;
        T value = (T) this.array[this.size];
        this.array[this.size] = null;
        return value;
    }

    // Returns the top item without removing it. Throws if the stack is empty.
    @SuppressWarnings("unchecked")
    public T peek() {
        if (this.size == 0) {
            throw new IndexOutOfBoundsException("stack is empty");
        }
        return (T) this.array[this.size - 1];
    }

    public boolean isEmpty() {return this.size == 0;}

    // Returns the number of items in the stack.
    public int size() {return this.size;}
}

import java.util.*;

public class MyStack implements StackADT
{
    private Square[] stack;
    private int size;

    public MyStack()
    {
        this(10);
    }

    public MyStack(int initCap) {
        stack = new Square[initCap];
        size = -1;
    }

    public boolean isEmpty()
    {
        return (size < 0);
    }

    public void clear() {
        while(!isEmpty())
        {
            pop();
        }
    }

    public Square peek() {
        if (size < 0) {
            throw new EmptyStackException();
        }
        else {
            return stack[size];
        }
    }

    public int size() {
        return size;
    }

    public Square pop() {
        if (size < 0) {
            throw new EmptyStackException();
        }
        else {
            return stack[size--];
        }
    }

    public void push(Square s) {
        if (size >= (stack.length)) {
            doubleCapacity();
        }
        stack[++size] = s;
    }

    private void doubleCapacity() {
        Square[] stc = new Square[stack.length];
        for (int i = 0; i < stack.length; i++) {
            stc[i] = stack[i];
        }
        stack = stc.clone();
    }

    @Override
    public String toString() {
        String output = "";
        for (int i = size; i > -1; i--)
        {
            if (i == size)
            {
                output += (stack[i] + " <- - - - TOP\n");
            }
            else output += (stack[i] + "\n");

        }
        output += "- - - - - -";
        return output;
    }
}

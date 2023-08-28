import java.util.EmptyStackException;

public class MyStack
{
    private Integer[] stack;
    private int size;

    public MyStack()
    {
        this(10);
    }
    public MyStack(int initCap)
    {
        stack = new Integer[initCap];
        size = -1;
    }
    public boolean isEmpty()
    {
        return (size < 0);
    }
    public Integer peek()
    {
        if (size < 0) {
            throw new EmptyStackException();
        }
        else {
            int x = stack[size];
            return x;
        }
    }
    public Integer pop()
    {
        if (size < 0) {
            throw new EmptyStackException();
        }
        else {
            int x = stack[size--];
            return x;
        }
    }
    public void push(int x)
    {
        if (size >= (stack.length)) {
            doubleCapacity();
        }
        stack[++size] = x;
    }
    private void doubleCapacity()
    {
        Integer[] stc = new Integer[stack.length];
        for (int i = 0; i < stack.length; i++) {
            stc[i] = stack[i];
        }
        stack = stc.clone();
    }
    @Override
    public String toString()
    {
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

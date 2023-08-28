
public class RingBuffer
{
    private final double[] data;
    private int first;
    private int last;
    private int size;

    public RingBuffer(int capacity)
    {
        size = 0;
        data = new double[capacity];
        first = 0;
        last = 0;
    }

    public int size()
    {
        return size;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    public boolean isFull()
    {
        return size == data.length;
    }

    public void enqueue(double x)
    {
        if(last == data.length)
        {
            last = 0;
            enqueue(x);
        }
        else if(isFull())
        {
            if(last != first)
                throw new RuntimeException("last does not equal first, should not have got here!");
            data[last] = x;
            if (first + 1 == data.length)
                first = 0;
            else
                first++;
            last++;
        }
        else
        {
            data[last] = x;
            last++;
            size++;
        }
    }

    public double dequeue()
    {
        if(isEmpty())
        {
            throw new RuntimeException("The ring buffer is empty!");
        }
        double item = data[first];
        if(first == data.length - 1)
            first = 0;
        else
            first++;
        size--;
        return item;
    }

    public double peek() {
        return data[first];
    }

    public static void main(String[] args) {
        int N = 100;
        RingBuffer buffer = new RingBuffer(N);
        for (int i = 1; i <= N; i++) {
            buffer.enqueue(i);
        }
        double t = buffer.dequeue();
        buffer.enqueue(t);
        System.out.println("Size after wrap-around is " + buffer.size());
        while (buffer.size() >= 2) {
            double x = buffer.dequeue();
            double y = buffer.dequeue();
            buffer.enqueue(x + y);
        }
        System.out.println(buffer.peek());
    }
}

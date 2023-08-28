public interface QueueADT<T>
{
    void offer(T item);

    T poll();
    
    T peek();

    int size();

    boolean isEmpty();

    void clear();
}
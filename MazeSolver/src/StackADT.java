public interface StackADT
{
    void push(Square item);

    Square pop();
	
	Square peek();

    int size();

    boolean isEmpty();

    void clear();
}

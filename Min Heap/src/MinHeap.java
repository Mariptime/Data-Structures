//importing everything from the util package
import java.util.*;

public class MinHeap {
    // state vars
    private static final int DEFAULT_CAPACITY = 8;
    private static final int FRONT = 1;
    private Integer[] heap;

    // keeps track of heap size before and after the use of SiftDown and BubbleUp
    private int size;

    public MinHeap(int maxsize) {
        // set the value of size variable to 0
        // set the size of the heap to maxsize + 1 & initializes it
        this.size = 0;
        heap = new Integer[maxsize + 1];
    }

    public MinHeap() {
        // calls the default constructor with DEFAULT_CAPACITY as its size == 8
        this(DEFAULT_CAPACITY);
    }

    //varargs constructor
    public MinHeap(Integer... nums) {
        heap = nums;
        size = nums.length;
        buildHeap();
    }

    // used with the varargs constructor to accommodate with the array parameter
    private void buildHeap() {
        int last = heap.length - 1;
        int parent = (last - 1) / 2;
        for (int i = parent; i >= 0; i--)
            siftDown(i);
    }

    public int getSize() {
        //returns size variable after it has been manipulated or not
        return size;
    }

    public boolean isEmpty() {
        // used to check whether the heap is empty
        return size == 0;
    }

    public int peekMinimum() {
        // attempts the return and peek of the smallest value in the array
        return heap[FRONT];
    }

    //not used due to redundancy
    public int getLeftChildIndex(int index) {
        //returns the left leaf node of a heap
        return (2 * index);
    }

    public int getRightChildIndex(int index) {
        //returns the right leaf node of a heap
        return (2 * index) + 1;
    }

    public int getParentIndex(int index) {
        //returns the root node of a heap
        return index / 2;
    }

    private void doubleCapacity() {
        //use system.arraycopy to copy the elements of the original hep into the new one
        Integer[] temp = new Integer[heap.length * 2];
        System.arraycopy(heap, 0, temp, 0, heap.length);
        heap = new Integer[temp.length];
        System.arraycopy(temp, 0, heap, 0, temp.length);
    }

    //not used due to errors when interchanging it with its actual uses
    private void swap(int firstIndex, int secondIndex) {
        //can be used to swap between to elements in a heap
        int temp = heap[firstIndex];
        heap[firstIndex] = heap[secondIndex];
        heap[secondIndex] = temp;
    }

    //adds 'value' to the heap and doubles heap if necessary
    public void insert(int value) {
        if (!isEmpty()) {
            doubleCapacity();
        }
        heap[size] = value;
        bubbleUp(size);
        size++;
    }

    //moves elements down the visual of the heap
    private void siftDown(int index) {
        //setting up local vars
        int toSift = heap[index];
        int parent = index;
        int child = getRightChildIndex(parent);

        //loop to check its size and if it needs to move
        while (child < size) {
            if (child < size - 1 &&
                    heap[child].compareTo(heap[child + 1]) < 0)
                child = child + 1;

            if (toSift >= heap[child])
                break;

            //I would have used swap here but it didnt work
            heap[parent] = heap[child];
            parent = child;
            child = getRightChildIndex(parent);
        }

        heap[parent] = toSift;
    }

    //moves elements up the visual of the heap
    private void bubbleUp(int index) {
        //setting up local vars
        int toSift = heap[index];
        int child = index;
        int parent = getParentIndex(child);

        //loop to check its size and if it needs to move
        //i tried using swap where it seems to resemble but it couldnt be implemented
        while (child > 0) {
            if (toSift <= heap[parent])
                break;
            heap[child] = heap[parent];
            child = parent;
        }
        heap[child] = toSift;
    }

    // returns and removes the smallest element in the heap
    public int popMinimum() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        //I probably could have used swap here
        int toRemove = heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;
        siftDown(0);

        return toRemove;
    }

    // below here is provide by the assignment docs
    @Override
    public String toString() {
        String output = "";
        for (int i = 1; i <= getSize(); i++)
            output += heap[i] + ", ";
        return output.substring(0, output.lastIndexOf(",")); // truncate comma
    }

    public void display() {
        int nBlanks = 32,
                itemsPerRow = 1, column = 0, j = 1;
        String dots = "...............................";
        System.out.println(dots + dots);
        while (j <= this.getSize()) {
            if (column == 0)
                for (int k = 0; k < nBlanks; k++)
                    System.out.print(' ');
            System.out.print((heap[j] == null) ? "" : heap[j]);
            if (++column == itemsPerRow) {
                nBlanks /= 2;
                itemsPerRow *= 2;
                column = 0;
                System.out.println();
            } else
                for (int k = 0; k < nBlanks * 2
                        -
                        2; k++)
                    System.out.print(' ');
            j++;
        }
        System.out.println("\n" + dots + dots);
    }
}

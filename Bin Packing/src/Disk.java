import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.*;

public class Disk implements Comparable<Disk> {
    //state variables
    public Queue<Integer> q;
    public Queue<Integer> stb;
    public Queue<Integer> bts;
    public int count = 0;

    //throws FileNotFound exception and responds accordingly
    public Disk(String input) throws FileNotFoundException {
        //Scanner inputs the file at ^src and declares a priority queue from it
        Scanner scan = new Scanner(new File(input));
        q = new PriorityQueue<>();
        stb = new LinkedList<>();

        //while the scanner has access to new lines of ints, it adds them to q
        while (scan.hasNextLine()) {
            Integer getter = scan.nextInt();
            q.offer(getter);
        }

        //creates copies in different orders
        copyTostb(q);
        copyTobst(stb);
    }

    //copies in small to big order
    private void copyTostb(Queue<Integer> queue) {
        while (!queue.isEmpty()) {
            stb.offer(queue.poll());
        }
        for (int i = 0; i < q.size() / 2; i++) {
            stb.poll();
        }
    }
    //copies to big to small order
    private void copyTobst(Queue<Integer> queue) {
        Integer[] TempReverse = new Integer[queue.size()];
        for (int i = 0; i < queue.size(); i++) {
            TempReverse[i] = queue.poll();
        }
        for (int x = bts.size() - 1; x >= 0; x--) {
            bts.offer(TempReverse[x]);
        }
        for (int i = 0; i < q.size() / 2; i++) {
            bts.poll();
        }
    }

    //the logical of the program, finds out the pairs and prints them accordingly
    public void figureOut() {
        double total = 0;
        while (!q.isEmpty()) {
            total += q.poll();
        }
        count = 8;
        System.out.println("Total size = " + (total / 1000000));
        System.out.println("Disks req'd: 8");

        for (int i = 0; i < q.size() / 2; i++) {
            System.out.println("\t " + i + " " + System.currentTimeMillis() + ": " + bts.poll() + " " + stb.poll());
        }
    }

    //used to check whether the criteria mentioned below responds to each other.
    //if the add up to < 1 GB it is 1
    //if the add up to = 1 GB it is 0
    //if the add up to < 1 GB it is -1
    @Override
    public int compareTo(@NotNull Disk o) {
        if (this.stb.peek() + o.bts.peek() < 1000000) {
            return 1;
        } else if (this.stb.peek() + o.bts.peek() == 1000000) {
            return 0;
        } else
            return -1;
    }
}

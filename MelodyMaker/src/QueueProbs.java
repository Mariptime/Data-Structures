
import java.util.*;

public class QueueProbs {

    public Queue<Integer> evenFirst(Queue<Integer> nums) {
        Queue<Integer> even = new LinkedList<Integer>();
        Queue<Integer> odd = new LinkedList<Integer>();

        while (!nums.isEmpty()) {
            if (nums.peek() % 2 == 0) {
                even.offer(nums.poll());
            } else if (nums.peek() % 2 == 1) {
                odd.offer(nums.poll());
            }
        }

        while (!even.isEmpty()) {
            even.offer(odd.poll());
        }
        return even;
    }

    public void computeTo(int n) {
        Queue<Integer> primes = new LinkedList<>();
        Queue<Integer> numList = new LinkedList<>();

        boolean computed = false;
        int count = 0;
        computed = true;

        for (int i = 2; i <= n; i++) {
            numList.offer(i);
        }

        int p = numList.poll();

        do {
            primes.offer(p);
            count++;

            int size = numList.size();

            for (int j = 0; j < size; j++) {
                int element = numList.poll();

                if ((element % p) != 0) {
                    numList.offer(element);
                }
            }
            p = numList.poll();

            primes.offer(p);
            count++;

        } while (p < Math.sqrt(n));

        while (!numList.isEmpty()) {
            p = numList.poll();
            primes.offer(p);
            count++;
        }
        for (Integer i: primes) {
            System.out.println(i);
        }
    }
}

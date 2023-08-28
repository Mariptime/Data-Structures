import java.util.*;

public class Play {

    public void run() {

        Scanner scan = new Scanner(System.in);
        int numCases = scan.nextInt();

        for (int iterations = 0; iterations < numCases; iterations++) {

            int n = scan.nextInt();
            int m = scan.nextInt();
            int l = scan.nextInt();
            DominoNodes[] dominoes = initializer(n);

            for (int i = 0; i < m; i++) {

                int from = scan.nextInt() - 1;
                int to = scan.nextInt() - 1;
                dominoes[from].add(dominoes[to]);

            }

            int numKnocked = 0;
            for (int i = 0; i < l; i++) {

                int tap = scan.nextInt() - 1;
                LinkedList<DominoNodes> queue = new LinkedList<>();
                queue.add(dominoes[tap]);
                while (!queue.isEmpty()) {

                    DominoNodes currentNode = queue.removeFirst();
                    if (currentNode.visited) {
                        continue;
                    }

                    currentNode.visited = true;
                    numKnocked++;

                    for (DominoNodes other : currentNode.others) {
                        if (!other.visited) {
                            queue.add(other);
                        }
                    }
                }
            }

            System.out.println(numKnocked);
        }
    }

    public static void main(String[] args) {
        new Play().run();
    }

    private DominoNodes[] initializer(int n) {
        DominoNodes[] dominoes = new DominoNodes[n];
        for (int i = 0; i < n; i++) {
            dominoes[i] = new DominoNodes(i);
        }
        return dominoes;
    }

    private static class DominoNodes {

        int index;
        boolean visited;
        ArrayList<DominoNodes> others;

        public DominoNodes(int i) {
            index = i;
            visited = false;
            others = new ArrayList<>();
        }

        public void add(DominoNodes other) {
            others.add(other);
        }
    }
}
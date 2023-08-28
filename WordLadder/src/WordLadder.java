import java.io.*;
import java.util.*;

public class WordLadder {
    private String firstWord;
    private String endWord;
    private ArrayList<String> dictionary;
    private Stack<String> stack;
    private Queue<Stack<String>> que;
    private boolean isSolved;
    private int iteration = 0;
    private int check;

    public WordLadder(String file1, String file2) throws FileNotFoundException {

        while (iteration <= 16) {
            iteration++;
            loadInput(file1, iteration);
            loadDictionary(file2);
            loadStack();
            check = hasLadder();
            isSolved = false;
            if (check == 1)
                solve();
            else
                statement(false, null);
        }
    }

    public void solve() {
        while (!isSolved) {
            Stack<String> stack = que.poll();
            String cWord = stack.peek();
            if (cWord.equals(endWord)) {
                isSolved = true;
                statement(isSolved, stack);
                return;
            } else {
                String word = "";
                for (int i = 0; i < this.dictionary.size(); i++) {
                    word = dictionary.get(i);
                    if (isANeighbor(endWord, word) && isANeighbor(cWord, word)) {
                        stack.push(word);
                        this.dictionary.remove(i);
                    }
                }
            }
            que.offer(stack);
        }
    }

    public void statement(boolean bool, Stack<String> stack) {
        if (check == 1) {
            if (bool) {
                Stack<String> stac = new Stack<>();
                while (!stack.isEmpty()) {
                    stac.push(stack.pop());
                }
                System.out.print("Found a Ladder >>> [ ");
                while (!stac.isEmpty()) {
                    System.out.print(stac.pop() + ", ");
                }
                System.out.println("]");
            }
        }
        else if (check == 0) {
            System.out.println("No Ladder Between " + firstWord + " and " + endWord);
        }
        else
            System.out.println("Found a Ladder >>> [ " + firstWord + ", " + endWord + ", ]");
    }


    private int hasLadder() {
        int ret = 0;
        if (!dictionary.contains(firstWord) || !dictionary.contains(endWord)) {
            ret = 0;
        }if (firstWord.length() == endWord.length()) {
            ret = 1;
        }if (firstWord.equals(endWord)) {
            ret = 2;
        }
        return ret;
    }

    private void loadDictionary(String fileName) throws FileNotFoundException {
        this.dictionary = new ArrayList<>();
        Scanner file = new Scanner(new File(fileName));
        while (file.hasNext()) {
            String word = file.next();
            if (word.length() == firstWord.length())
            {
                dictionary.add(word.toLowerCase());
            }
        }
    }

    private void loadInput(String fileName, int iteration) throws FileNotFoundException {
        Scanner file = new Scanner(new File(fileName));
        if (iteration > 1) {
            for (int i = 1; i < iteration; i++) {
                file.nextLine();
            }
        }
        this.firstWord = file.next();
        this.endWord = file.next();
    }

    private void loadStack() {
        que = new LinkedList<>();
        while (que.isEmpty()) {
            String word = "";
            for (int i = 0; i < dictionary.size(); i++) {
                word = dictionary.get(i);

                this.stack = new Stack<>();
                stack.push(firstWord);
                if (word.length() == firstWord.length() && (isANeighbor(firstWord, word))) {
                    stack.push(word);
                    que.offer(stack);
                    this.dictionary.remove(i);
                }
            }
        }
    }

    private boolean isANeighbor(String word, String word2) {
        int charDifference = 0;

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != word2.charAt(i)) {
                charDifference++;
            }
        }

        return (charDifference == 1);
    }

    public static void main(String[] args) throws FileNotFoundException {
        WordLadder run = new WordLadder("input.txt", "dictionary.txt");

    }
}

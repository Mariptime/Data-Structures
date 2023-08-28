import java.io.*;
import java.util.*;

public class BoggleSolver
{
	private ArrayList<String> dict;
	private HashSet<String> words;
	private BoggleBoard bBoard;
	private int rows;
	private int cols;
	private Stack[][] neighbors;
	private boolean[][] inCurrentPrefix;
	private Stack<List.Node> prefixNodeStack;

	public BoggleSolver(String dictionaryName) throws FileNotFoundException {
		Scanner scan  = new Scanner(new File(dictionaryName));
		while(scan.hasNext())
			dict.add(scan.next());
	}

	public Iterable<String> getAllValidWords(BoggleBoard board) {
		words = new HashSet<>();
		bBoard = board;
		rows = bBoard.rows();
		cols = bBoard.cols();
		neighbors = new Stack[rows][cols];

		buildNeighbors();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				inCurrentPrefix = new boolean[rows][cols];
				prefixNodeStack = new Stack<List.Node>();
				prefixNodeStack.push(dict.get(1));
				dfs("", i, j);
			}
		}
		return words;
	}
	private void dfs(String prefix, int row, int col) {
		char c = bBoard.getLetter(row, col);
		inCurrentPrefix[row][col] = true;

		List.Node x = dict.nextNode(prefixNodeStack, c);

		if (x != null) {
			prefixNodeStack.push(x);
			String newPrefix;
			if (c == 'Q') {
				List.Node uNode = dict.nextNode(prefixNodeStack, 'U');
				if (uNode == null) {
					prefixNodeStack.pop();
					inCurrentPrefix[row][col] = false;
					return;
				}
				prefixNodeStack.push(uNode);
				newPrefix = prefix + "QU";
			} else {
				newPrefix = prefix + c;
			}
			if (newPrefix.length() > 2 && dict.contains(newPrefix))
				words.add(newPrefix);
			for (Object oneDIndex: neighbors[row][col]) {
				int m = (int) oneDIndex / cols;
				int n = (int) oneDIndex % cols;
				if (!inCurrentPrefix[m][n]) {
					dfs(newPrefix, m, n);
				}
			}
			prefixNodeStack.pop();
			if (c == 'Q') prefixNodeStack.pop();
		}
		inCurrentPrefix[row][col] = false;
	}
	private void buildNeighbors() {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				Stack<Integer> neighborsStack = new Stack<Integer>();
				for (int i = row - 1; i < row + 2; i++) {
					for (int j = col - 1; j < col + 2; j++) {
						if (i > - 1 && i < rows && j > -1 && j < cols)
							neighborsStack.push(toOneD(i, j));
					}
				}
				neighbors[row][col] = neighborsStack;
			}
		}
	}

	private int toOneD(int row, int col) {
		return row * cols + col;
	}

	public int scoreOf(String word) {
		return dict.contains(word) ? score(word) : 0;
	}

	private int score(String word) {
		int len = word.length();
		if (len < 3) return 0;
		if (len < 5) return 1;
		if (len == 5) return 2;
		if (len == 6) return 3;
		if (len == 7) return 5;
		return 11;
	}

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("WORKING");

		final String PATH   = "./data/";
		BoggleBoard  board  = new BoggleBoard(PATH + "board-q.txt");
		BoggleSolver solver = new BoggleSolver(PATH + "dictionary-algs4.txt");

		int totalPoints = 0;

		for (String s : solver.getAllValidWords(board)) {
			System.out.println(s + ", points = " + solver.scoreOf(s));
			totalPoints += solver.scoreOf(s);
		}

		System.out.println("Score = " + totalPoints);
	}

}

import java.io.*;
import java.util.*;

public class GameTree {

    private BinaryTreeNode root;
    private Scanner my_inputFile;
    private final String fileName;
    private BinaryTreeNode currentNodeReferenceInTree;

    public GameTree(String fileName) {
        this.fileName = fileName;

        try {
            my_inputFile = new Scanner(new FileInputStream(this.fileName));
        } catch (FileNotFoundException e) {
            my_inputFile = null;
            root = null;
        }

        root = build();
        if (my_inputFile != null)
            my_inputFile.close();
        currentNodeReferenceInTree = root;

    }

    private BinaryTreeNode build() {
        if (my_inputFile != null && my_inputFile.hasNextLine()) {

            String str = my_inputFile.nextLine();
            if (isAQuestion(str)) {
                BinaryTreeNode t = new BinaryTreeNode(str);
                t.left = build();
                t.right = build();
                return t;
            }
            return new BinaryTreeNode(str);
        } else return null;
    }

    private boolean isAQuestion(String str) {
        return str.charAt(str.length() - 1) == '?';
    }

    public void add(String newQ, String newA) {
        String temp = currentNodeReferenceInTree.data;
        currentNodeReferenceInTree.data = newQ;
        currentNodeReferenceInTree.left = new BinaryTreeNode(newA);
        currentNodeReferenceInTree.right = new BinaryTreeNode(temp);
    }

    public boolean foundAnswer() {
        return currentNodeReferenceInTree != null
                && !isAQuestion(currentNodeReferenceInTree.data);
    }

    public String getCurrent() {
        if (currentNodeReferenceInTree != null)
            return currentNodeReferenceInTree.data;
        return "";
    }

    public void playerSelected(Choice yesOrNo) {
        if (yesOrNo == Choice.Yes)
            currentNodeReferenceInTree = currentNodeReferenceInTree.left;
        else
            currentNodeReferenceInTree = currentNodeReferenceInTree.right;
    }

    public void reStart() {
        currentNodeReferenceInTree = root;
    }

    @Override
    public String toString() {
        return traversal(root, "");
    }

    private String traversal(BinaryTreeNode t, String str) {
        if (t == null)
            return "";
        else {
            return traversal(t.right, str + "-") + str + t.data + '\n'
                    + traversal(t.left, str + "-");
        }
    }

    public void saveGame() {
        PrintWriter writer;
        try {
            if (my_inputFile == null)
                throw new FileNotFoundException(
                        "Trying to write to a non-existent file!");
            writer = new PrintWriter(new FileOutputStream(fileName));
        } catch (FileNotFoundException e) {
            return;
        }
        write(writer, root);
        writer.close();
    }

    private void write(PrintWriter writer, BinaryTreeNode t) {
        if (t != null) {
            writer.println(t.data);
            write(writer, t.left);
            write(writer, t.right);
        }
    }

    private static class BinaryTreeNode {
        private String data;
        private BinaryTreeNode left;
        private BinaryTreeNode right;

        BinaryTreeNode(String theData) {
            data = theData;
            left = null;
            right = null;
        }
    }
}

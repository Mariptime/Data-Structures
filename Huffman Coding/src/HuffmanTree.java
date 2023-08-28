import java.io.*;
import java.util.*;

public class HuffmanTree {

    private HuffmanNode rootNode;
    public static final int CHAR_MAX = 256;

    public HuffmanTree(String fileName) {
        try
        {
            Scanner scan = new Scanner(new File(fileName));
            while (scan.hasNextLine()) {
                int letterCode = Integer.parseInt(scan.nextLine());
                String code = scan.nextLine();
                rootNode = HuffmanTreeBuilder(rootNode, letterCode, code);
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("An IOException Occurred");
        }
    }

    public HuffmanTree(int[] count) {
        HuffmanNode rootNode;
        HuffmanNode leftChildNode;
        HuffmanNode rightChildNode;
        PriorityQueue<HuffmanNode> priorityQueueTree = new PriorityQueue<>();

        for (int i = 0; i < count.length; i++) {
            if (count[i] != 0) {
                priorityQueueTree.add(new HuffmanNode(i, count[i]));
            }
        }
        priorityQueueTree.add(new HuffmanNode(CHAR_MAX, 1));
        while (priorityQueueTree.size() > 1) {
            leftChildNode = priorityQueueTree.poll();
            rightChildNode = priorityQueueTree.poll();
            rootNode = new HuffmanNode(-1, leftChildNode.getFrequency()
                                        + rightChildNode.getFrequency(), leftChildNode, rightChildNode);
            priorityQueueTree.add(rootNode);
        }
        this.rootNode = priorityQueueTree.poll();
    }

    public void write(String fileName) {
        if (rootNode != null) {
            write(fileName, rootNode, "");
        }
    }

    private void write(String fileName, HuffmanNode root, String code) {
        try {
            if (root.getLeftChild() == null && root.getRightChild() == null) {
                FileWriter writer = new FileWriter(fileName);
                writer.write(root.getAscii());
                writer.write(code);
            } else {
                write(fileName, root.getLeftChild(), code + "0");
                write(fileName, root.getRightChild(), code + "1");
            }
        }
        catch(IOException e)
        {
            System.out.println("An IO Exception Occurred");
        }
    }

    private HuffmanNode HuffmanTreeBuilder(HuffmanNode root, int letterCode, String code) {
        if (root == null) {
            root = new HuffmanNode(0, -1);
        }
        if (code.length() == 1) {
            if (code.charAt(0) == '0') {
                root.setLeftChild(new HuffmanNode(0, letterCode));
            } else {
                root.setRightChild(new HuffmanNode(0, letterCode));
            }
        } else {
            char codeValue = code.charAt(0);
            code = code.substring(1);
            if (codeValue == '0') {
                root.setLeftChild(HuffmanTreeBuilder(root.getLeftChild(), letterCode, code));
            } else {
                root.setRightChild(HuffmanTreeBuilder(root.getRightChild(), letterCode, code));
            }
        }
        return root;
    }

    public void decode(BitInputStream input, String output, int eofValue) {
        int read = input.readBit();
        while (read != -1) {
            read = HuffmanTreeTraverse(input, read, rootNode, output, eofValue);
        }
    }

    private int HuffmanTreeTraverse(BitInputStream input, int code, HuffmanNode root, String output, int eofValue) {
        try
        {
            if (root.getLeftChild() == null && root.getRightChild() == null && root.getAscii() != eofValue) {
                FileWriter Fwriter = new FileWriter(output);
                Fwriter.write(root.getAscii());
                return code;
            } else if (code == 0 && root.getAscii() != eofValue) {
                return HuffmanTreeTraverse(input, input.readBit(), root.getLeftChild(), output, eofValue);
            } else if (root.getAscii() != eofValue) {
                return HuffmanTreeTraverse(input, input.readBit(), root.getRightChild(), output, eofValue);
            }
        }
        catch(IOException e)
        {
            System.out.println("An IO Exception Occurred");
        }
        return -1;
    }
}

    class HuffmanNode implements Comparable<HuffmanNode> {
    private final int frequency;
    private final int ascii;
    private HuffmanNode leftChild;
    private HuffmanNode rightChild;

    public HuffmanNode(int ascii, int frequency, HuffmanNode left, HuffmanNode right) {
        this.frequency = frequency;
        this.ascii = ascii;
        this.leftChild = left;
        this.rightChild = right;
    }

    public HuffmanNode(int ascii, int frequency) {
        this(ascii, frequency, null, null);
    }

    public int getFrequency() {
        return frequency;
    }

    public int getAscii() {
        return ascii;
    }

    public HuffmanNode getLeftChild() {
        return leftChild;
    }

    public HuffmanNode getRightChild() {
        return rightChild;
    }

    public void setLeftChild(HuffmanNode leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(HuffmanNode rightChild) {
        this.rightChild = rightChild;
    }

    public int compareTo(HuffmanNode other) {
        return this.frequency - other.getFrequency();
    }
}
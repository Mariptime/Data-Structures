public class MyBST
{
    private static BSTNode root;
    private int C = 0;
    public MyBST() {
        root = null;
    }

    public boolean contains(int id) {
        BSTNode current = root;
        while (current != null) {
            if (current.val == id) {
                return true;
            } else if (current.val > id) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return false;
    }

    public int size() {
        return C;
    }

    public boolean delete(int id) {
        BSTNode parent = root;
        BSTNode current = root;
        boolean isLeftChild = false;
        while (current.val != id) {
            parent = current;
            if (current.val > id) {
                isLeftChild = true;
                current = current.left;
            } else {
                isLeftChild = false;
                current = current.right;
            }
            if (current == null) {
                return false;
            }
        }
        if (current.left == null && current.right == null) {
            if (current == root) {
                root = null;
            }
            if (isLeftChild == true) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        }
        else if (current.right == null) {
            if (current == root) {
                root = current.left;
            } else if (isLeftChild) {
                parent.left = current.left;
            } else {
                parent.right = current.left;
            }
        } else if (current.left == null) {
            if (current == root) {
                root = current.right;
            } else if (isLeftChild) {
                parent.left = current.right;
            } else {
                parent.right = current.right;
            }
        } else if (current.left != null && current.right != null) {
            BSTNode successor = getSuccessor(current);
            if (current == root) {
                root = successor;
            } else if (isLeftChild) {
                parent.left = successor;
            } else {
                parent.right = successor;
            }
            successor.left = current.left;
        }
        C--;
        return true;
    }

    public BSTNode getSuccessor(BSTNode deleteNode) {
        BSTNode successor = null;
        BSTNode successorParent = null;
        BSTNode current = deleteNode.right;
        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.left;
        }
        if (successor != deleteNode.right) {
            successorParent.left = successor.right;
            successor.right = deleteNode.right;
        }
        return successor;
    }

    int getMin()  {
        int minval = root.val;
        while (root.left != null)  {
            minval = root.left.val;
            root = root.left;
        }
        return minval;
    }

    int getMax()  {
        int maxval = root.val;
        while (root.right != null)  {
            maxval = root.right.val;
            root = root.right;
        }
        return maxval;
    }

    public void inOrder() {
        BSTNode current, pre;
        if (root == null)
            return;
        current = root;
        while (current != null) {
            if (current.left == null) {
                System.out.print(current.val + " ");
                current = current.right;
            } else {
                pre = current.left;
                while (pre.right != null
                        && pre.right != current)
                    pre = pre.right;
                if (pre.right == null) {
                    pre.right = current;
                    current = current.left;
                }
                else {
                    pre.right = null;
                    System.out.print(current.val + " ");
                    current = current.right;
                }
            }
        }
    }

    public void insert(int id) {
        BSTNode newNode = new BSTNode(id);
        if (root == null) {
            root = newNode;
            return;
        }
        BSTNode current = root;
        BSTNode parent = null;
        while (true) {
            parent = current;
            if (id < current.val) {
                current = current.left;
                if (current == null) {
                    parent.left = newNode;
                    C++;
                    return;
                }
            } else {
                current = current.right;
                if (current == null) {
                    parent.right = newNode;
                    C++;
                    return;
                }
            }
        }
    }

    public void printr(BSTNode root){
        if(root!=null){
            printr(root.left);
            System.out.print(" " + root.val);
            printr(root.right);
        }
    }

    public void print() {
        printr(root);
        }

    private class BSTNode {
        Integer val;
        BSTNode left, right;

        public BSTNode(Integer val) {
            this.val = val;
            left = right = null;
        }

        @Override
        public String toString() {
            return "" + this.val;
        }
    }
}
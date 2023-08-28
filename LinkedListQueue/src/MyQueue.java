import java.util.*;
    public class MyQueue<T> {

        class Node {
            T data;
            Node next;

            public Node() {
            }

            public Node(T item) {
                this.data = item;
            }

            public Node getNext() {
                return next;
            }

            public void setNext(Node next) {
                this.next = next;
            }

        }

        private final Node head;
        private Node tail;
        private int size = 0;

        public MyQueue() {
            head = new Node();
            tail = head;
        }

        public void offer(T item) {
            Node q = new Node(item);
            tail.setNext(q);
            tail = q;
            this.size++;
        }

        public T poll() throws NoSuchElementException {
            if (this.isEmpty()) {
                throw new NoSuchElementException();
            } else {
                T item = (head.next).data;
                if (head.next == tail) {
                    tail = head;
                }
                head.next = (head.next).next;
                this.size--;
                return item;
            }
        }


        public int size() {
            return this.size;
        }

        public boolean isEmpty() {
            return (head == tail);
        }
    }

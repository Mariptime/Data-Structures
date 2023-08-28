
public class MyLinkedList<E> {

    private ListNode head;
    private ListNode tail;
    private int size;

    private class ListNode<E>{
        E val;
        ListNode next;

        public ListNode(E val)
        {
            this.val = val;
        }

        @Override
        public String toString()
        {
            return "" + this.val;
        }
    }

    public MyLinkedList()
    {
        head = null;
    }

    public MyLinkedList(E... vals){
        for (E val : vals) {
            head = new ListNode(val);
            head = head.next;
        }
    }

    public MyLinkedList(E val)
    {
        head = new ListNode(val);
        head.next = null;
    }

    public void add(E newVal)
    {
        if (head == null)
        {
            head = new ListNode(newVal);
            return;
        }
        ListNode dupHead = head;
        while(dupHead.next != null)
        {
            dupHead = dupHead.next;
        }
        dupHead.next = new ListNode(newVal);
        this.size++;
    }

    private ListNode makeDup()
    {
        ListNode dupHead;
        if (head == null)
            dupHead = null;
        else
        {
            dupHead = new ListNode(head.val);
            dupHead.next = head.next;
        }
        return dupHead;
    }

    public boolean contains(E target)
    {
        ListNode dupHead = makeDup();
        while(dupHead != null)
        {
            if(dupHead.val == target)
                return true;
            dupHead = dupHead.next;
        }
        return false;
    }

    public E get(int index)
    {
        int i = 0;
        if (index < 0 || index >= size())
        {
            throw new IndexOutOfBoundsException();
        }
        ListNode dupHead = head;
        while(dupHead != null)
        {
            if(i == index)
                return (E) dupHead.val;
            dupHead = dupHead.next;
            i++;
        }

    return (E) head.val; }
    public int indexOf(E target)
    {
        int i = 0;
        ListNode dupHead = head;
        while (dupHead != null)
        {
            if (dupHead.val == target)
                return i;
            dupHead = dupHead.next;
            i++;
        }
        return -1;
    }

    public void set (E newVal, int index)
    {
        int i = 0;
        if (index < 0 || index >= size())
        {
            throw new IndexOutOfBoundsException();
        }
        ListNode dupHead = head;
        while(dupHead != null)
        {
            if(i == index)
                dupHead.val = newVal;
            dupHead = dupHead.next;
            i++;
        }
    }

    public int size()
    {
        int s = 0;
        ListNode dupHead = head;
        while(dupHead != null)
        {
            s++;
            dupHead = dupHead.next;
        }
        return s;
    }

    public boolean isEmpty()
    {
        return size() == 0;
    }

    public E remove(int index)
    {
        if (index < 0 || index >= size())
        {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0)
        {
            E val = (E) head.val;
            head = head.next;
            return val;
        }

        int i = 0;
        E out = (E) head.next.val;
        ListNode dupHead = head;
        while(dupHead != null)
        {
            if (i == index-1)
            {
                out = (E) dupHead.next.val;
                dupHead.next = dupHead.next.next;
            }
            dupHead = dupHead.next;
            i++;
        }
        this.size--;
        return out;
    }

    public void add(E value, int index) {
        if (index == size -1) {
            head.next = new ListNode(value);
        } else {
            ListNode dupHead = head;
            for (int i = 0; i < size; i++) {
                if (i == index-1)
                {
                    ListNode newNode = new ListNode(value);
                    newNode.next = dupHead.next;
                    dupHead.next = newNode;
                }
                dupHead = dupHead.next;
            }

        }
    }

    public String toString()
    {
        if (head == null)
            return "[]";
        String s = "[";
        ListNode dupHead = head;
        s+= dupHead;
        dupHead = dupHead.next;
        while(dupHead != null)
        {
            s += "," + dupHead.val;
            dupHead = dupHead.next;
        }
        s += "]";
        return s;
    }
}

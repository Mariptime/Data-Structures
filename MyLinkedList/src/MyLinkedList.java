
public class MyLinkedList {

    private ListNode head;
    private ListNode tail;
    private int size;

    private class ListNode{
        int val;
        ListNode next;

        public ListNode(int val)
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

    public MyLinkedList(int val)
    {
        head = new ListNode(val);
        head.next = null;
    }

    public void add(int newVal)
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

    boolean contains(int target)
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

    public int get(int index)
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
                return dupHead.val;
            dupHead = dupHead.next;
            i++;
        }
        return -1;
    }
    public int indexOf(int target)
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

    public void set (int newVal, int index)
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

    public int remove(int index)
    {
        if (index < 0 || index >= size())
        {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0)
        {
            int val = head.val;
            head = head.next;
            return val;
        }

        int i = 0;
        int out = -1;
        ListNode dupHead = head;
        while(dupHead != null)
        {
            if (i == index-1)
            {
                out = dupHead.next.val;
                dupHead.next = dupHead.next.next;
            }
            dupHead = dupHead.next;
            i++;
        }
        this.size--;
        return out;
    }

    public void add(int value, int index) {
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

public class Runner
{
    public static void main(String[] args)
    {
        MyLinkedList a = new MyLinkedList();
        MyLinkedList b = new MyLinkedList(20);

        System.out.println(a);
        System.out.println(b);

        MyLinkedList list = new MyLinkedList();
        list.add(4); list.add(5); list.add(6);
        System.out.println(list);

        System.out.println(list.contains(3));
        System.out.println(list.contains(4));

        System.out.println(list.indexOf(6));

        list.add(10);
        System.out.println(list.get(list.size() - 1));

        list.add(7); list.add(8); list.add(9);
        list.set(100, 4);
        System.out.println(list);
        System.out.println(list.size());

        list.remove(1);
        System.out.println(list);

        list.add(1000, 2);
        System.out.println(list);

        while (!list.isEmpty()) {
            System.out.print(list.remove(0) + " ");
        }

        System.out.println("\n" + list.size());
    }
}

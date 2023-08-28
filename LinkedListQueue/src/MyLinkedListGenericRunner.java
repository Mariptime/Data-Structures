public class MyLinkedListGenericRunner
{
	public static void main(String[] args) 
	{
		MyLinkedList<Integer> a = new MyLinkedList<>();
		MyLinkedList<Boolean> b = new MyLinkedList<>(true);
		MyLinkedList<Double>  c = new MyLinkedList<>(3.14159, 1.0, 1.0);
		
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		
		MyLinkedList<String> list = new MyLinkedList<>();
		list.add("does"); list.add("this"); list.add("work");
		System.out.println(list);
		System.out.println(list.size());
		
		System.out.println(list.indexOf("work"));
		
		list.add("I've");
		System.out.println(list.get(list.size() - 1));
		
		list.add("absolutely"); list.add("no"); list.add("idea");
		System.out.println(list);
		
		list.set("positively", 4);
		System.out.println(list);
		System.out.println(list.size());
		
		list.remove(4);
		System.out.println(list);
		
		list.add("even", 2);
		System.out.println(list); 
		
		while (!list.isEmpty()) {
			System.out.print(list.remove(0) + " ");
		}
		
		System.out.println("\n" + list.size());
	}
}
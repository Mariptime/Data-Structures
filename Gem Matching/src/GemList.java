import java.util.LinkedList;
import java.util.List;

public class GemList
{
	public List<Gem> list = new LinkedList<>();

	private class Node
	{
		private Gem gem;
		private Node next;
	}

	private int size()
	{
		return list.size();
	}
	private void draw(double y)
	{
		double x = 0.0;
		for(Gem gem: list)
		{
			if(x < 1)
			{
				gem.draw(x,y);
				x += 0.075;
			}
			else
				break;
		}
	}

	public String toString()
	{
		String out = "";
		for (Gem g: list) {
			if (list == null)
			{
				out += "<none>";
			}
			else
				out += g.getType() + " -> ";
		}
		return out;
	}

	public void insertBefore(Gem gem, int index)
	{
		if(index >= list.size())
		{
			list.add(gem);
		}
		else
			list.add(index, gem);
	}

	public int score()
	{
		int sum = 0;
		for (int i = 0; i < list.size(); i++) {
			Gem g = list.get(i);
			sum += g.getPoints();
		}
		return sum*2;
	}

	public static void main(String [] args)
	{
		GemList list = new GemList();
		System.out.println("<none>");
		System.out.println("size = " + list.size() + ", score = " + list.score());
		list.draw(0.9);

		list.insertBefore(new Gem(GemType.BLUE, 10), 0);
		System.out.println("\n" + list);
		System.out.println("size = " + list.size() + ", score = " + list.score());
		list.draw(0.8);

		list.insertBefore(new Gem(GemType.BLUE, 20), 99);
		System.out.println("\n" + list);
		System.out.println("size = " + list.size() + ", score = " + list.score());
		list.draw(0.7);

		list.insertBefore(new Gem(GemType.ORANGE, 30), 1);
		System.out.println("\n" + list);
		System.out.println("size = " + list.size() + ", score = " + list.score());
		list.draw(0.6);

		list.insertBefore(new Gem(GemType.ORANGE, 10), 2);
		System.out.println("\n" + list);
		System.out.println("size = " + list.size() + ", score = " + list.score());
		list.draw(0.5);

		list.insertBefore(new Gem(GemType.ORANGE, 50), 3);
		System.out.println("\n" + list);
		System.out.println("size = " + list.size() + ", score = " + list.score());
		list.draw(0.4);

		list.insertBefore(new Gem(GemType.GREEN, 50), 2);
		System.out.println("\n" + list);
		System.out.println("size = " + list.size() + ", score = " + list.score());
		list.draw(0.3);
	}	
}

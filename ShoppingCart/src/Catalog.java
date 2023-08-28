import java.util.ArrayList;

public class Catalog
{
    private final String name;
    private final ArrayList<Item> list = new ArrayList<>();

    public Catalog(String name) {
        this.name = name;
    }
    public void add(Item item)
    {
        list.add(item);
    }
    public int size()
    {
        return list.size();
    }
    public Item getItem(int index)
    {
        return list.get(index);
    }
    public String getName() {
        return name;
    }

}

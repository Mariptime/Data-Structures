import java.util.ArrayList;

public class ShoppingCart
{
    private final ArrayList<ItemOrder> orders = new ArrayList<>();

    public ShoppingCart() {}

    public void add (ItemOrder newOrder)
    {
        for (int i = 0; i < orders.size(); i++)
        {
            if (newOrder.equals(orders.get(i)))
            {
                orders.set(i, newOrder);
                return;
            }
        }
        orders.add(newOrder);
    }
    public double getTotal()
    {
        double total = 0;
        for (ItemOrder order: orders) {
            total += order.getPrice();
        }
        return total;
    }
        
}

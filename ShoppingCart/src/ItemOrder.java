public class ItemOrder
{
    private final Item item;
    private final int qty;

    public ItemOrder(Item item, int qty) {
        this.item = item;
        this.qty = qty;
    }

    public Item getItem() {
        return item;
    }
    public double getPrice()
    {
        return item.priceFor(qty);
    }
    public boolean equals(Object obj)
    {
        ItemOrder ord = (ItemOrder) obj;
        Item check = ord.getItem();
        return item.equals(check);
    }
}

public class Item
{
    private final String name;
    private final double price;
    private final int bulkQty;
    private final double bulkPrice;

    public Item(String name, double price, int bulkQty, double bulkPrice) {
        this.name = name;
        this.price = price;
        this.bulkQty = bulkQty;
        this.bulkPrice = bulkPrice;
        if (price < 0 || bulkQty < 0 || bulkPrice < 0) {
            throw new IllegalArgumentException("error");
        }
    }

    private String getName() {
        return name;
    }

    public Item(String name, double price) {
        this(name,price, 0 , price);
    }

    public double priceFor(int quantity)
    {
        if (bulkQty > 0 && quantity >= bulkQty) {
            return bulkPrice * quantity;
        }
        else{
            return price * quantity;
        }

    }

    @Override
    public boolean equals(Object obj)
    {
        Item i = (Item) obj;
        String check = i.getName();
        return (name.equals(check));
    }

    @Override
    public String toString() {
        if (bulkQty > 0)
        {
            return name + ", " + price + ", (" + bulkQty + " for " + bulkPrice + ")";
        }
        else
            return name + ", " + price;
    }
}

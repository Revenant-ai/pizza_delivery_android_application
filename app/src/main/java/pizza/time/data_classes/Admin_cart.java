package pizza.time.data_classes;

public class Admin_cart
{
    private String Name;
    private double Price;
    private int Qty;
    private String Size;

    public Admin_cart(String name, double price, int qty,String size) {
        Name = name;
        Price = price;
        Qty = qty;
        Size=size;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }
}

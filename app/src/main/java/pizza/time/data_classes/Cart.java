package pizza.time.data_classes;

public class Cart
{
    private String Name;
    private double Price;
    private int Qty;
    private String Photo;
    private String Size;


    public Cart(String Name, double Price, int Qty, String Photo, String Size) {
        this.Name = Name;
        this.Price = Price;
        this.Qty= Qty;
        this.Photo=Photo;
        this.Size=Size;
    }

    public String getPizza_name() {
        return Name;
    }

    public void setPizza_name(String pizza_name) {
        Name = pizza_name;
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

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }
}

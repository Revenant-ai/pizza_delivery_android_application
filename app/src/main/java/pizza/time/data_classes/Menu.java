package pizza.time.data_classes;

public class Menu
{
    private String Name;
    private double Price;
    private int Qty;
    private String Photo;

    public Menu(String Name, double Price, int Qty, String Photo) {
        this.Name = Name;
        this.Price = Price;
        this.Qty= Qty;
        this.Photo=Photo;
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
}

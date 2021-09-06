package pizza.time.data_classes;

public class Order_client
{
    private String Date,Time,Status;
    private String Grand_total,User,Order_id;


    public Order_client(String date, String time, String status, String grand_total, String user, String order_id) {
        Date = date;
        Time = time;
        Status = status;
        Grand_total=grand_total;
        User=user;
        Order_id=order_id;

    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String gettime() {
        return Time;
    }

    public void Settime(String time) {
        Time = time;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getGrand_total() {
        return Grand_total;
    }

    public void setGrand_total(String grand_total) {
        Grand_total = grand_total;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getOrder_id() {
        return Order_id;
    }

    public void setOrder_id(String order_id) {
        Order_id = order_id;
    }
}

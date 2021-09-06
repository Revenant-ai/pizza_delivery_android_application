package pizza.time.data_classes;

public class User
{
    private String name,email,phone,uid;

    public User(){

    }
    public User(String name, String email,String phone){
        this.name=name;
        this.email=email;
        this.phone=phone;
    }
    //getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }


    //setters
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}

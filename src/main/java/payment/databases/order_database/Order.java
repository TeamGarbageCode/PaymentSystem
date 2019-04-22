package payment.databases.order_database;

public class Order {

    private String id;
    private String name;
    private String description;
    private String price;
    private String seller;

    public Order(String name, String price, String description, String seller) {
        this.id = nextID();
        this.name = name;
        this.description = description;
        this.price = price;
        this.seller = seller;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getSeller() {
        return seller;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    private String nextID(){
        return "default id";
    }
}

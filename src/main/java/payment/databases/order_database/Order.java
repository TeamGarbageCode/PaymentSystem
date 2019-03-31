package payment.databases.order_database;

public class Order {

    private static final String defaultSeller = "admin";

    private String id;
    private String name;
    private String description;
    private String price;
    private String seller;

    public Order(String id, String name, String price) {
        this.id = id;
        this.name = name;
        this.description = "";
        this.price = price;
        this.seller = defaultSeller;
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
}

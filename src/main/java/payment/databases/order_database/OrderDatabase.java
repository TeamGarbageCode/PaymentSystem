package payment.databases.order_database;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class OrderDatabase {

    private Map<String, Collection<Order>> orders = new HashMap<>();

    public  OrderDatabase(String url,String login,String password) {

    }

    public void addOrder(String login, Order order){
        orders.get(login)
                .add(order);
    }

    public Collection<Order> getOrders(String login){
        return orders.get(login);
    }

    public Map<String, Collection<Order>> getAllOrders(){
        return orders;
    }

}

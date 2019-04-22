package payment.databases.order_database;

import payment.databases.DBConnector;
import payment.exceptions.DatabaseCrashedException;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class OrderDatabase {

    private Map<String, Collection<Order>> orders = new HashMap<>();

    public  OrderDatabase(String url, String dblogin, String dbpassword) throws DatabaseCrashedException {
        DBConnector dbConnector = new DBConnector();
        try {
            dbConnector.connectToDB(url, dblogin, dbpassword);
            Statement statement = dbConnector.getConnection().createStatement();
            //sort by login before SELECT
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

            while (resultSet.next()) {
                String lastLogin = resultSet.getString("login");

                //ArrayList?
                Collection<Order> result = new ArrayList<>();

                for(String login = resultSet.getString("login");
                        login.equals(lastLogin); login = resultSet.getString("login")){
                    String name = resultSet.getString("name");
                    String price = resultSet.getString("price");
                    String description = resultSet.getString("description");
                    String seller = resultSet.getString("seller");
                    result.add(new Order(name, price, description, seller));
                }

                orders.put(lastLogin, result);
            }
        } catch (Exception e){
            throw new DatabaseCrashedException(e.getMessage());
        } finally {
            dbConnector.closeConnection();
        }
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

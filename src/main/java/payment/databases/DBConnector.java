package payment.databases;

import java.sql.*;
import java.util.Properties;

public class DBConnector {
    private Connection connection;

    public void connectToDB(String url,String login,String password){
        Properties properties = new Properties();
        properties.put("user", login);
        properties.put("password", password);
        properties.put("autoReconnect", "true");
        properties.put("characterUnicode", "true");
        properties.put("useUnicode", "true");
        properties.put("useSSL", "false");
        properties.put("useLegacyDatetimeCode", "false");
        properties.put("serverTimezone", "UTC");
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            connection =  DriverManager.getConnection(url,properties);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Connection getConnection(){
        return connection;
    }
    public boolean isConnectionClosed()  {
        try {
            return connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

}
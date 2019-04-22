package payment.databases.user_database;

import com.mysql.cj.xdevapi.SqlDataResult;
import payment.databases.DBConnector;
import payment.exceptions.IncorrectLoginException;
import payment.exceptions.LoginAlreadyExistsException;
import payment.exceptions.UnallowedRoleException;
import payment.exceptions.DatabaseCrashedException;
import payment.users.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import java.util.*;
<<<<<<< HEAD

public class UserDatabase {

    private Map<String, User> users = new HashMap<>();

    public UserDatabase(String url,String dblogin,String dbpassword) throws DatabaseCrashedException {
        DBConnector dbConnector = new DBConnector();
        try {
            dbConnector.connectToDB(url, dblogin, dbpassword);
            Statement statement = dbConnector.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                String login = resultSet.getString("login");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                byte[] password = resultSet.getString("password").getBytes();

                User result;
                switch (Role.valueOf(resultSet.getString("role"))){
                    case CLIENT:
                        result = new Client(login, firstName, lastName, password);
                        break;
                    case ADMIN:
                        result = new Admin(login, firstName, lastName, password, this);
                        break;
                    default:
                        throw new UnallowedRoleException();
                }
                users.put(login, result);
            }
        } catch (Exception e){
            throw new DatabaseCrashedException(e.getMessage());
        } finally {
            dbConnector.closeConnection();
=======
/*
поля в БД
id, login,password,firstName,lastName,id_role
*/

public  class UserDatabase {
    private final  String URL = "jdbc:mysql://localhost:3306";
    private final  String LOGIN = "root";
    private final  String PASSWORD = "root";

    public  List<User> getAllUsers(){
        List<User> users = new ArrayList<User>();
        try(Statement statement = createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT login,firstName,lastName,password,role FROM users\n" +
                    "JOIN roles ON roles.id = id_role");
            while (resultSet.next()){
                users.add(createUser(resultSet));
            }
        }catch (SQLException ex){
            ex.printStackTrace();
>>>>>>> 429a3788b540ab9d7d0fec4734a8fd2cbf91f27b
        }
        return users;
    }
<<<<<<< HEAD

    public UserDatabase(String adminFirstName, String adminLastName) throws DatabaseCrashedException {
        try {
            addUser("admin", adminFirstName, adminLastName, "admin", Role.ADMIN);
        } catch (Exception e) {
            throw new DatabaseCrashedException();
=======
    private  User createUser(ResultSet resultSet)throws SQLException{
        switch (Role.valueOf(resultSet.getString("role"))){
            case ADMIN:
                return new Admin(resultSet.getString("login"),resultSet.getString("firstName"),
                        resultSet.getString("lastName"),"password",this);
            case CLIENT:
                return new Client(resultSet.getString("login"),resultSet.getString("firstName"),
                        resultSet.getString("lastName"),"password");
>>>>>>> 429a3788b540ab9d7d0fec4734a8fd2cbf91f27b
        }
        return null;
    }
    private Statement createStatement()throws SQLException{
        DBConnector connector = new DBConnector();
        connector.connectToDB(URL,LOGIN,PASSWORD);
        return connector.getConnection().createStatement();
    }
    public User getUser(String login){
        try(Statement statement = createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT login,firstName,lastName,password,role FROM users\n" +
                    "JOIN roles ON roles.id = id_role WHERE login =" + "\"" + login + "\"");
            if (resultSet.next()){
                return createUser(resultSet);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
    //role is not used. When user was added, one is client. So then it can be changed to admin
    public void addUser(String login,String firstName,String lastName,String password,Role role)throws LoginAlreadyExistsException{
        String data = "\"" + login + "\",\""+password + "\",\"" + firstName + "\",\" " + lastName + "\"";
        try(Statement statement = createStatement()){
            statement.executeUpdate("INSERT INTO users (login,password,firstName,lastName) VALUES (" + data + ")");
        }catch (SQLIntegrityConstraintViolationException ex){
            throw new LoginAlreadyExistsException();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    public void delUser(String login){
        try(Statement statement = createStatement()){
            statement.executeUpdate("DELETE FROM users WHERE login = \"" + login + "\"");
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
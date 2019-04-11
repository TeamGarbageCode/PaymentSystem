package payment.databases.user_database;

import com.mysql.cj.xdevapi.SqlDataResult;
import payment.databases.DBConnector;
import payment.exceptions.IncorrectLoginException;
import payment.exceptions.LoginAlreadyExistsException;
import payment.exceptions.UnallowedRoleException;
import payment.exceptions.UserDatabaseCrashedException;
import payment.users.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import java.util.*;
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
        }
        return users;
    }
    private  User createUser(ResultSet resultSet)throws SQLException{
        switch (Role.valueOf(resultSet.getString("role"))){
            case ADMIN:
                return new Admin(resultSet.getString("login"),resultSet.getString("firstName"),
                        resultSet.getString("lastName"),"password",this);
            case CLIENT:
                return new Client(resultSet.getString("login"),resultSet.getString("firstName"),
                        resultSet.getString("lastName"),"password");
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
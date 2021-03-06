package payment.databases.user_database;

import payment.databases.DBConnector;
import payment.exceptions.IncorrectLoginException;
import payment.exceptions.LoginAlreadyExistsException;
import payment.exceptions.UnallowedRoleException;
import payment.exceptions.DatabaseCrashedException;
import payment.users.*;

import java.sql.ResultSet;
import java.sql.Statement;

import java.util.*;

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
        }
    }

    public UserDatabase(String adminFirstName, String adminLastName) throws DatabaseCrashedException {
        try {
            addUser("admin", adminFirstName, adminLastName, "admin", Role.ADMIN);
        } catch (Exception e) {
            throw new DatabaseCrashedException();
        }
    }

    public void save(String url,String login,String password){
        //write file
    }

    public User signIn(String login, String password){
        User result = users.get(login);
        if(result != null) {
            if (result.checkPassword(password)) {
                return result;
            }
        }

        return null;
    }

    public void addUser(String login, String firstName, String lastName, String password, Role role)
            throws UnallowedRoleException, LoginAlreadyExistsException, IncorrectLoginException {
        User newUser;

        if(login.isEmpty()){
            throw new IncorrectLoginException();
        }

        switch (role){
            case ADMIN:
                newUser = new Admin(login, firstName, lastName, password, this);
                break;
            case CLIENT:
                newUser = new Client(login, firstName, lastName, password);
                break;
            default:
                throw new UnallowedRoleException();
        }

        if(users.putIfAbsent(login, newUser) != null){
            throw new LoginAlreadyExistsException();
        }
    }

    public void delUser(String login){
        users.remove(login);
    }

    public Collection<User> getAllUsers(){
        return users.values();
    }
}
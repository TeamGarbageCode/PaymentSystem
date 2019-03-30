package Payment.databases;

import Payment.users.*;

import java.nio.file.Path;
import java.util.*;

public class UserDatabase {

    private Map<String, User> users = new HashMap<>();

    public UserDatabase(Path file){
        //read file
    }

    public UserDatabase(String adminFirstName, String adminLastName) throws Exception{
        addUser("admin", adminFirstName, adminLastName, "admin", Role.ADMIN);
    }

    public void save(Path file){
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

    public void addUser(String login, String firstName, String lastName, String password, Role role) throws Exception{
        User newUser;

        switch (role){
            case ADMIN:
                newUser = new Admin(login, firstName, lastName, password, this);
                break;
            case CLIENT:
                newUser = new Client(login, firstName, lastName, password);
                break;
            default:
                //create class for this exception
                throw new Exception("Unallowed role");
        }

        if(users.putIfAbsent(login, newUser) != null){
            //create class for this exception
            throw new Exception("user already exist");
        }
    }

    public void delUser(String login){
        users.remove(login);
    }

    public Collection<User> getAllUsers(){
        return users.values();
    }
}
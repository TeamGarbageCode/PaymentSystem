package Payment.databases;

import Payment.exceptions.IncorrectLoginException;
import Payment.exceptions.LoginAlreadyExistsException;
import Payment.exceptions.UnallowedRoleException;
import Payment.exceptions.UserDatabaseCrashedException;
import Payment.users.*;

import java.nio.file.Path;
import java.util.*;

public class UserDatabase {

    private Map<String, User> users = new HashMap<>();

    public UserDatabase(Path file){
        //read file
    }

    public void save(Path file){
        //write file
    }

    public UserDatabase(String adminFirstName, String adminLastName) throws UserDatabaseCrashedException {
        try {
            addUser("admin", adminFirstName, adminLastName, "admin", Role.ADMIN);
        } catch (Exception e) {
            throw new UserDatabaseCrashedException();
        }
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
package Payment.users;

import Payment.Auxilary;
import Payment.BankDatabase;
import Payment.OrderDatabase;

import java.util.Arrays;
import java.util.Objects;

public abstract class User {

    private String login;
    private String firstName;
    private String lastName;
    private byte[] codedPassword;

    public User(String login, String firstName, String lastName, byte[] hashPass) {
        this.firstName = firstName;
        this.lastName = lastName;

        codedPassword = hashPass.clone();
    }

    public User(String login, String firstName, String lastName, String password) {
        this( login, firstName, lastName, hash(password) );
    }

    public boolean checkPassword(String password){
        return Arrays.equals( codedPassword, hash(password) );
    }

    public boolean changePassword(String oldPassword, String newPassword){
        if(!checkPassword(oldPassword)){
            return false;
        }

        codedPassword = hash(newPassword);
        return true;
    }

    public String getLogin(){
        return login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public abstract Role getRole();

    public abstract void interactWithDB(BankDatabase bdb, OrderDatabase odb);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login);
    }

    private static byte[] hash(String src){
        return Auxilary.toSHA1( src.getBytes() );
    }
}

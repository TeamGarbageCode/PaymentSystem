package Payment.exceptions;

public class LoginAlreadyExistsException extends Exception{

    public LoginAlreadyExistsException(){
        super("login already exists");
    }

}

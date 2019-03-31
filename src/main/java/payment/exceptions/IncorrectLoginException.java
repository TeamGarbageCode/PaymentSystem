package payment.exceptions;

public class IncorrectLoginException extends Exception {

    public IncorrectLoginException(){
        super("incorrect login detected");
    }

}

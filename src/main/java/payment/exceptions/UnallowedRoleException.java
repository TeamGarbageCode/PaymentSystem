package payment.exceptions;

public class UnallowedRoleException extends Exception{

    public UnallowedRoleException(){
        super("unallowed role detected");
    }

}

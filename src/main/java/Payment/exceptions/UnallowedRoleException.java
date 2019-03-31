package Payment.exceptions;

public class UnallowedRoleException extends Exception{

    public UnallowedRoleException(){
        super("unallowed role detected");
    }

}

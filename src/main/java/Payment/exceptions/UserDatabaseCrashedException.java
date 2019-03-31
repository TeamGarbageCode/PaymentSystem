package Payment.exceptions;

public class UserDatabaseCrashedException extends Exception {

    public UserDatabaseCrashedException(){
        super("some problems with the user database");
    }

    public UserDatabaseCrashedException(String msg){
        super(msg);
    }

}

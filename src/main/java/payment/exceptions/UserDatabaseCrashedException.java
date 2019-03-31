package payment.exceptions;

public class UserDatabaseCrashedException extends Exception {

    public UserDatabaseCrashedException(){
        super("some problems with the user_database database");
    }

    public UserDatabaseCrashedException(String msg){
        super(msg);
    }

}

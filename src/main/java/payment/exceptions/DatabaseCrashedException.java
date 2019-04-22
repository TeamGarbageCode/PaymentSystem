package payment.exceptions;

public class DatabaseCrashedException extends Exception {

    public DatabaseCrashedException(){
        super("some problems with the database");
    }

    public DatabaseCrashedException(String msg){
        super(msg);
    }

}

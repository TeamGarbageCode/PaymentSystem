package payment.databases.bank_database;

public class CreditCard {

    private static String lastID = "0";
    private String id;
    private Account account;
    private boolean blocked;

    public CreditCard(Account account){
        this.account = account;
        this.id = nextID();
        this.blocked = false;
    }

    public void setBlocked(boolean blocked){
        this.blocked = blocked;
    }

    public Account getAccount(){
        return account;
    }

    public boolean isBlocked(){
        return blocked;
    }

    public int getAmount(){
        return account.getAmount();
    }

    public String getId(){
        return id;
    }

    private static String nextID(){
        return String.valueOf(Integer.valueOf(lastID) + 1);
    }
}

package payment.databases.bank_database;

public class CreditCard {

    private static String lastID = "1000000";

    private final String ID;
    private String password;
    private BankAccount account;
    private boolean blocked;

    public CreditCard(String ID, String password, BankAccount account){
        this.ID = ID;
        this.password = password;
        this.account = account;
        this.blocked = false;
    }

    public void setBlocked(boolean blocked){
        this.blocked = blocked;
    }

    public BankAccount getAccount(){
        return account;
    }

    public boolean isBlocked(){
        return blocked;
    }

    public int getAmount(){
        return account.getAmount();
    }

    public String getID(){
        return ID;
    }

    private static String nextID(){
        lastID = String.valueOf(Integer.valueOf(lastID) + 1);
        return lastID;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "ID='" + ID + '\'' +
                ", amount=" + account.getAmount() +
                ", blocked=" + blocked +
                '}';
    }
}

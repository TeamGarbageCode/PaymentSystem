package payment.databases.bank_database;

public class BankAccount {

    private final String ID;
    private int amount;

    public BankAccount(String ID, int amount){
        this.ID = ID;
        this.amount = amount;
    }

    void setAmount(int amount){
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public String getID(){
        return ID;
    }
}
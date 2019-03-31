package payment.databases.bank_database;

public class Account {

    private int amount;

    public Account(int amount){
        this.amount = amount;
    }

    void setAmount(int amount){
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
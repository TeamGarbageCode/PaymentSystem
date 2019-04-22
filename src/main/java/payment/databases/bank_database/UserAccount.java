package payment.databases.bank_database;

import java.util.Collection;

public class UserAccount {
    private Collection<BankAccount> accounts;
    private Collection<CreditCard> creditCards;

    public Collection<BankAccount> getAccounts() {
        return accounts;
    }

    public Collection<CreditCard> getCreditCards() {
        return creditCards;
    }

    @Override
    public String toString() {
        StringBuilder accountsInfo = new StringBuilder();

        for (BankAccount account: accounts) {
            accountsInfo.append(account.toString()).append("\n");
        }

        StringBuilder ccInfo = new StringBuilder();
        for (CreditCard cc: creditCards) {
            ccInfo.append(cc.toString()).append("\n");
        }

        return "UserAccount{" +
                "accounts: " + accountsInfo +
                "credit cards=" + ccInfo +
                '}';
    }
}
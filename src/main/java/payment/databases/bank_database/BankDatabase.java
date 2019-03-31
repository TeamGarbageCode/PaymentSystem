package payment.databases.bank_database;

import payment.exceptions.BlockedCreditCardException;
import payment.exceptions.IncorrectCreditCardException;
import payment.exceptions.IncorrectLoginException;

import java.nio.file.Path;
import java.util.*;

public class BankDatabase {

    public static class AccountInfo{
        Account account;
        CreditCard creditCard;

        @Override
        public String toString() {
            return "AccountInfo{" +
                    "amount=" + account.getAmount() +
                    ", creditCardID=" + creditCard.getId() +
                    '}';
        }
    }

    private List<CreditCard> creditCards = new ArrayList<>();
    private Map<String, AccountInfo> accounts;

    //read file
    public BankDatabase(String url,String login,String password){

    }

    //write file
    public void save(Path file){

    }

    public void setCreditCardBlocked(String creditCardID, boolean blocked){
        for (CreditCard creditCard : creditCards) {
            if (creditCard.getId().equals(creditCardID)) {
                creditCard.setBlocked(blocked);
            }
        }
    }

    //remove User from bdb or set AccountInfo = null?
    public void delAccount(String login){
        accounts.remove(login);
    }

    public String getCreditCardID(String login) throws IncorrectLoginException{
        AccountInfo accountInfo = accounts.get(login);

        if(accountInfo == null) {
            throw new IncorrectLoginException();
        }

        return accountInfo.creditCard.getId();
    }

    public AccountInfo getAccountInfo(String login){
        return accounts.get(login);
    }

    //if credit card with srcCreditCardID ID not blocked,
    //transfer amount from srcCreditCardID card to destCreditCardID card(even if it has been blocked)
    public void transfer(String srcCreditCardID, String destCreditCardID, int amount)
            throws IncorrectCreditCardException, BlockedCreditCardException {
        AccountInfo srcInfo = accounts.get(srcCreditCardID);
        AccountInfo destInfo = accounts.get(destCreditCardID);

        if(srcInfo == null || destInfo == null){
            throw  new IncorrectCreditCardException();
        }
        if(srcInfo.creditCard.isBlocked() ){
            throw  new BlockedCreditCardException();
        }

        Account srcAccount = srcInfo.creditCard.getAccount();
        srcAccount.setAmount(srcAccount.getAmount() - amount);
        Account destAccount = destInfo.creditCard.getAccount();
        destAccount.setAmount(destAccount.getAmount() + amount);
    }
}

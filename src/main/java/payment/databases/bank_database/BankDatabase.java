package payment.databases.bank_database;

import payment.exceptions.BlockedCreditCardException;
import payment.exceptions.IncorrectCreditCardException;

import java.nio.file.Path;
import java.util.*;

public class BankDatabase {

    private List<CreditCard> creditCards = new ArrayList<>();
    private Map<String, UserAccount> accounts;


    //read file
    public BankDatabase(String url,String login,String password){
    }


    //write file
    public void save(Path file){
    }


    public void setCreditCardBlocked(String creditCardID, boolean blocked) throws IncorrectCreditCardException {
        CreditCard creditCard = creditCards.stream()
                .filter(creditCard1 -> creditCardID.equals(creditCard1.getID()))
                .findAny()
                .orElse(null);

        if(creditCard == null){
            throw new IncorrectCreditCardException();
        }

        creditCard.setBlocked(blocked);
    }


    //remove User from bdb or set UserAccount = null?
    public void delAccount(String login){
        accounts.remove(login);
    }


    public Collection<CreditCard> getCreditCards(String login) {
        UserAccount accountInfo = accounts.get(login);

        if(accountInfo == null) {
            throw null;
        }

        return accountInfo.getCreditCards();
    }


    public UserAccount getAccountInfo(String login){
        return accounts.get(login);
    }


    //if credit card with srcCreditCardID ID not blocked,
    //transfer amount from srcCreditCardID card to destCreditCardID card(even if it has been blocked)
    public void transfer(String srcCreditCardID, String destCreditCardID, int amount)
            throws IncorrectCreditCardException, BlockedCreditCardException {

        CreditCard srcCreditCard = creditCards.stream()
                .filter(creditCard -> srcCreditCardID.equals(creditCard.getID()))
                .findAny()
                .orElse(null);
        CreditCard destCreditCard = creditCards.stream()
                .filter(creditCard -> destCreditCardID.equals(creditCard.getID()))
                .findAny()
                .orElse(null);
        if(srcCreditCard == null || destCreditCard == null){
            throw  new IncorrectCreditCardException();
        }
        if(srcCreditCard.isBlocked() ){
            throw  new BlockedCreditCardException();
        }

        BankAccount srcAccount = srcCreditCard.getAccount();
        srcAccount.setAmount(srcAccount.getAmount() - amount);
        BankAccount destAccount = destCreditCard.getAccount();
        destAccount.setAmount(destAccount.getAmount() + amount);
    }
}
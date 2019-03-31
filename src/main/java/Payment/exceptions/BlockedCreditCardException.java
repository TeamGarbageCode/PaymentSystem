package Payment.exceptions;

public class BlockedCreditCardException extends Exception {

    public BlockedCreditCardException(){
        super("credit card has been blocked");
    }

}

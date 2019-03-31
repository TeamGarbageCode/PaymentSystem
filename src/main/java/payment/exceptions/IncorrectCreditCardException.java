package payment.exceptions;

public class IncorrectCreditCardException extends Exception{

    public IncorrectCreditCardException(){
        super("incorrect credit card number");
    }

}

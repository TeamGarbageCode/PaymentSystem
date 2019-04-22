package payment.users;

import payment.Auxiliary;
import payment.databases.bank_database.BankDatabase;
import payment.databases.bank_database.CreditCard;
import payment.databases.order_database.Order;
import payment.databases.order_database.OrderDatabase;
import payment.exceptions.BlockedCreditCardException;
import payment.exceptions.IncorrectCreditCardException;
import payment.exceptions.IncorrectLoginException;

import java.util.ArrayList;
import java.util.Collection;

public class Client extends User{

    public Client(String login, String firstName, String lastName, byte[] hashPass) {
        super(login, firstName, lastName, hashPass);
    }

    public Client(String login, String firstName, String lastName, String password) {
        super(login, firstName, lastName, password);
    }

    @Override
    public Role getRole() {
        return Role.CLIENT;
    }

    @Override
    public void interactWithDB(BankDatabase bdb, OrderDatabase odb) {

        String login = getLogin();
        int answer;
        while(true){
            printClientMenu();
            answer = Auxiliary.getCheckedInt();

            switch (answer){
                case 1:
                    for(Order order: odb.getOrders(login)){
                        System.out.println(order);
                    }
                    break;
                case 2:
                    Order order = chooseOrder(odb);

                    if(order == null){
                        System.out.println("You have no orders");
                        break;
                    }

                    String creditCardID = chooseCreditCard(bdb);

                    if(creditCardID == null){
                        System.out.println("You have no credit cards");
                        break;
                    }

                    try {
                        bdb.transfer(creditCardID,
                                order.getSeller(),
                                Integer.valueOf(order.getPrice() ) );
                    } catch (IncorrectCreditCardException e) {
                        System.err.println("banking transaction cannot be performed at the moment, try again later");
                    } catch (BlockedCreditCardException e){
                        System.err.println("credit card has been blocked");
                    }
                    break;
                case 3:
                    System.out.println("destination");
                    String dest = Auxiliary.getCheckedString();
                    System.out.println("amount");
                    int amount = Auxiliary.getCheckedInt();

                    try {
                        bdb.transfer(chooseCreditCard(bdb), dest, amount);
                    } catch (IncorrectCreditCardException e) {
                        System.err.println("incorrect credit card number");
                    } catch (BlockedCreditCardException e){
                        System.err.println("Your credit card has been blocked");
                    }
                    break;
                case 4:
                    bdb.delAccount(login);
                    return;
                case 5:
                    try {
                        bdb.setCreditCardBlocked(chooseCreditCard(bdb), true);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 6:
                    System.out.println("Old password");
                    String oldPassword = Auxiliary.getCheckedString();
                    System.out.println("New password");
                    String newPassword= Auxiliary.getCheckedString();

                    changePassword(oldPassword, newPassword);
                    break;
                case 0:
                    return;
                default:
                    System.err.println("no such paragraph in menu");
                    break;
            }

        }
    }

    private String chooseCreditCard(BankDatabase bdb){
        ArrayList<CreditCard> creditCards = (ArrayList<CreditCard>) bdb.getCreditCards(getLogin());

        if(creditCards == null){
            return null;
        }

        System.out.println("Your credit cards: ");
        for (int i = 0; i < creditCards.size(); ++i){
            System.out.println(i  + " - " + creditCards.get(i));
        }

        System.out.println("Choose credit card");
        int number = Auxiliary.getCheckedInt();

        if(number < 0 || number > creditCards.size()){
            System.err.println("You have made a mischoice");
            return null;
        }

        return creditCards.get(number).getID();
    }

    private Order chooseOrder(OrderDatabase odb){
        ArrayList<Order> orders = (ArrayList<Order>) odb.getOrders(getLogin());

        if(orders == null){
            return null;
        }

        System.out.println("Your orders: ");
        for (int i = 0; i < orders.size(); ++i){
            System.out.println(i  + " - " + orders.get(i));
        }

        System.out.println("Choose order");
        int number = Auxiliary.getCheckedInt();

        if(number < 0 || number > orders.size()){
            System.err.println("You have made a mischoice");
            return null;
        }

        return orders.get(number);
    }

    private static void printClientMenu(){
        System.out.println("1.\tPrint all orders");
        System.out.println("2.\tPay for the order");
        System.out.println("3.\tMoney transfer");
        System.out.println("4.\tClose account");
        System.out.println("5.\tBlock credit card");
        System.out.println("6.\tChange password");
        System.out.println("0.\tLogout");
    }
}

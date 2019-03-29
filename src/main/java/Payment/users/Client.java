package Payment.users;

import Payment.Auxilary;
import Payment.BankDatabase;
import Payment.Order;
import Payment.OrderDatabase;

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
            answer = Auxilary.getCheckedInt();

            switch (answer){
                case 1:
                    for(Order order: odb.getOrders(login)){
                        System.out.println(order);
                    }
                    break;
                case 2:
                    Collection<Order> orders = odb.getOrders(login);
                    for(Order order: orders){
                        System.out.println(order);
                    }
                    System.out.println("order number");
                    int idx = Auxilary.getCheckedInt();

                    Order[] ordersArr = orders.toArray(new Order[0]);

                    try {
                        bdb.transfer(bdb.getCreditCardID(login),
                                ordersArr[idx].getSeller(),
                                Integer.valueOf( ordersArr[idx].getPrice() ) );
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("destination");
                    String dest = Auxilary.getCheckedString();
                    System.out.println("amount");
                    int amount = Auxilary.getCheckedInt();

                    try {
                        bdb.transfer(bdb.getCreditCardID(login), dest, amount);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    bdb.delAccount(login);
                    return;
                case 5:
                    try {
                        bdb.setCreditCardBlocked(bdb.getCreditCardID(login), true);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 6:
                    System.out.println("Old password");
                    String oldPassword = Auxilary.getCheckedString();
                    System.out.println("New password");
                    String newPassword= Auxilary.getCheckedString();

                    changePassword(oldPassword, newPassword);
                    break;
                case 0:
                    return;
                default:
                    System.err.println("incorrect value");
                    break;
            }

        }
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

package payment.users;

import payment.Auxiliary;
import payment.databases.bank_database.BankDatabase;
import payment.databases.order_database.OrderDatabase;
import payment.databases.user_database.UserDatabase;
import payment.exceptions.IncorrectCreditCardException;

import java.util.Collection;

public class Admin extends User {

    private UserDatabase udb;

    public Admin(String login, String firstName, String lastName, String password, UserDatabase udb){
        super(login, firstName, lastName, password);
        this.udb = udb;
    }

    public Admin(String login, String firstName, String lastName, byte[] hashPass, UserDatabase udb){
        super(login, firstName, lastName, hashPass);
        this.udb = udb;
    }

    @Override
    public Role getRole() {
        return Role.ADMIN;
    }

    @Override
    public void interactWithDB(BankDatabase bdb, OrderDatabase odb) {
        int answer;
        while(true){
            printAdminMenu();
            answer = Auxiliary.getCheckedInt();

            switch (answer){
                case 1:
                    System.out.println("login");
                    String login = Auxiliary.getCheckedString();
                    System.out.println("first name");
                    String firstName = Auxiliary.getCheckedString();
                    System.out.println("last name");
                    String lastName = Auxiliary.getCheckedString();
                    System.out.println("password");
                    String password = Auxiliary.getCheckedString();

                    try {
                        udb.addUser(login, firstName, lastName, password, Role.CLIENT);
                    } catch (Exception e) {
                        System.err.println("sth happened");
                        System.err.println(e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("login of user to be deleted");
                    String loginToBeDeleted = Auxiliary.getCheckedString();

                    udb.delUser(loginToBeDeleted);
                    bdb.delAccount(loginToBeDeleted);
                    break;
                case 3:
                    Collection<User> users = udb.getAllUsers();
                    for (User user: users) {
                        System.out.println(user);
                        System.out.println(bdb.getAccountInfo(user.getLogin()));
                    }
                    break;
                case 4:
                    System.out.println("credit card id");
                    String creditCardID = Auxiliary.getCheckedString();
                    printBlockMenu();
                    int choice = Auxiliary.getCheckedInt();

                    try {
                        switch (choice) {
                            case 1:
                                bdb.setCreditCardBlocked(creditCardID, true);
                                break;
                            case 2:
                                bdb.setCreditCardBlocked(creditCardID, false);
                                break;
                            default:
                                System.err.println("mischoice");
                        }
                    } catch (IncorrectCreditCardException e){
                        System.err.println("there is no credit card corresponding to this number");
                    }
                    break;
                case 5:
                    System.out.println("Old password");
                    String oldPassword = Auxiliary.getCheckedString();
                    System.out.println("New password");
                    String newPassword= Auxiliary.getCheckedString();

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

    private static void printAdminMenu(){
        System.out.println("1.\tAdd user_database");
        System.out.println("2.\tDelete user_database");
        System.out.println("3.\tPrint all users");
        System.out.println("4.\tBlock credit card");
        System.out.println("5.\tChange password");
        System.out.println("0.\tLogout");
    }

    private static void printBlockMenu(){
        System.out.println("1.\tBlock");
        System.out.println("2.\tUnlock");
    }
}

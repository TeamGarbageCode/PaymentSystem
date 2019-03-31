package payment;

import payment.databases.bank_database.BankDatabase;
import payment.databases.order_database.OrderDatabase;
import payment.databases.user_database.UserDatabase;
import payment.users.User;

import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Main {

    private static void printMenu(){
        System.out.println("1. Login");
        System.out.println("0. Exit");
    }

    public static void main(String[] args) {

        final Path rootDir = FileSystems.getDefault().getPath("src/lab4/");

        String udbUrl = "udb";
        String bdbUrl = "bdb";
        String odbUrl = "odb";

        try{
            UserDatabase udb = new UserDatabase(udbUrl, "admin", "admin");
            Test.fillUDB(udb);

            BankDatabase bdb = new BankDatabase(bdbUrl, "admin", "admin");
            Test.fillBDB(bdb);

            OrderDatabase odb = new OrderDatabase(odbUrl, "admin", "admin");
            Test.fillODB(odb);

            int answer;
            int attempt = 0;
            do {
                printMenu();
                answer = Auxilary.getCheckedInt();

                switch (answer){
                    case 1:
                        System.out.println("Login");
                        String login = Auxilary.getCheckedString();
                        System.out.println("Password");
                        String password = Auxilary.getCheckedString();

                        User user = udb.signIn(login, password);
                        if(user != null){
                            attempt = 0;
                            user.interactWithDB(bdb, odb);
                        } else{
                            System.err.println("incorrect login or password");
                            ++attempt;
                        }

                        if(attempt >= 3){
                            System.err.println("You have mistype 3 times");
                            System.err.println("Programme will exit");
                        }

                        break;
                    case 0:
                        break;
                    default:
                        System.err.println("You have made a mischoice");
                }
            }while(answer != 0);
        }catch (Exception e){
            System.err.println("some exception was happened");
            System.err.println(e.getMessage());
        }

    }

}
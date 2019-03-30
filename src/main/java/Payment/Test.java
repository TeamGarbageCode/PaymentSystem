package Payment;

import Payment.databases.BankDatabase;
import Payment.databases.OrderDatabase;
import Payment.databases.UserDatabase;
import Payment.users.Role;

import java.util.ArrayList;

public abstract class Test {

    public static void fillUDB(UserDatabase udb){
        try {
            udb.addUser("admin", "admin", "", "admin", Role.ADMIN);

            udb.addUser("tim","tim", "", "1111", Role.CLIENT);
            udb.addUser("dim","dim", "", "2222", Role.CLIENT);
            udb.addUser("jim","jim", "", "3333", Role.CLIENT);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void fillODB(OrderDatabase odb){
        odb.getAllOrders().put("tim", new ArrayList<>());
        odb.getAllOrders().put("dim", new ArrayList<>());
        odb.getAllOrders().put("jim", new ArrayList<>());

        odb.addOrder("tim", new Order("testid1", "tim order 1", "10.10") );
        odb.addOrder("tim", new Order("testid2", "tim order 2", "12.10") );
        odb.addOrder("dim", new Order("testid3", "dim order 1", "1.10") );
        odb.addOrder("dim", new Order("testid4", "dim order 2", "120.10") );
        odb.addOrder("dim", new Order("testid5", "dim order 3", "20.10") );
        odb.addOrder("jim", new Order("testid6", "jim order 1", "50") );
    }

    public static void fillBDB(BankDatabase bdb){

    }
}
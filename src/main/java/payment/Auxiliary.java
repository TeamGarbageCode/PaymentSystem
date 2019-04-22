package payment;

import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public abstract class Auxiliary {

    public static String getCheckedString(){
        Scanner scanner = new Scanner(new InputStreamReader(System.in));

        String result;
        boolean correct = true;

        do {
            result = scanner.next();

            System.out.println("you have entered: " + result);
            System.out.println("is it correct?\n" +
                    "1. Yes\n" +
                    "2.  No");
            if(getCheckedInt() == 2){
                System.err.println("some error happened");
                System.err.println("please retype");
                correct = false;
            }
        }while(!correct);

        return result;
    }

    public static int getCheckedInt(){
        Scanner scanner = new Scanner(new InputStreamReader(System.in));

        int result = 0;
        boolean ok = false;

        do{
            try {
                result = scanner.nextInt();
                ok = true;
            } catch (Exception e) {
                System.err.println("some error happened");
                System.err.println(e.getMessage());
                System.err.println("please retype");
            }
        }while(!ok);

        return result;
    }

    public static byte[] toSHA1(byte[] src){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        }
        catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md.digest(src);
    }

    public static String byteArrayToHexString(byte[] b) {
        StringBuilder result = new StringBuilder();
        for (byte b1 : b) {
            result.append(Integer.toString((b1 & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }

}

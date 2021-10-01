package banking.transactions.idGen;

import java.util.Random;

public class IdGenerator {

    public static String idGen(String stringId){

        Random randomNumber = new Random();
        long nextLong = randomNumber.nextLong();
        return stringId + "" +nextLong;
    }

}

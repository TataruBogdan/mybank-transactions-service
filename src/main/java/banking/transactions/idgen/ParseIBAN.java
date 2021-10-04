package banking.transactions.idgen;


import banking.commons.dto.TransactionDTO;
import banking.transactions.model.Transaction;

import static banking.commons.dto.types.AccountType.CURRENT;
import static banking.commons.dto.types.AccountType.DEPOSIT;

public class ParseIBAN {

    private static final String accountCurrent = "CURR";
    private static final String accountDeposit = "DEP";
    private static final String accountLoan = "LOAN";

    // parsing the IBAN that is passes into the transaction

    public static void parseFromStringIban(String accountIban, TransactionDTO transaction){

        int specChar = accountIban.indexOf("-");
        String subString = null;
        if (specChar != -1) {
            subString = accountIban.substring(0, specChar);
        }
        if (subString != null && subString.equals(accountCurrent)) {
                transaction.setFromAccountType(CURRENT);
            } else if (subString != null && subString.equals(accountDeposit) ){
                transaction.setFromAccountType(DEPOSIT);
            } else if (subString != null && subString.equals(accountLoan) ){
                transaction.setFromAccountType(DEPOSIT);
            } else {
            System.out.println("IBAN " + accountIban + "not good. Please insert another IBAN");
        }

    }

    public static void parseFromStringIban(String accountIban, Transaction transaction){

        int specChar = accountIban.indexOf("-");
        String subString = null;
        if (specChar != -1) {
            subString = accountIban.substring(0, specChar);
        }
        if (subString != null) {
            if (subString.equals(accountCurrent) ) {
                transaction.setFromAccountType(CURRENT);
            } else if (subString.equals(accountDeposit) ){
                transaction.setFromAccountType(DEPOSIT);
            } else if (subString.equals(accountLoan) ){
                transaction.setFromAccountType(DEPOSIT);
            }
        } else{
            System.out.println(" IBAN not good");
        }
    }

    public static void parseToStringIban(String accountIban, TransactionDTO transaction){

        int specChar = accountIban.indexOf("-");
        String subString = null;
        if (specChar != -1) {
            subString = accountIban.substring(0, specChar);
        }
        if (subString != null) {
            if (subString.equals(accountCurrent) ) {
                transaction.setToAccountType(CURRENT);
            } else if (subString.equals(accountDeposit) ){
                transaction.setToAccountType(DEPOSIT);
            } else if (subString.equals(accountLoan) ){
                transaction.setToAccountType(DEPOSIT);
            }
        } else{
            System.out.println(" IBAN not good");
        }
    }

    public static void parseToStringIban(String accountIban, Transaction transaction){

        int specChar = accountIban.indexOf("-");
        String subString = null;
        if (specChar != -1) {
            subString = accountIban.substring(0, specChar);
        }
        if (subString != null) {
            if (subString.equals(accountCurrent) ) {
                transaction.setToAccountType(CURRENT);
            } else if (subString.equals(accountDeposit) ){
                transaction.setToAccountType(DEPOSIT);
            } else if (subString.equals(accountLoan) ){
                transaction.setToAccountType(DEPOSIT);
            }
        } else{
            System.out.println(" IBAN not good");
        }
    }

}

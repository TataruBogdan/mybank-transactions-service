package banking.transactions.idgen;

import banking.commons.dto.types.AccountType;

public class IbanUtils {

    // parse the account Iban - takes first characters up to the special character ("-")
    // and return the type of the account -
    //
    // send the result back to AccountType.parseIbanType() which is creating an AccountType
    // enum made from ibanType and the microservice

    public static AccountType parseTypeStringIban(String accountIban) {
        int specChar = accountIban.indexOf("-");
        if (specChar < 0) {
            return null;
        }

        return AccountType.parseIbanType(accountIban.substring(0, specChar));
    }

}

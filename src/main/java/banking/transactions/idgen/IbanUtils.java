package banking.transactions.idgen;

import banking.commons.dto.types.AccountType;

public class IbanUtils {

    public static AccountType parseTypeStringIban(String accountIban) {
        int specChar = accountIban.indexOf("-");
        if (specChar < 0) {
            return null;
        }

        return AccountType.parseIbanType(accountIban.substring(0, specChar));
    }

}

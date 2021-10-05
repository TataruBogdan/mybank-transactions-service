package banking.transactions.rest.client;

import banking.commons.dto.AccountLoanDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class AccountLoanRestClient {

    @Autowired
    private RestTemplate restTemplate;

    public AccountLoanDTO getAccountLoanByIban(String iban) {
        AccountLoanDTO accountLoanDTO = restTemplate.getForObject("http://localhost:8400/accounts-loan/" + iban, AccountLoanDTO.class);

        return  accountLoanDTO;
    }
}

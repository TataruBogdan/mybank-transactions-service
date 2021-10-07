package banking.transactions.rest.client;

import banking.commons.dto.AccountLoanDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountLoanRestClient {

    @Autowired
    private RestTemplate loanRestTemplate;

    public AccountLoanDTO getAccountLoanByIban(String iban) {
        AccountLoanDTO accountLoanDTO = loanRestTemplate.getForObject("http://localhost:8400/accounts-loan/" + iban, AccountLoanDTO.class);

        return  accountLoanDTO;
    }
}

package banking.transactions.rest.client;

import banking.commons.dto.AccountLoanDTO;
import banking.transactions.config.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountLoanRestClient {

    @Autowired
    private RestTemplate loanRestTemplate;

    @Autowired
    private ApplicationProperties applicationProperties;

    public AccountLoanDTO getAccountLoanByIban(String iban) {
        AccountLoanDTO accountLoanDTO = loanRestTemplate.getForObject(applicationProperties.getAccountsDepositUrl() + iban,
                AccountLoanDTO.class);

        return  accountLoanDTO;
    }
}

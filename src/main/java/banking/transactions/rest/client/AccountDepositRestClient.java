package banking.transactions.rest.client;

import banking.commons.dto.AccountDepositDTO;
import banking.transactions.config.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountDepositRestClient {

    @Autowired
    private RestTemplate depositRestTemplate;

    @Autowired
    private ApplicationProperties applicationProperties;

    public AccountDepositDTO getAccountDepositByIban(String iban){
        AccountDepositDTO accountDepositDTO = depositRestTemplate.getForObject(applicationProperties.getAccountsDepositUrl() + iban,
                AccountDepositDTO.class);

        return accountDepositDTO;
    }
}

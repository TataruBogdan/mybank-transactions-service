package banking.transactions.rest.client;

import banking.commons.dto.AccountCurrentDTO;
import banking.transactions.config.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountCurrentRestClient {

    @Autowired
    private RestTemplate currentRestTemplate;

    @Autowired
    private ApplicationProperties applicationProperties;

    public AccountCurrentDTO getAccountCurrentByIban(String iban){
        AccountCurrentDTO accountCurrentDTO = currentRestTemplate.getForObject(applicationProperties.getAccountsCurrentUrl() + iban,
                AccountCurrentDTO.class);

        return accountCurrentDTO;
    }


}

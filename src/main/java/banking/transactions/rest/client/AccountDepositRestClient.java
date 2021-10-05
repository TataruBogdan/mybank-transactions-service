package banking.transactions.rest.client;

import banking.commons.dto.AccountDepositDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class AccountDepositRestClient {

    @Autowired
    private RestTemplate restTemplate;

    public AccountDepositDTO getAccountDepositByIban(String iban){
        AccountDepositDTO accountDepositDTO = restTemplate.getForObject("http://localhost:8300/accounts-deposit/" + iban, AccountDepositDTO.class);

        return accountDepositDTO;
    }
}

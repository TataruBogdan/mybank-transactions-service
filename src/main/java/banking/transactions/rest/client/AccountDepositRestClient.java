package banking.transactions.rest.client;

import banking.commons.dto.AccountDepositDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountDepositRestClient {

    @Autowired
    private RestTemplate depositRestTemplate;

    public AccountDepositDTO getAccountDepositByIban(String iban){
        AccountDepositDTO accountDepositDTO = depositRestTemplate.getForObject("http://localhost:8300/accounts-deposit/" + iban, AccountDepositDTO.class);

        return accountDepositDTO;
    }
}

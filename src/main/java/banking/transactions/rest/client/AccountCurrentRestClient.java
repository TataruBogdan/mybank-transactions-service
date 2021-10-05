package banking.transactions.rest.client;


import banking.commons.dto.AccountCurrentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


// este un servicu restClient pentru fiecre microserviciu in parte adica AccountCurrentRestClient, AccountLoanRestClient
@Service
public class AccountCurrentRestClient {

    @Autowired
    private RestTemplate restTemplate;

    public AccountCurrentDTO getAccountCurrentByIban(String iban){
        AccountCurrentDTO accountCurrentDTO = restTemplate.getForObject("http://localhost:8200/accounts-current/" + iban, AccountCurrentDTO.class);

        return accountCurrentDTO;
    }








}

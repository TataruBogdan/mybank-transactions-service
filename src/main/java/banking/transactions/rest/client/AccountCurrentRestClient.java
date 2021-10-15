package banking.transactions.rest.client;

import banking.commons.dto.AccountCurrentDTO;
import banking.transactions.config.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//all rest clients must have URL and path in application yaml
//create in application.yaml a configuration rest-client - URL resource for each application

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

    //PATCH mapping in AccountCurrentController -                              requestBody?
    public ResponseEntity<AccountCurrentDTO> debitAccountCurrent(String iban, Double amount) {

        String body = "{\"fromIban\":\"CURR-\",\"amount\":amount}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<AccountCurrentDTO> accountCurrentFromIbanResponseEntity = currentRestTemplate.exchange("/account-current/debit/" + iban, HttpMethod.PATCH, requestEntity, AccountCurrentDTO.class);
        return accountCurrentFromIbanResponseEntity;
    }

    public ResponseEntity<AccountCurrentDTO> creditAccountCurrent(String iban, Double amount) {

        String body = "{\"toIban\":\"CURR-\",\"amount\":amount}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<AccountCurrentDTO> accountCurrentToIbanResponseEntity = currentRestTemplate.exchange("/account-current/credit/" + iban,HttpMethod.PATCH, requestEntity, AccountCurrentDTO.class);

        return accountCurrentToIbanResponseEntity;
    }



    // change - Create new microservice mybank-scheduler - execute debit, credit account and status
//    public ResponseEntity<AccountCurrentDTO> debitAccountCurrent(String iban, AmountDTO amountDTO){
//
//        String body = "{\"iban\":\"CURR-\",\"amountDTO\":\"amount\"}";
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<String> requestEntity = new HttpEntity<>(body,headers);
//
//        ResponseEntity<AccountCurrentDTO> accountCurrentDebitDTO =
//                currentRestTemplate.exchange(applicationProperties.getAccountCurrentDebitUrl() + iban, HttpMethod.PATCH, requestEntity, AccountCurrentDTO.class);
//
//
//        return accountCurrentDebitDTO;
//    }


}

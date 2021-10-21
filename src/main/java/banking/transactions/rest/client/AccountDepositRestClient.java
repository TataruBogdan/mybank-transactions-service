package banking.transactions.rest.client;

import banking.commons.dto.AccountDepositDTO;
import banking.commons.dto.IndividualDTO;
import banking.commons.dto.types.ArgsDTO;
import banking.transactions.config.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Optional;

//all rest clients must have URL and path in application yaml
//create in application.yaml a configuration rest-client - URL resource for each application

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


    public ResponseEntity<AccountDepositDTO> createNewAccountDepositForIndividual(IndividualDTO individualId, int months, Double amount){

        ArgsDTO args = new ArgsDTO();
        args.setAmount(amount);
        args.setMonths(months);

        HttpHeaders headers = new HttpHeaders();
        headers. setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<ArgsDTO> requestEntity = new HttpEntity<>(args, headers);

        AccountDepositDTO accountDepositDTO = depositRestTemplate.postForObject("/account-debit/" + individualId, requestEntity, AccountDepositDTO.class);

        return ResponseEntity.of(Optional.of(accountDepositDTO));
    }


}

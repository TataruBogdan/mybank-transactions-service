package banking.transactions.rest;


import banking.commons.dto.AccountCurrentDTO;
import banking.commons.dto.IndividualDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestClient {

    @Autowired
    private RestTemplate restTemplate;

    public IndividualDTO getIndividualById(Integer id){

        IndividualDTO individualDTO = restTemplate.getForObject("http://localhost:8100/individuals/" + id, IndividualDTO.class);

        return individualDTO;
    }


    public AccountCurrentDTO getAccountCurrentByIban(String iban){

        AccountCurrentDTO accountCurrentDTO = restTemplate.getForObject("http://localhost:8200/accounts-current/" + iban, AccountCurrentDTO.class);

        return accountCurrentDTO;


    }

}

package banking.transactions.rest.client;

import banking.commons.dto.IndividualDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class AccountIndividualRestClient {

    @Autowired
    private RestTemplate restTemplate;

    public IndividualDTO getIndividualById(Integer id){
        IndividualDTO individualDTO = restTemplate.getForObject("http://localhost:8100/individuals/" + id, IndividualDTO.class);

        return individualDTO;
    }
}

package banking.transactions.rest.client;

import banking.commons.dto.IndividualDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountIndividualRestClient {

    @Autowired
    private RestTemplate individualRestTemplate;

    public IndividualDTO getIndividualById(Integer id){
        IndividualDTO individualDTO = individualRestTemplate.getForObject("http://localhost:8100/individuals/" + id, IndividualDTO.class);

        return individualDTO;
    }
}

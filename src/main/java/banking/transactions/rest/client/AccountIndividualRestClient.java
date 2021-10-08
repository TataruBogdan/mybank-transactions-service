package banking.transactions.rest.client;

import banking.commons.dto.IndividualDTO;
import banking.transactions.config.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountIndividualRestClient {

    @Autowired
    private RestTemplate individualRestTemplate;

    @Autowired
    private ApplicationProperties applicationProperties;

    public IndividualDTO getIndividualById(Integer id){
        IndividualDTO individualDTO = individualRestTemplate.getForObject(applicationProperties.getIndividualUrl() + id,
                IndividualDTO.class);

        return individualDTO;
    }
}

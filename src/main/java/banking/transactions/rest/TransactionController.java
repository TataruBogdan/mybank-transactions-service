package banking.transactions.rest;


import banking.commons.dto.IndividualDTO;
import banking.transactions.dto.AmountDTO;
import banking.transactions.dto.TransactionDTO;
import banking.transactions.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;


    @Autowired
    private IndividualRestClient individualRestClient;

    @PostMapping(value = "/fromIban/{fromIban}/toIban/{toIban}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionDTO> createTransaction(@PathVariable("fromIban") String fromIban,
                                                            @PathVariable("toIban") String toIban,
                                                            @RequestBody AmountDTO amount){

        TransactionDTO transaction = transactionService.createTransaction(fromIban, toIban, amount.getAmount());

        //not working - cannot
        IndividualDTO fromIndividualById = individualRestClient.getIndividualById(transaction.getFromIndividualId());
        IndividualDTO toIndividualById = individualRestClient.getIndividualById(transaction.getToIndividualId());
        transaction.setFromIndividualDTO(fromIndividualById);
        transaction.setToIndividualDTO(toIndividualById);

        return ResponseEntity.ok(transaction);
    }


//    public ResponseEntity<>






















    @GetMapping("/fromIban/{fromIban}/toIban/{toIban}")
    public ResponseEntity<TransactionDTO> retrieveTransaction(@PathVariable("fromIban") String fromIban, @PathVariable("toIban") String toIban) {

        TransactionDTO transactionByFromAndTo = transactionService.findTransactionByFromAndTo(fromIban, toIban);



        return ResponseEntity.ok(transactionByFromAndTo);
    }

}

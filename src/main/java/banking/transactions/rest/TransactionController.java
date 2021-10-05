package banking.transactions.rest;


import banking.commons.dto.AccountCurrentDTO;
import banking.commons.dto.TransactionDTO;
import banking.transactions.dto.AmountDTO;
import banking.transactions.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private RestClient restClient;

    @PostMapping(value = "/fromIban/{fromIban}/toIban/{toIban}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionDTO> createTransaction(@PathVariable("fromIban") String fromIban,
                                                            @PathVariable("toIban") String toIban,
                                                            @RequestBody AmountDTO amount) {

        TransactionDTO transaction = transactionService.createTransaction(fromIban, toIban, amount.getAmount());

        //HARD CODDED - check to witch account type IBAN belongs
        AccountCurrentDTO accountCurrentFromByIban = restClient.getAccountCurrentByIban(fromIban);
        AccountCurrentDTO accountCurrentToByIban = restClient.getAccountCurrentByIban(toIban);

        transaction.setToIban(accountCurrentToByIban.getIban());
        transaction.setFromIban(accountCurrentFromByIban.getIban());

        transaction.setToIndividualId(accountCurrentToByIban.getIndividualId());
        transaction.setFromIndividualId(accountCurrentFromByIban.getIndividualId());

        transaction.setToIndividualDTO(accountCurrentToByIban.getIndividual());
        transaction.setFromIndividualDTO(accountCurrentFromByIban.getIndividual());

        return ResponseEntity.ok(transaction);
    }


    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionDTO> retrieveTransaction(@PathVariable("transactionId") String transactionId) {

        Optional<TransactionDTO> transactionByIBAN = transactionService.getTransactionById(transactionId);

        if (transactionByIBAN.isPresent()) {

            AccountCurrentDTO accountCurrentToByIban = restClient.getAccountCurrentByIban(transactionByIBAN.get().getToIban());
            AccountCurrentDTO accountCurrentFromByIban = restClient.getAccountCurrentByIban(transactionByIBAN.get().getFromIban());

            transactionByIBAN.get().setToIndividualId(accountCurrentToByIban.getIndividualId());
            transactionByIBAN.get().setFromIndividualId(accountCurrentFromByIban.getIndividualId());

            transactionByIBAN.get().setToIndividualDTO(accountCurrentToByIban.getIndividual());
            transactionByIBAN.get().setFromIndividualDTO(accountCurrentFromByIban.getIndividual());

            return ResponseEntity.ok(transactionByIBAN.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

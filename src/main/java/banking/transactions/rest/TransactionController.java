package banking.transactions.rest;


import banking.commons.dto.*;
import banking.transactions.dto.AmountDTO;
import banking.transactions.rest.client.AccountCurrentRestClient;
import banking.transactions.rest.client.AccountDepositRestClient;
import banking.transactions.rest.client.AccountIndividualRestClient;
import banking.transactions.rest.client.AccountLoanRestClient;
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
    private AccountCurrentRestClient accountCurrentRestClient;

    @Autowired
    private AccountIndividualRestClient accountIndividualRestClient;

    @Autowired
    private AccountDepositRestClient accountDepositRestClient;

    @Autowired
    private AccountLoanRestClient accountLoanRestClient;

    @PostMapping(value = "/fromIban/{fromIban}/toIban/{toIban}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionDTO> createTransaction(@PathVariable("fromIban") String fromIban,
                                                            @PathVariable("toIban") String toIban,
                                                            @RequestBody AmountDTO amount) {

        TransactionDTO transaction = transactionService.createTransaction(fromIban, toIban, amount.getAmount());

        //HARD CODDED - check to witch account type IBAN belongs
        //TODO - need to check the IBAN and see what kind of IBAN is then pass it to transaction and save it
        //TODO String microservice = transaction.getFromAccountType().getMicroservice();

        IndividualDTO  fromIndividualDTO = null;

        switch (transaction.getFromAccountType()){
            case CURRENT: {
                AccountCurrentDTO accountFromByIban = accountCurrentRestClient.getAccountCurrentByIban(fromIban);
                fromIndividualDTO = accountFromByIban.getIndividual();
                break;
            }
            case LOAN: {
                AccountLoanDTO accountLoanByIban = accountLoanRestClient.getAccountLoanByIban(fromIban);
                fromIndividualDTO = accountLoanByIban.getIndividualDTO();
            }
            case DEPOSIT: {
                AccountDepositDTO accountDepositByIban = accountDepositRestClient.getAccountDepositByIban(fromIban);
                fromIndividualDTO = accountDepositByIban.getIndividual();
            }
        }

        return ResponseEntity.ok(transaction);
    }


    //TODO this will be send to account service
    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionDTO> retrieveTransaction(@PathVariable("transactionId") String transactionId) {

        Optional<TransactionDTO> transactionByIBAN = transactionService.getTransactionById(transactionId);

        if (transactionByIBAN.isPresent()) {
            // TODO - TREBUIE SA VERIFIC CE FEL DE CONT ESTE CEL DIN TRANZACTIE : CURR, DEP, LOAN
            //TODO - SI APOI SA APELEZ SERVICIUL CORESPUNZATOR

            AccountCurrentDTO accountCurrentFromByIban = accountCurrentRestClient.getAccountCurrentByIban(transactionByIBAN.get().getFromIban());
            AccountCurrentDTO accountCurrentToByIban = accountCurrentRestClient.getAccountCurrentByIban(transactionByIBAN.get().getToIban());


//            transactionByIBAN.get().setToIndividualId(accountCurrentToByIban.getIndividualId());
//            transactionByIBAN.get().setFromIndividualId(accountCurrentFromByIban.getIndividualId());

            transactionByIBAN.get().setToIndividualDTO(accountCurrentToByIban.getIndividual());
            transactionByIBAN.get().setFromIndividualDTO(accountCurrentFromByIban.getIndividual());

            return ResponseEntity.ok(transactionByIBAN.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @GetMapping("/individual/{individualId}")
//    public ResponseEntity<List<TransactionDTO>> retrieveAllTransactionIndividualID(@PathVariable("individualId") int individualId){
//
//        List<TransactionDTO> transactionByIndividualId = transactionService.findByToIndividualId(individualId);
//
//        if (transactionByIndividualId.isEmpty()){
//            return ResponseEntity.notFound().build();
//        }
//
//        IndividualDTO individualById = restClient.getIndividualById(individualId);
//        for (TransactionDTO transactionDTO : transactionByIndividualId){
//            transactionDTO.setFromIndividualDTO(individualById);
//            transactionDTO.setToIndividualDTO(individualById);
//        }
//
//        return ResponseEntity.ok(transactionByIndividualId);
//
//    }

}

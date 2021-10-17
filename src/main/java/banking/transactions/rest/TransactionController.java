package banking.transactions.rest;

import banking.commons.dto.*;
import banking.commons.dto.types.TransactionStatus;
import banking.transactions.rest.client.AccountCurrentRestClient;
import banking.transactions.rest.client.AccountDepositRestClient;
import banking.transactions.rest.client.AccountIndividualRestClient;
import banking.transactions.rest.client.AccountLoanRestClient;
import banking.transactions.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static banking.transactions.idgen.IbanUtils.parseTypeStringIban;
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

        IndividualDTO fromIndividualDTO = null;
        IndividualDTO toIndividualDTO = null;

        switch (parseTypeStringIban(fromIban)){
            case CURRENT: {
                AccountCurrentDTO accountCurrentFromByIban = accountCurrentRestClient.getAccountCurrentByIban(fromIban);
                fromIndividualDTO = accountCurrentFromByIban.getIndividual();
                //ResponseEntity<AccountCurrentDTO> accountCurrentDTO = accountCurrentRestClient.debitAccountCurrent(fromIban, amount);
                break;
            }
            case LOAN: {
                AccountLoanDTO accountLoanByIban = accountLoanRestClient.getAccountLoanByIban(fromIban);
                fromIndividualDTO = accountLoanByIban.getIndividualDTO();
                break;
            }
            case DEPOSIT: {
                AccountDepositDTO accountDepositByIban = accountDepositRestClient.getAccountDepositByIban(fromIban);
                fromIndividualDTO = accountDepositByIban.getIndividual();
                break;
            }
        }
        switch (parseTypeStringIban(toIban)){
            case CURRENT: {
                AccountCurrentDTO accountCurrentByIban = accountCurrentRestClient.getAccountCurrentByIban(toIban);
                toIndividualDTO = accountCurrentByIban.getIndividual();
                break;
            }
            case LOAN: {
                AccountLoanDTO accountLoanByIban = accountLoanRestClient.getAccountLoanByIban(toIban);
                toIndividualDTO = accountLoanByIban.getIndividualDTO();
                break;
            }
            case DEPOSIT: {
                AccountDepositDTO accountDepositByIban = accountDepositRestClient.getAccountDepositByIban(toIban);
                toIndividualDTO = accountDepositByIban.getIndividual();
                break;
            }
        }

        TransactionDTO transaction = transactionService.createTransaction(fromIban, toIban, fromIndividualDTO.getId(), toIndividualDTO.getId(), amount.getAmount());
        transaction.setFromIndividualDTO(fromIndividualDTO);
        transaction.setToIndividualDTO(toIndividualDTO);
        return ResponseEntity.ok(transaction);
    }


//    //TODO this will be send to account service
//    @GetMapping("/{transactionId}")
//    public ResponseEntity<TransactionDTO> retrieveTransaction(@PathVariable("transactionId") String transactionId) {
//
//        Optional<TransactionDTO> transactionByIBAN = transactionService.getTransactionById(transactionId);
//
//        if (transactionByIBAN.isPresent()) {
//            // TODO - TREBUIE SA VERIFIC CE FEL DE CONT ESTE CEL DIN TRANZACTIE : CURR, DEP, LOAN
//            //TODO - SI APOI SA APELEZ SERVICIUL CORESPUNZATOR
//
//            AccountCurrentDTO accountCurrentFromByIban = accountCurrentRestClient.getAccountCurrentByIban(transactionByIBAN.get().getFromIban());
//            AccountCurrentDTO accountCurrentToByIban = accountCurrentRestClient.getAccountCurrentByIban(transactionByIBAN.get().getToIban());
//
//
//
//            transactionByIBAN.get().setFromIndividualDTO(accountCurrentFromByIban.getIndividual());
//            transactionByIBAN.get().setToIndividualDTO(accountCurrentToByIban.getIndividual());
//
//
//            return ResponseEntity.ok(transactionByIBAN.get());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    //return only id transaction with status
    @GetMapping(value = "/search-by-status", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getAllTransactionsWithStatus(@RequestBody TransactionSearchInputDTO statuses) {

        //return list of ids of transactions with status
        List<TransactionDTO> allTransactionsFindByStatus = transactionService.getAllTransactionsFindByTransactionsStatus(statuses.getStatusList());

        List<String> transactionsWithIds = new ArrayList<>();

        for (TransactionDTO transactionsIDs: allTransactionsFindByStatus) {
            String transactionId = transactionsIDs.getTransactionId();
            transactionsWithIds.add(transactionId);
        }

        if (transactionsWithIds.isEmpty()) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(allTransactionsFindByStatus);
        }

        return ResponseEntity.ok(transactionsWithIds);
    }


    //??? /transactions/transactionId ? - e ok
    @PatchMapping("/execute/{transactionId}")
    public Optional<TransactionDTO> executeTransaction(@PathVariable("transactionId") String transactionId){
    //TODO - incarcam TransactionsDTO
        Optional<TransactionDTO> transactionById = transactionService.getTransactionById(transactionId);
        //debit from iban
        String transactionFromIban = transactionById.get().getFromIban();
        Double transactionAmount = transactionById.get().getTransactionAmount();

        ResponseEntity<AccountCurrentDTO> debitAccountCurrent = accountCurrentRestClient.debitAccountCurrent(transactionFromIban, transactionAmount);

        //credit to iban
        String transactionToIban = transactionById.get().getToIban();
        ResponseEntity<AccountCurrentDTO> creditAccountCurrent = accountCurrentRestClient.creditAccountCurrent(transactionToIban, transactionAmount);

        //modificam transaction status finished
        transactionById.get().setStatus(TransactionStatus.FINISHED);

        return transactionById;


    }

}

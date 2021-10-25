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
    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionDTO> retrieveTransaction(@PathVariable("transactionId") String transactionId) {

        Optional<TransactionDTO> transactionByIBAN = transactionService.getTransactionById(transactionId);

        if (transactionByIBAN.isPresent()) {
            // TODO - TREBUIE SA VERIFIC CE FEL DE CONT ESTE CEL DIN TRANZACTIE : CURR, DEP, LOAN
            //TODO - SI APOI SA APELEZ SERVICIUL CORESPUNZATOR

            AccountCurrentDTO accountCurrentFromByIban = accountCurrentRestClient.getAccountCurrentByIban(transactionByIBAN.get().getFromIban());
            AccountCurrentDTO accountCurrentToByIban = accountCurrentRestClient.getAccountCurrentByIban(transactionByIBAN.get().getToIban());



            transactionByIBAN.get().setFromIndividualDTO(accountCurrentFromByIban.getIndividual());
            transactionByIBAN.get().setToIndividualDTO(accountCurrentToByIban.getIndividual());


            return ResponseEntity.ok(transactionByIBAN.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //return only id transaction with status
    @PostMapping(value = "/search-by-status", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<SearchTransactionsResponseDTO> getAllTransactionsWithStatus(@RequestBody TransactionSearchInputDTO statuses) {

        //return list of ids of transactions with status
        List<String> allTransactionsIdsFindByTransactionsStatus = transactionService.getAllTransactionsFindByTransactionsStatus(statuses.getStatusList());

        SearchTransactionsResponseDTO searchTransactionsResponseDTO = new SearchTransactionsResponseDTO();
        searchTransactionsResponseDTO.setIds(allTransactionsIdsFindByTransactionsStatus);

        if (searchTransactionsResponseDTO.getIds().isEmpty()) {
            ResponseEntity.status(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(searchTransactionsResponseDTO);
    }


    //??? /transactions/transactionId ? - e ok
    @PatchMapping(value = "/execute/{transactionId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionDTO> executeTransaction(@PathVariable("transactionId") String transactionId){
        //TODO - fromIndividualDTO ; tondividualDTO - null !!!
        Optional<TransactionDTO> transactionById = transactionService.getTransactionById(transactionId);
        IndividualDTO fromIndividualDTO = transactionById.get().getFromIndividualDTO();
        Integer fromIndividualDTOId = fromIndividualDTO.getId();
        System.out.println(fromIndividualDTOId);
        IndividualDTO toIndividualDTO = transactionById.get().getToIndividualDTO();

        Double transactionAmount = transactionById.get().getTransactionAmount();

        String fromIban = transactionById.get().getFromIban();
        String toIban = transactionById.get().getToIban();


        switch (parseTypeStringIban(fromIban)) {
            case CURRENT: {
                ResponseEntity<AccountCurrentDTO> debitAccountCurrent = accountCurrentRestClient.debitAccountCurrent(fromIban, transactionAmount);
                // este corect asa ???? de verificat !!!
                debitAccountCurrent.getBody().setIndividual(fromIndividualDTO);
            }
        }
        switch (parseTypeStringIban(toIban)) {
            case CURRENT: {
                ResponseEntity<AccountCurrentDTO> creditAccountCurrent = accountCurrentRestClient.creditAccountCurrent(toIban, transactionAmount);
                creditAccountCurrent.getBody().setIndividual(toIndividualDTO);
            }

            //can only make a new deposit that will wait x months to maturation
            case DEPOSIT: {

                AccountDepositDTO accountDepositByIban = accountDepositRestClient.getAccountDepositByIban(toIban);
                int maturityMonths = accountDepositByIban.getMaturityMonths();
                ResponseEntity<AccountDepositDTO> newAccountDepositForIndividual = accountDepositRestClient.createNewAccountDepositForIndividual(toIndividualDTO, maturityMonths, transactionAmount);

            }
        }

        if (transactionById.isPresent()) {
           //modificam transaction status finished
           transactionById.get().setStatus(TransactionStatus.FINISHED);
            return ResponseEntity.status(HttpStatus.OK).body(transactionById.get());
       }
       return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

}

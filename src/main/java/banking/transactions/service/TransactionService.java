package banking.transactions.service;

import banking.commons.dto.TransactionDTO;
import banking.commons.dto.types.TransactionStatus;

import java.util.List;
import java.util.Optional;

public interface TransactionService {


    TransactionDTO createTransaction(String fromIban, String toIban, Integer fromId, Integer toId,
                                            double amount);

    Optional<TransactionDTO> getTransactionById(String transactionId);

    List<String> getAllTransactionsFindByTransactionsStatus(List<TransactionStatus> statuses);



}

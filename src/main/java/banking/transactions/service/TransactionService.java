package banking.transactions.service;

import banking.commons.dto.TransactionDTO;

import java.util.Optional;

public interface TransactionService {


    TransactionDTO createTransaction(String fromIban, String toIban,
                                    double amount);

    Optional<TransactionDTO> getTransactionById(String transactionId);

}

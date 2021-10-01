package banking.transactions.service;

import banking.transactions.dto.TransactionDTO;

public interface TransactionService {


    TransactionDTO createTransaction(String fromIban, String toIban,
                                    double amount);

    TransactionDTO findTransactionByFromAndTo(String fromIban, String toIban);

}

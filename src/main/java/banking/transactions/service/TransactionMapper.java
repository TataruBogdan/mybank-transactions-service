package banking.transactions.service;


import banking.transactions.dto.TransactionDTO;
import banking.transactions.model.Transaction;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionDTO transactionToDTO(Transaction transaction);

    Transaction toTransaction(TransactionDTO transactionDTO);

    List<TransactionDTO> listTransactionsDTO(List<Transaction> transactionList);
}

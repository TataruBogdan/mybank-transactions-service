package banking.transactions.service.impl;


import banking.transactions.dao.TransactionRepository;
import banking.transactions.dto.TransactionDTO;
import banking.transactions.model.Transaction;
import banking.transactions.service.TransactionMapper;
import banking.transactions.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

import static banking.transactions.model.TransactionStatus.NEW;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private final TransactionRepository transactionRepository;

    @Autowired
    private final TransactionMapper transactionMapper;


    @Override
    public TransactionDTO createTransaction(String fromIban, String toIban,
                                            double amount) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setFromIban(fromIban);
        transaction.setFromAccountType(transaction.getFromAccountType());
        transaction.setToIban(toIban);
        transaction.setToAccountType(transaction.getToAccountType());
        transaction.setTransactionAmount(amount);
        transaction.setTransactionTime(new Date());
        transaction.setStatus(NEW);

        Transaction savedTransaction = transactionRepository.save(transaction);
        return transactionMapper.transactionToDTO(savedTransaction);

    }

    @Override
    public TransactionDTO findTransactionByFromAndTo(String fromIban, String toIban) {

        TransactionDTO transactionByFromIbanAndToIban = transactionRepository.findByFromIbanAndToIban(fromIban, toIban);
        return transactionByFromIbanAndToIban;
    }

}

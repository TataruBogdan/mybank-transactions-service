package banking.transactions.service.impl;


import banking.transactions.dao.TransactionRepository;
import banking.commons.dto.TransactionDTO;
import banking.transactions.idgen.ParseIBAN;
import banking.transactions.model.Transaction;
import banking.transactions.service.TransactionMapper;
import banking.transactions.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static banking.commons.dto.types.TransactionStatus.NEW;

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
        ParseIBAN.parseFromStringIban(fromIban,transaction);
        transaction.setFromIban(fromIban);
        transaction.setToIban(toIban);
        ParseIBAN.parseToStringIban(toIban, transaction);
        transaction.setTransactionAmount(amount);
        transaction.setTransactionTime(new Date());
        transaction.setStatus(NEW);

        Transaction savedTransaction = transactionRepository.save(transaction);
        return transactionMapper.transactionToDTO(savedTransaction);

    }

    @Override
    public Optional<TransactionDTO> getTransactionById(String transactionId) {
        return transactionRepository.findById(transactionId)
                .map(transaction -> transactionMapper.transactionToDTO(transaction));


    }


}

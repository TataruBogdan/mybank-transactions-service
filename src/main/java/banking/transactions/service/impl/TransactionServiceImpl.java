package banking.transactions.service.impl;


import banking.commons.dto.TransactionDTO;
import banking.transactions.dao.TransactionRepository;
import banking.transactions.model.Transaction;
import banking.transactions.model.TransactionStatus;
import banking.transactions.service.TransactionMapper;
import banking.transactions.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static banking.transactions.idgen.IbanUtils.parseTypeStringIban;

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
        transaction.setFromAccountType(parseTypeStringIban(fromIban));
        transaction.setToIban(toIban);
        transaction.setToAccountType(parseTypeStringIban(toIban));
        transaction.setTransactionAmount(amount);
        transaction.setTransactionTime(LocalDateTime.now());
        transaction.setStatus(TransactionStatus.NEW);

        Transaction savedTransaction = transactionRepository.save(transaction);
        return transactionMapper.transactionToDTO(savedTransaction);

    }

    @Override
    public Optional<TransactionDTO> getTransactionById(String transactionId) {
        return transactionRepository.findById(transactionId)
                .map(transaction -> transactionMapper.transactionToDTO(transaction));


    }


}

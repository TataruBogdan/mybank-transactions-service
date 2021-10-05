package banking.transactions.dao;

import banking.commons.dto.TransactionDTO;
import banking.transactions.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository  extends JpaRepository<Transaction, String> {

//    List<Transaction> findByIndividualId(int individualId);

    TransactionDTO findByFromIbanAndToIban(String fromIban, String toIban);


}

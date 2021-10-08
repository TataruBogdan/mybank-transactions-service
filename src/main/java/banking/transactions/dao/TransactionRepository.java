package banking.transactions.dao;

import banking.commons.dto.TransactionDTO;
import banking.commons.dto.types.AccountType;
import banking.transactions.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository  extends JpaRepository<Transaction, String> {

//    List<Transaction> findByIndividualId(int individualId);

                                        //Change from String
    TransactionDTO findByFromIbanAndToIban(AccountType fromIban, AccountType toIban);




}

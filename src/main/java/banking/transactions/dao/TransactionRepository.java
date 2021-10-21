package banking.transactions.dao;


import banking.commons.dto.types.AccountType;
import banking.transactions.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository  extends JpaRepository<Transaction, String> {


    Transaction findByFromIbanAndToIban(AccountType fromIban, AccountType toIban);

    @Query(value = "SELECT transaction_id FROM transaction where transaction_status in (:statuses)", nativeQuery = true )
    List<String> findTransactionByTransactionStatusList(List<String> statuses);



}

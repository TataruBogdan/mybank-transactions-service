package banking.transactions.dao;

import banking.transactions.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepository  extends JpaRepository<Transaction, Integer> {
}

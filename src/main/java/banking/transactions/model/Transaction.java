package banking.transactions.model;


import banking.commons.transactions.model.AccountType;
import banking.commons.transactions.model.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    //todo - insert field in database
    @Id
    @Column(name = "transaction_id", nullable = false)
    private Integer transactionId;

    private String fromIban;

    @Enumerated(EnumType.STRING)
    private AccountType fromAccountType;

    private String toIban;

    @Enumerated(EnumType.STRING)
    private AccountType toAccountType;

    private Integer transactionAmount;

    @Column(name = "transaction_timestamp")
    private Date transactionTime;


    @Enumerated(EnumType.STRING)
    private TransactionStatus taxOperationType;

}

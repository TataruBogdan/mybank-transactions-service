package banking.transactions.model;
import banking.commons.dto.types.AccountType;
import banking.commons.dto.types.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Transaction {

    //TODO - insert field in database
    @Id
    @Column(name = "transaction_id")
    private String transactionId;

    private String fromIban;

    @Enumerated(EnumType.STRING)
    private AccountType fromAccountType;

    private String toIban;

    @Enumerated(EnumType.STRING)
    private AccountType toAccountType;

    private Double transactionAmount;

    @Column(name = "transaction_timestamp")
    private Date transactionTime;


    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_status")
    private TransactionStatus status;

}

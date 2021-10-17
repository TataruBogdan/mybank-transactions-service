package banking.transactions.model;

import banking.commons.dto.types.AccountType;
import banking.commons.dto.types.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Transaction {

    @Id
    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "from_individual_id")
    private Integer fromIndividualId;

    @JsonProperty("fromIban")
    private String fromIban;

    @Enumerated(EnumType.STRING)
    @Column(name = "from_account_type")
    private AccountType fromAccountType;

    @Column(name = "to_individual_id")
    private Integer toIndividualId;

    @JsonProperty("toIban")
    private String toIban;

    @Enumerated(EnumType.STRING)
    @Column(name = "to_Account_Type")
    private AccountType toAccountType;

    private Double transactionAmount;

    @Column(name = "transaction_timestamp")
    private LocalDateTime transactionTime;


    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_status")
    private TransactionStatus status;

}

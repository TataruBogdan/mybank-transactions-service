package banking.transactions.dto;


import banking.commons.dto.IndividualDTO;
import banking.transactions.model.AccountType;
import banking.transactions.model.TransactionStatus;
import lombok.Data;

import java.util.Date;

@Data
public class TransactionDTO {

    private String transactionId;
    private AccountType fromAccountType;
    private String toIban;
    private AccountType toAccountType;
    private Integer transactionAmount;
    private Date transactionTime;
    private TransactionStatus taxOperationType;
    private int toIndividualId;
    private IndividualDTO toIndividualDTO;
    private int fromIndividualId;
    private IndividualDTO fromIndividualDTO;



}

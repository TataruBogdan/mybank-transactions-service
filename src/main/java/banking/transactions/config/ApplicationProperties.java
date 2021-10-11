package banking.transactions.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rest-client")
@Data
public class ApplicationProperties {

    private String accountsLoanUrl;
    private String accountsCurrentUrl;
    private String accountCurrentDebitUrl;
    private String accountsDepositUrl;
    private String individualUrl;
}

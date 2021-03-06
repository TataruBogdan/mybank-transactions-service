package banking.transactions;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class TransactionApplication implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        SpringApplication.run(TransactionApplication.class, args);
    }

//    @Autowired
//    private TransactionRepository transactionRepository;


    @Override
    public void run(String... args) throws Exception {
//        logger.info("Insert transaction -> {}", transactionRepository.save(
//                new Transaction(321, "ABC123", AccountType.CURRENT, "BCD123", AccountType.CURRENT, 1000,
//                        new Date(), TransactionStatus.APPROVED)
//        ));
    }
}

package com.webapp.bankingportal.config;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.webapp.bankingportal.entity.Account;
import com.webapp.bankingportal.entity.Transaction;
import com.webapp.bankingportal.entity.TransactionType;
import com.webapp.bankingportal.entity.User;
import com.webapp.bankingportal.repository.AccountRepository;
import com.webapp.bankingportal.repository.TransactionRepository;
import com.webapp.bankingportal.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(UserRepository userRepository, AccountRepository accountRepository,
            TransactionRepository transactionRepository) {
        return args -> {
            if (userRepository.count() > 0) {
                System.out.println("Sample data already present, skipping initialization.");
                return;
            }
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            User u1 = new User();
            u1.setFirstName("Alice");
            u1.setLastName("Johnson");
            u1.setEmail("alice@example.com");
            u1.setPassword(encoder.encode("password123"));
            u1.setCountryCode("+91");
            u1.setPhoneNumber("9876543210");
            u1.setAddress("123 Main St, City");

            Account a1 = new Account();
            a1.setAccountNumber("ACC1001");
            a1.setAccountStatus("ACTIVE");
            a1.setBalance(1000.0);
            a1.setPin("1234");
            a1.setUser(u1);
            u1.setAccount(a1);

            User u2 = new User();
            u2.setFirstName("Bob");
            u2.setLastName("Smith");
            u2.setEmail("bob@example.com");
            u2.setPassword(encoder.encode("password123"));
            u2.setCountryCode("+91");
            u2.setPhoneNumber("9123456780");
            u2.setAddress("456 Side St, City");

            Account a2 = new Account();
            a2.setAccountNumber("ACC1002");
            a2.setAccountStatus("ACTIVE");
            a2.setBalance(2000.0);
            a2.setPin("4321");
            a2.setUser(u2);
            u2.setAccount(a2);

            userRepository.save(u1);
            userRepository.save(u2);

            Transaction t1 = new Transaction();
            t1.setAmount(100.0);
            t1.setTransactionType(TransactionType.DEBIT);
            t1.setTransactionDate(new Date());
            t1.setSourceAccount(a1);
            t1.setTargetAccount(a2);
            t1.setDescription("Initial transfer");

            transactionRepository.save(t1);

            System.out.println("Sample users, accounts, and transactions created.");
        };
    }
}

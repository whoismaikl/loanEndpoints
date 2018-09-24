package com.qred.task;

import com.qred.task.domain.entity.Company;
import com.qred.task.domain.entity.Loan;
import com.qred.task.domain.repository.CompanyRepository;
import com.qred.task.domain.repository.LoanRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

@SpringBootApplication
public class TaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);
    }

    @Bean
    CommandLineRunner companyRunner(CompanyRepository companyRepository) {
        return args -> {
            companyRepository.save(new Company("40003875131", "Zebra", "Private", "contact@zebra.com", "+37125770075", new BigDecimal("50000.00"), false, LocalDateTime.now()));
            companyRepository.save(new Company("40334875111", "admin@lursoft.com", "+37122779955", new BigDecimal("20000.00"), false, LocalDateTime.now()));
        };
    }

    @Bean
    CommandLineRunner loanRunner(LoanRepository loanRepository) {
        return args -> {
            loanRepository.save(new Loan(1L, new BigDecimal("200.00"),1, new BigDecimal(206), LocalDateTime.of(2018, Month.AUGUST, 29, 12, 12, 12),  false, false, false));
            loanRepository.save(new Loan(1L, new BigDecimal("300.00"),10, new BigDecimal(41.5), LocalDateTime.of(2018, Month.SEPTEMBER, 11, 19, 11, 11),  true, true, false));
            loanRepository.save(new Loan(1L, new BigDecimal("500.00"),10, new BigDecimal(51.5), LocalDateTime.of(2018, Month.SEPTEMBER, 12, 19, 19, 19),  false, false, false));
            loanRepository.save(new Loan(1L, new BigDecimal("1000.00"),10, new BigDecimal(103), LocalDateTime.of(2018, Month.SEPTEMBER, 13, 11, 11, 11),  false, false, true));
            loanRepository.save(new Loan(2L, new BigDecimal("1000.00"),10,  new BigDecimal(103), LocalDateTime.of(2018, Month.SEPTEMBER, 19, 11, 23, 21),  false, false, false));
            loanRepository.save(new Loan(2L, new BigDecimal("2000.00"),10,  new BigDecimal(206),  LocalDateTime.of(2018, Month.AUGUST, 21, 19, 23, 11),  true, true, false));
        };
    }


}

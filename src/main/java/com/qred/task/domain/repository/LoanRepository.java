package com.qred.task.domain.repository;

import com.qred.task.domain.entity.Company;
import com.qred.task.domain.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {

}

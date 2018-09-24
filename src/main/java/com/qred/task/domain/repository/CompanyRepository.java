package com.qred.task.domain.repository;

import com.qred.task.domain.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface CompanyRepository extends JpaRepository<Company, Long> {

    Company findByRegistrationNumber(String companyRegistrationNumber);
    List<Company> findAll();
}

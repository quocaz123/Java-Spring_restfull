package com.Quokka.Jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Quokka.Jobhunter.domain.dto.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

}

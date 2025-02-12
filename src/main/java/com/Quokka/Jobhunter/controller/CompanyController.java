package com.Quokka.Jobhunter.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Quokka.Jobhunter.domain.dto.Company;
import com.Quokka.Jobhunter.domain.dto.ResultPaginationDTO;
import com.Quokka.Jobhunter.service.CompanyService;
import com.Quokka.Jobhunter.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/companies")
    public ResponseEntity<?> createCompany(@Valid @RequestBody Company postCompany) {
        Company company = this.companyService.handleCreateCompany(postCompany);
        return ResponseEntity.status(HttpStatus.CREATED).body(company);
    }

    // fetch all companies
    @GetMapping("/companies")
    public ResponseEntity<ResultPaginationDTO> getListCompany(
            @Filter Specification<Company> spec, Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK).body(this.companyService.fetchAllCompany(spec, pageable));
    }

    @GetMapping("/companies/{id}")
    public ResponseEntity<Company> getCompanyId(@PathVariable("id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.companyService.getCompanyById(id));
    }

    @PutMapping("/companies")
    public ResponseEntity<Company> updateCompany(@Valid @RequestBody Company company) {
        return ResponseEntity.status(HttpStatus.OK).body((this.companyService.handleUpdateCompany(company)));
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable("id") long id) throws IdInvalidException {
        if (!companyService.existsById(id)) {
            throw new IdInvalidException("ID không tồn tại: " + id);
        }
        this.companyService.handleDeleteCompany(id);
        return ResponseEntity.ok("Success");
    }

}

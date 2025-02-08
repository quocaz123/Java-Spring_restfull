package com.Quokka.Jobhunter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Quokka.Jobhunter.domain.dto.Company;
import com.Quokka.Jobhunter.domain.dto.ResultPaginationDTO;
import com.Quokka.Jobhunter.repository.CompanyRepository;
import com.Quokka.Jobhunter.domain.dto.Meta;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company handleCreateCompany(Company company) {
        return this.companyRepository.save(company);
    }

    public ResultPaginationDTO fetchAllCompany(Pageable pageable) {
        Page<Company> pageCompany = this.companyRepository.findAll(pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        Meta mt = new Meta();

        mt.setPage(pageCompany.getNumber());
        mt.setPageSize(pageCompany.getSize());

        mt.setPages(pageCompany.getTotalPages());
        mt.setTotal(pageCompany.getTotalElements());

        rs.setMeta(mt);
        rs.setResult(pageCompany.getContent());

        return rs;
    }

    public Company getCompanyById(long id) {
        Optional<Company> comOptional = this.companyRepository.findById(id);
        if (comOptional.isPresent()) {
            return comOptional.get();
        }
        return null;
    }

    public Company handleUpdateCompany(Company company) {
        Company currCompany = this.getCompanyById(company.getId());
        if (currCompany != null) {
            currCompany.setLogo(company.getLogo());
            currCompany.setAddress(company.getAddress());
            currCompany.setName(company.getName());
            currCompany.setDescription(company.getDescription());

            currCompany = this.companyRepository.save(currCompany);
            return currCompany;
        }
        return null;
    }

    public void handleDeleteCompany(long id) {
        this.companyRepository.deleteById(id);
    }

    public boolean existsById(long id) {
        return this.companyRepository.existsById(id);
    }
}

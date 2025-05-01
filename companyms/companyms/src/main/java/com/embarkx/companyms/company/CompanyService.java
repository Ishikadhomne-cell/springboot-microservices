package com.embarkx.companyms.company;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();
    boolean updateCompany(Company company, long id);
    void createCompany(Company company);
    boolean deleteCompanyById(Long id);
    Company getCompanybyId(Long id);
}

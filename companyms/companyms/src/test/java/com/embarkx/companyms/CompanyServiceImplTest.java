package com.embarkx.companyms;

import com.embarkx.companyms.company.Company;
import com.embarkx.companyms.company.CompanyRepository;
import com.embarkx.companyms.company.impl.CompanyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompanyServiceImplTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyServiceImpl companyService;

    private AutoCloseable closeable;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
        System.out.println("🔧 Setup complete for CompanyServiceImplTest");
    }

    @Test
    void testGetAllCompanies() {
        System.out.println("🧪 Running: testGetAllCompanies");
        Company c1 = new Company();
        c1.setId(1L);
        c1.setName("Company A");

        Company c2 = new Company();
        c2.setId(2L);
        c2.setName("Company B");

        List<Company> mockList = Arrays.asList(c1, c2);
        when(companyRepository.findAll()).thenReturn(mockList);

        List<Company> result = companyService.getAllCompanies();

        assertEquals(2, result.size());
        assertEquals("Company A", result.get(0).getName());
        verify(companyRepository, times(1)).findAll();
        System.out.println("✅ Passed: testGetAllCompanies");

    }

    @Test
    void testGetCompanyById_Found() {
        System.out.println("🧪 Running: testGetCompanyById_Found");
        Company company = new Company();
        company.setId(1L);
        company.setName("Tech Co");

        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));

        Company result = companyService.getCompanybyId(1L);
        assertNotNull(result);
        assertEquals("Tech Co", result.getName());
        verify(companyRepository, times(1)).findById(1L);
        System.out.println("✅ Passed: testGetCompanyById_Found");
    }

    @Test
    void testGetCompanyById_NotFound() {
        System.out.println("🧪 Running: testGetCompanyById_NotFound");
        when(companyRepository.findById(999L)).thenReturn(Optional.empty());

        Company result = companyService.getCompanybyId(999L);
        assertNull(result);
        verify(companyRepository, times(1)).findById(999L);
        System.out.println("✅ Passed: testGetCompanyById_NotFound");
    }

    @Test
    void testCreateCompany() {
        Company company = new Company();
        company.setName("New Co");

        companyService.createCompany(company);

        verify(companyRepository, times(1)).save(company);
    }

    @Test
    void testUpdateCompany_Found() {
        System.out.println("🧪 Running: testUpdateCompany_Found");
        Company oldCompany = new Company();
        oldCompany.setId(1L);
        oldCompany.setName("Old Name");
        oldCompany.setDescription("Old Desc");

        Company updated = new Company();
        updated.setName("New Name");
        updated.setDescription("New Desc");

        when(companyRepository.findById(1L)).thenReturn(Optional.of(oldCompany));

        boolean result = companyService.updateCompany(updated, 1L);

        assertTrue(result);
        assertEquals("New Name", oldCompany.getName());
        assertEquals("New Desc", oldCompany.getDescription());

        verify(companyRepository, times(1)).findById(1L);
        verify(companyRepository, times(1)).save(oldCompany);
        System.out.println("✅ Passed: testUpdateCompany_Found");
    }

    @Test
    void testUpdateCompany_NotFound() {
        System.out.println("🧪 Running: testUpdateCompany_NotFound");
        Company updated = new Company();
        updated.setName("New Name");

        when(companyRepository.findById(5L)).thenReturn(Optional.empty());

        boolean result = companyService.updateCompany(updated, 5L);

        assertFalse(result);
        verify(companyRepository, times(1)).findById(5L);
        verify(companyRepository, times(0)).save(any());
        System.out.println("✅ Passed: testUpdateCompany_NotFound");
    }

    @Test
    void testDeleteCompanyById_Found() {
        System.out.println("🧪 Running: testDeleteCompanyById_Found");
        when(companyRepository.existsById(3L)).thenReturn(true);

        boolean result = companyService.deleteCompanyById(3L);

        assertTrue(result);
        verify(companyRepository, times(1)).existsById(3L);
        verify(companyRepository, times(1)).deleteById(3L);
        System.out.println("✅ Passed: testDeleteCompanyById_Found");
    }

    @Test
    void testDeleteCompanyById_NotFound() {
        System.out.println("🧪 Running: testDeleteCompanyById_NotFound");
        when(companyRepository.existsById(10L)).thenReturn(false);

        boolean result = companyService.deleteCompanyById(10L);

        assertFalse(result);
        verify(companyRepository, times(1)).existsById(10L);
        verify(companyRepository, times(0)).deleteById(any());
        System.out.println("✅ Passed: testDeleteCompanyById_NotFound");
    }
}

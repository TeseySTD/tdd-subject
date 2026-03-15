package edu.skoreiko.lab1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Rout
 * @version 1.0.0
 * @project lab1
 * @class CompanyServiceTest
 * @since 15.03.2026 - 23.16
 */

class CompanyServiceTest {
    private ICompanyService service;

    @BeforeEach
    void setUp() {
        service = new CompanyServiceImpl();
    }

    @Test
    void getTopLevelParent_ShouldReturnSelf_WhenNoParent() {
        Company root = new Company(null, 10);
        assertEquals(root, service.getTopLevelParent(root));
    }

    @Test
    void getTopLevelParent_ShouldReturnParent_WhenOneLevelDeep() {
        Company root = new Company(null, 10);
        Company child = new Company(root, 5);
        assertEquals(root, service.getTopLevelParent(child));
    }

    @Test
    void getTopLevelParent_ShouldReturnRoot_WhenMultipleLevelsDeep() {
        Company root = new Company(null, 10);
        Company mid = new Company(root, 5);
        Company leaf = new Company(mid, 2);
        assertEquals(root, service.getTopLevelParent(leaf));
    }

    @Test
    void getTopLevelParent_ShouldReturnNull_WhenInputIsNull() {
        assertNull(service.getTopLevelParent(null));
    }


    @Test
    void getEmployeeCount_ShouldReturnOwnCount_WhenNoChildrenExist() {
        Company c1 = new Company(null, 10);
        List<Company> all = Collections.singletonList(c1);
        assertEquals(10, service.getEmployeeCountForCompanyAndChildren(c1, all));
    }

    @Test
    void getEmployeeCount_ShouldSumDirectChildren() {
        Company root = new Company(null, 10);
        Company child1 = new Company(root, 5);
        Company child2 = new Company(root, 3);
        List<Company> all = Arrays.asList(root, child1, child2);

        assertEquals(18, service.getEmployeeCountForCompanyAndChildren(root, all));
    }

    @Test
    void getEmployeeCount_ShouldSumNestedChildrenDeeply() {
        Company root = new Company(null, 10);
        Company child = new Company(root, 5);
        Company grandChild = new Company(child, 2);
        List<Company> all = Arrays.asList(root, child, grandChild);

        assertEquals(17, service.getEmployeeCountForCompanyAndChildren(root, all));
    }

    @Test
    void getEmployeeCount_ShouldReturnZero_WhenCompanyIsNull() {
        assertEquals(0, service.getEmployeeCountForCompanyAndChildren(null, Collections.emptyList()));
    }

    @Test
    void getEmployeeCount_ShouldIgnoreUnrelatedCompanies() {
        Company root1 = new Company(null, 10);
        Company root2 = new Company(null, 20);
        List<Company> all = Arrays.asList(root1, root2);

        assertEquals(10, service.getEmployeeCountForCompanyAndChildren(root1, all));
    }

    @Test
    void getEmployeeCount_ShouldReturnOwnCount_WhenListIsEmpty() {
        Company root = new Company(null, 10);
        assertEquals(10, service.getEmployeeCountForCompanyAndChildren(root, Collections.emptyList()));
    }
}
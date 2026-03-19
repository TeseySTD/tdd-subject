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

    @Test
    void getEmployeeCount_ShouldHandleBroadTree() {
        Company root = new Company(null, 10);
        List<Company> all = new java.util.ArrayList<>();
        all.add(root);
        for (int i = 0; i < 100; i++) {
            all.add(new Company(root, 1));
        }

        assertEquals(110, service.getEmployeeCountForCompanyAndChildren(root, all));
    }

    @Test
    void getEmployeeCount_ShouldHandleNullListGracefully() {
        Company root = new Company(null, 10);
        assertEquals(10, service.getEmployeeCountForCompanyAndChildren(root, null));
    }

    @Test
    void getEmployeeCount_ShouldWorkCorrectly_WhenTargetCompanyIsNotInList() {
        Company root = new Company(null, 10);
        Company child = new Company(root, 5);
        List<Company> onlyChildren = Collections.singletonList(child);

        assertEquals(15, service.getEmployeeCountForCompanyAndChildren(root, onlyChildren));
    }

    @Test
    void getEmployeeCount_ShouldHandleForestScenario() {
        Company root1 = new Company(null, 10);
        Company child1 = new Company(root1, 5);

        Company root2 = new Company(null, 100);
        Company child2 = new Company(root2, 50);

        List<Company> forest = Arrays.asList(root1, child1, root2, child2);

        assertEquals(15, service.getEmployeeCountForCompanyAndChildren(root1, forest));
        assertEquals(150, service.getEmployeeCountForCompanyAndChildren(root2, forest));
    }

    @Test
    void getEmployeeCount_ShouldHandleZeroAndNegativeValues() {
        Company root = new Company(null, 10);
        Company child1 = new Company(root, 0);
        Company child2 = new Company(root, -2);
        List<Company> all = Arrays.asList(root, child1, child2);

        assertEquals(8, service.getEmployeeCountForCompanyAndChildren(root, all));
    }

    @Test
    void getEmployeeCount_ShouldHandleDeepHierarchy_Performance() {
        Company current = new Company(null, 1);
        List<Company> all = new java.util.ArrayList<>();
        all.add(current);

        Company top = current;
        for (int i = 0; i < 999; i++) {
            Company next = new Company(current, 1);
            all.add(next);
            current = next;
        }

        assertEquals(1000, service.getEmployeeCountForCompanyAndChildren(top, all));
    }

    @Test
    void getEmployeeCount_ShouldWorkRegardlessOfListOrder() {
        Company root = new Company(null, 10);
        Company child = new Company(root, 5);
        Company grandChild = new Company(child, 2);

        List<Company> shuffledList = Arrays.asList(grandChild, child, root);

        assertEquals(17, service.getEmployeeCountForCompanyAndChildren(root, shuffledList));
    }

    @Test
    void getTopLevelParent_ShouldHandleMultipleCallsOnSameObject() {
        Company root = new Company(null, 10);
        Company child = new Company(root, 5);

        service.getTopLevelParent(child);
        Company secondResult = service.getTopLevelParent(child);

        assertEquals(root, secondResult);
    }

    @Test
    void getTopLevelParent_ShouldReturnCorrectRoot_InComplexTree() {
        Company root = new Company(null, 100);
        Company branch1 = new Company(root, 10);
        Company branch2 = new Company(root, 20);
        Company leaf = new Company(branch2, 5);

        assertEquals(root, service.getTopLevelParent(leaf));
        assertEquals(root, service.getTopLevelParent(branch1));
    }

    @Test
    void getTopLevelParent_ShouldWorkWithOrphanNodesInList() {
        Company root = new Company(null, 10);
        Company orphanChild = new Company(root, 5);

        assertEquals(root, service.getTopLevelParent(orphanChild));
    }
}
package edu.skoreiko.lab1;
import java.util.List;

/**
 * @author Rout
 * @version 1.0.0
 * @project lab1
 * @class CompanyServiceImpl
 * @since 15.03.2026 - 23.05
 */

public class CompanyServiceImpl implements ICompanyService {

    @Override
    public Company getTopLevelParent(Company child) {
        if (child == null) return null;

        Company current = child;
        while (current.getParent() != null) {
            current = current.getParent();
        }
        return current;
    }

    @Override
    public long getEmployeeCountForCompanyAndChildren(Company company, List<Company> companies) {
        if (company == null) return 0;

        long total = company.getEmployeeCount();

        if (companies == null || companies.isEmpty()) {
            return total;
        }

        for (Company potentialChild : companies) {
            if (potentialChild.getParent() == company) {
                total += getEmployeeCountForCompanyAndChildren(potentialChild, companies);
            }
        }

        return total;
    }
}
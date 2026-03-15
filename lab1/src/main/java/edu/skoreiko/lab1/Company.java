package edu.skoreiko.lab1;

/**
 * @author Rout
 * @version 1.0.0
 * @project lab1
 * @class Company
 * @since 15.03.2026 - 23.01
 */

public class Company {
    private Company parent;
    private long employeeCount;

    public Company(Company parent, long employeeCount) {
        this.parent = parent;
        this.employeeCount = employeeCount;
    }

    public Company getParent() { return parent; }
    public long getEmployeeCount() { return employeeCount; }
}
package com.mindex.challenge.util;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;

import java.util.List;
import java.util.Set;

public class EmployeeUtil {
    private EmployeeUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static Employee createEmployeeFrom(String employeeId, Employee employee) {
        return new Employee(employeeId, employee.firstName(), employee.lastName(),
                employee.position(), employee.department(), employee.directReports(), employee.compensation());
    }

    public static Employee createEmployeeFrom(String employeeId, String firstName, String lastName,
                                              String position, String department, Set<Employee> directReports, Compensation compensation) {
        return new Employee(employeeId, firstName, lastName, position, department, directReports, compensation);
    }
}

package com.mindex.challenge.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Set;

public record Employee(
        @Id
        String employeeId,
        String firstName,
        String lastName,
        String position,
        String department,
        @DocumentReference
        Set<Employee> directReports,
        Compensation compensation) {

    public Employee {
        if (null != directReports) {
            directReports = Set.copyOf(directReports);
        }
    }

}

package com.mindex.challenge.service;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class ReportStructureService {
    private static final Logger LOG = LoggerFactory.getLogger(ReportStructureService.class);

    private final EmployeeRepository employeeRepository;

    @Autowired
    public ReportStructureService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public ReportingStructure retrieveReportingStructure(String employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new IllegalArgumentException("Employee id cannot be blank");
        }

        var employee = employeeRepository.findByEmployeeId(employeeId);
        Long totalNumberOfReports = getTotalNumberOfReports(employee);

        var reportStructure = new ReportingStructure(employee, totalNumberOfReports);
        LOG.debug("Retrieving report structure: {}", reportStructure);

        return reportStructure;
    }

    private Long getTotalNumberOfReports(Employee employee) {
        if (!CollectionUtils.isEmpty(employee.directReports())) {
            for (var employeeReport : employee.directReports()) {
                if (!CollectionUtils.isEmpty(employeeReport.directReports())) {
                    return getTotalNumberOfReports(employeeReport) + employeeReport.directReports().size();
                }
            }
        }
        return !CollectionUtils.isEmpty(employee.directReports()) ? employee.directReports().size() : 0L;
    }
}

package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.util.EmployeeUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        var createdEmployee = EmployeeUtil.createEmployeeFrom(UUID.randomUUID().toString(), employee);
        employeeRepository.insert(createdEmployee);

        LOG.debug("Employee with employeeId [{}] was created", createdEmployee.employeeId());

        return createdEmployee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Retrieving employee with id [{}]", id);

        if (StringUtils.isBlank(id)) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        var employee = employeeRepository.findByEmployeeId(id);

        if (null == employee) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        LOG.debug("Employee with employeeId [{}] was read", employee.employeeId());

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }
}

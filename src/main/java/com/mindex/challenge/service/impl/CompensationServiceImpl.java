package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.util.CompensationUtil;
import com.mindex.challenge.util.EmployeeUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CompensationServiceImpl implements CompensationService {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    private final EmployeeRepository employeeRepository;
    private final CompensationRepository compensationRepository;

    @Autowired
    public CompensationServiceImpl(EmployeeRepository employeeRepository,
                                   CompensationRepository compensationRepository) {
        this.employeeRepository = employeeRepository;
        this.compensationRepository = compensationRepository;
    }


    @Override
    public Compensation createCompensation(String employeeId, Compensation compensation) {
        if (StringUtils.isBlank(employeeId) || compensation == null) {
            LOG.error("Invalid employee id or compensation");
            throw new IllegalArgumentException("Invalid employee id or compensation is null/missing");
        }

        var employee = employeeRepository.findByEmployeeId(employeeId);

        var createdCompensation = CompensationUtil.createCompensation(UUID.randomUUID().toString(), compensation);
        compensationRepository.insert(createdCompensation);

        var updateEmployee = EmployeeUtil.createEmployeeFrom(employeeId, employee.firstName(), employee.lastName(), employee.position(),
                employee.department(), employee.directReports(), createdCompensation);
        employeeRepository.save(updateEmployee);

        return createdCompensation;
    }

    @Override
    public Compensation readCompensation(String employeeId) {
        if (StringUtils.isBlank(employeeId)) {
            LOG.error("Invalid employee id or compensation ");
            throw new IllegalArgumentException("Invalid employee id or compensation is null/missing");
        }

        var employee = employeeRepository.findByEmployeeId(employeeId);
        var compensation = employee.compensation();
        LOG.info("Read compensation for {}", compensation);

        return compensation;
    }
}

package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    private final CompensationService compensationService;

    @Autowired
    public CompensationController(CompensationService compensationService) {
        this.compensationService = compensationService;
    }

    @PostMapping("/compensation/{employeeId}")
    public Compensation createCompensation(@PathVariable String employeeId, @RequestBody Compensation compensation) {
        LOG.debug("Creating Compensation create for employee Id [{}]", employeeId);

        return compensationService.createCompensation(employeeId, compensation);
    }

    @GetMapping("/compensation/{employeeId}")
    public Compensation retrieveCompensation(@PathVariable String employeeId) {
        LOG.debug("Retrieve employee Compensation [{}]", employeeId);

        return compensationService.readCompensation(employeeId);
    }

}

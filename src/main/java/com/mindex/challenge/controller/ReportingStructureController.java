package com.mindex.challenge.controller;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportingStructureController {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class);

    private final ReportStructureService reportStructureService;

    @Autowired
    public ReportingStructureController(ReportStructureService reportStructureService) {
        this.reportStructureService = reportStructureService;
    }

    @GetMapping("/report/{employeeId}")
    public ReportingStructure retrieveReportingStructure(@PathVariable String employeeId) {
        LOG.debug("Retrieving report structure for employee Id [{}]", employeeId);

        return reportStructureService.retrieveReportingStructure(employeeId);
    }
}

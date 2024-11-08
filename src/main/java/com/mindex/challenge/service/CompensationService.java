package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;

public interface CompensationService {
    Compensation createCompensation(String employeeId, Compensation compensation);
    Compensation readCompensation(String employeeId);
}

package com.mindex.challenge.util;

import com.mindex.challenge.data.Compensation;

public class CompensationUtil {
    private CompensationUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static Compensation createCompensation(String compensationId, Compensation compensation) {
        return new Compensation(compensationId, compensation.salary(), compensation.effectiveDate());
    }
}

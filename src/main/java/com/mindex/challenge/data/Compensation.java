package com.mindex.challenge.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Compensation(
        @Id
        String compensationId,
        BigDecimal salary,
        @JsonFormat(pattern = "MM/dd/yyyy")
        LocalDate effectiveDate) {
}

package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {
    private String compensationCreateUrl;
    private String compensationReadUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Before
    public void setup() {
        compensationCreateUrl = "http://localhost:" + port + "/compensation/{employeeId}";
        compensationReadUrl = "http://localhost:" + port + "/compensation/{employeeId}";
    }

    @Test
    public void testCreateCompensation() {
        var employeeId = "16a596ae-edd3-4847-99fe-c4518e82c86f";
        var employee = employeeRepository.findByEmployeeId(employeeId);

        assertNull(employee.compensation());

        LocalDate localDate = LocalDate.of(2024, Month.NOVEMBER, 11);
        var compensationToCreate = new Compensation(null, BigDecimal.valueOf(120000), localDate);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var compensation = testRestTemplate.exchange(compensationCreateUrl,
                HttpMethod.POST,
                new HttpEntity<>(compensationToCreate, headers),
                Compensation.class,
                employeeId).getBody();

        assertNotNull(compensation);
        assertNotNull(compensation.compensationId());
        assertEquals(LocalDate.of(2024, Month.NOVEMBER, 11), compensation.effectiveDate());

        var compensationRead = testRestTemplate.getForEntity(compensationReadUrl, Compensation.class, employeeId).getBody();

        assertNotNull(compensationRead);
        assertEquals(compensation.effectiveDate(), compensationRead.effectiveDate());
        assertEquals(compensation.compensationId(), compensationRead.compensationId());

        employee = employeeRepository.findByEmployeeId(employeeId);
        assertNotNull(employee.compensation());
    }
}
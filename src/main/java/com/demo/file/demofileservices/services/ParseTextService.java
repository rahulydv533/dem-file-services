package com.demo.file.demofileservices.services;

import com.demo.file.demofileservices.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.demo.file.demofileservices.util.Constant.*;
import static org.apache.logging.log4j.util.Strings.trimToNull;

@Service
public class ParseTextService {

    public List<Employee> getEmployeeRecord(List<String> flatMessages) {
        List<Employee> employeeRecords = new ArrayList<>();
        flatMessages.forEach(msg -> employeeRecords.add(buildEmployeeRecord(msg)));
        return employeeRecords;
    }

    private Employee buildEmployeeRecord(String msg) {
        Employee employee = new Employee();
        int startPosition = PAYLOAD_START_INDEX;
        employee.setName(trimToNull(msg.substring(startPosition, EMPLOYEE_NAME_LENGTH)));
        startPosition += EMPLOYEE_NAME_LENGTH;
        employee.setAddress(trimToNull(msg.substring(startPosition, startPosition + EMPLOYEE_ADDRESS_LENGTH)));
        startPosition += EMPLOYEE_ADDRESS_LENGTH;
        employee.setCompany(trimToNull(msg.substring(startPosition, startPosition + EMPLOYEE_COMPANY_LENGTH)));
        startPosition += EMPLOYEE_COMPANY_LENGTH;
        employee.setContactNumber(trimToNull(msg.substring(startPosition, startPosition + EMPLOYEE_CONTACT_NUMBER_LENGTH)));
        return employee;


    }
}

package com.demo.file.demofileservices.util;

import com.demo.file.demofileservices.model.Employee;

import java.util.*;
import java.util.function.Function;

public enum FileAdapterEnum {
    ;
    public static final Function<Map<Integer, List<String>>, List<Employee>> EMPLOYEE_DETAILS = data -> {
        Map<String, Employee> employeeMap = new HashMap<>();
        data.forEach((key, values) -> {
            if (values.size() == 4 & !ValidatorEnum.VALIDATE.test(values)) {
                Employee emp = new Employee();
                emp.setName(values.get(0));
                emp.setAddress(values.get(1));
                emp.setCompany(values.get(2));
                emp.setContactNumber(values.get(3));

                employeeMap.put(values.get(0), emp);
            }
        });
        return employeeMap.values().stream().toList();
    };
}

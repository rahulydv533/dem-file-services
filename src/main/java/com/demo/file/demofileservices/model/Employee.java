package com.demo.file.demofileservices.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    String name;
    String address;
    String company;
    String contactNumber;

}

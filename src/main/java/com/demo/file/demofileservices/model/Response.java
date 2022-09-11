package com.demo.file.demofileservices.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response {

    private Integer userId;
    private String name;
    private String email;
    private String status;
    private String errorMessage;
}

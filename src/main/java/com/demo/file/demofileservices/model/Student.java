package com.demo.file.demofileservices.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
//    @JsonProperty(value="userId",required = true)
    @JsonProperty(value = "userId")
    private Integer id;

    private String name;
    private String email;
    private String password;

}

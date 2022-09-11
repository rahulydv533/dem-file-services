package com.demo.file.demofileservices.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
//    @JsonProperty(value="userId",required = true)
    //@JsonProperty(value="userId")
    private Integer id;

    private String name;
    private String description;
    private String price;
    private String file_path;
    private String fileName;
    private String updatedAt;
    private String createdAt;


}

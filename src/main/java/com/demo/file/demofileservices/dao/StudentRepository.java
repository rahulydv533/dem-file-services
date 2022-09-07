package com.demo.file.demofileservices.dao;

import com.demo.file.demofileservices.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

//    https://www.baeldung.com/spring-boot-h2-database
}

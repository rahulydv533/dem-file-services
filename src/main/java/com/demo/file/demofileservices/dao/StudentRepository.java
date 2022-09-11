package com.demo.file.demofileservices.dao;

import com.demo.file.demofileservices.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByIdAndPassword(Integer id, String password);


//    https://www.baeldung.com/spring-boot-h2-database
}

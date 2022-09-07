package com.demo.file.demofileservices.controller;

import com.demo.file.demofileservices.dao.StudentRepository;
import com.demo.file.demofileservices.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    @GetMapping(value = "/getStudents", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Student>> getStudents() {

        List<Student> studentList = new ArrayList<>();
        studentList.addAll(studentRepository.findAll());
        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }

    @PostMapping(value = "/insertStudent", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createStudent(@RequestBody Student student) {
        studentRepository.save(student);

        return new ResponseEntity<String>("Successfully Inserted In Database", HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteStudent/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Integer id) {

        studentRepository.deleteById(id);
        return new ResponseEntity<String>(String.format("Employee with contact number {} is successfully deleted", id), HttpStatus.OK);
    }
}

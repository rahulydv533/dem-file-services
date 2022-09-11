package com.demo.file.demofileservices.controller;

import com.demo.file.demofileservices.dao.StudentRepository;
import com.demo.file.demofileservices.model.Response;
import com.demo.file.demofileservices.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @GetMapping(value = "/getStudentById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> getStudentById(@PathVariable Integer id) {

        return new ResponseEntity<>(studentRepository.findById(id).get(), HttpStatus.OK);
    }

    @PostMapping(value = "/insertStudent", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> createStudent(@RequestBody Student student) {
        Student student1 = studentRepository.save(student);
        Optional<Student> std = Optional.of(student1);
        Response responseObject = prepareResponse(std);
        return new ResponseEntity<Response>(responseObject, HttpStatus.OK);

//        return new ResponseEntity<String>("Successfully Inserted In Database", HttpStatus.OK);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> verifyStudent(@RequestBody Student student) {
        Optional<Student> student1 = studentRepository.findByIdAndPassword(student.getId(), student.getPassword());
        Response responseObject = prepareResponse(student1);
        return new ResponseEntity<Response>(responseObject, HttpStatus.OK);

//        return new ResponseEntity<String>("Successfully Inserted In Database", HttpStatus.OK);
    }

    private Response prepareResponse(Optional<Student> student1) {
        if (student1.isPresent()) {
            Student student = student1.get();
            return Response.builder().userId(student.getId()).name(student.getName())
                    .email(student.getEmail()).status("0").build();
        } else {
            return Response.builder().status("-1").errorMessage("Data Not Found").build();
        }
    }

    @DeleteMapping(value = "/deleteStudent/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Integer id) {

        studentRepository.deleteById(id);
        return new ResponseEntity<String>(String.format("Employee with contact number %d is successfully deleted", id), HttpStatus.OK);
    }
}

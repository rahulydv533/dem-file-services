package com.demo.file.demofileservices.services;

import com.demo.file.demofileservices.model.Employee;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    public List<Employee> uploadFile(MultipartFile file) throws IOException;
}

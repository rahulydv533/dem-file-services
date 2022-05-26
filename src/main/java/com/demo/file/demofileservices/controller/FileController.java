package com.demo.file.demofileservices.controller;

import com.demo.file.demofileservices.model.Employee;
import com.demo.file.demofileservices.services.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("demo")
public class FileController {

    private FileService fileService;
    public FileController(FileService fileService){
         this.fileService=fileService;
     }

     @GetMapping("")
     public String index(){
        return "upload";
     }
    @PostMapping(value="/uploadFile", consumes = { "multipart/form-data" },produces={MediaType.TEXT_PLAIN_VALUE})
    public String uploadFile(@RequestParam MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {

        //return ResponseEntity.status(HttpStatus.OK).body(fileService.uploadFile(file));
        List<Employee> empList = fileService.uploadFile(file);
        redirectAttributes.addFlashAttribute("employeeList",empList );
        return "redirect:/demo/uploadResponse";
        //return ResponseEntity.ok(empList);
    }

    @GetMapping("uploadResponse")
    public String uploadResponse(){
        return "uploadResponse";
    }

}

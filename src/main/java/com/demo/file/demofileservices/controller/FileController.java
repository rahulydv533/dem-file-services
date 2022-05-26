package com.demo.file.demofileservices.controller;

import com.demo.file.demofileservices.model.Employee;
import com.demo.file.demofileservices.services.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("demo")
public class FileController {

    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    private List<String> contentTypes = Arrays.asList("application/vnd.ms-exce", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

    @GetMapping("")
    public String index() {
        return "uploadTextFile";
    }

    @PostMapping(value = "/uploadFile", consumes = {"multipart/form-data"}, produces = {MediaType.TEXT_PLAIN_VALUE})
    public String uploadFile(@RequestParam MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Uploaded file is empty.");
            return "redirect:/demo/errorResponse";
        } else if (contentTypes.contains(file.getContentType())) {
            List<Employee> empList = fileService.uploadExcelFile(file);
            redirectAttributes.addFlashAttribute("employeeList", empList);
            return "redirect:/demo/uploadExcelFileResponse";
        } else if (file.isEmpty()) {
            return "redirect:/demo/errorResponse";
        } else if (file.getContentType().equalsIgnoreCase("text/plain")) {
            List<Employee> empList = fileService.uploadTextFile(file);
            redirectAttributes.addFlashAttribute("employeeList", empList);
            return "redirect:/demo/uploadTextFileResponse";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid file: Uploaded file should be either in txt or xlx format.");
            return "redirect:/demo/errorResponse";
        }

    }

    @GetMapping("uploadTextFileResponse")
    public String uploadTextFileResponse() {
         /*
         Thymeleaf, which is a Java template engine for creating dynamic web pages.
         https://www.baeldung.com/spring-web-flash-attributes
          */
        return "UploadTextFileResponse";
    }

    @GetMapping(value = "uploadExcelFileResponse", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Employee>> uploadExcelFileResponse(HttpServletRequest request) {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        List<Employee> empList = (List<Employee>) inputFlashMap.get("employeeList");
        return ResponseEntity.status(HttpStatus.OK).body(empList);
    }

    @GetMapping(value = "errorResponse", produces = MediaType.TEXT_PLAIN_VALUE)
    public String errorResponse() {
        return "ErrorResponse";
    }


}

package com.demo.file.demofileservices.services;

import com.demo.file.demofileservices.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.micrometer.core.instrument.util.StringUtils.isEmpty;

@Service
public class FileServiceImp implements FileService {

    Logger log = LoggerFactory.getLogger(FileServiceImp.class);

    private ParseTextService parseTextService;

    public FileServiceImp(ParseTextService parseTextService) {
        this.parseTextService = parseTextService;
    }

    public List<Employee> uploadFile(MultipartFile multipartFile) throws IOException {
        try {

            List<String> listOfDataByBufferReader = new ArrayList<>();
            String tmpLocation = System.getProperty("java.io.tmpdir");
            log.info("tempLocation: " + tmpLocation);
            multipartFile.transferTo(new File(tmpLocation + multipartFile.getOriginalFilename()));

            /*
            1st option to read data line by line
            List<String> listOfData = Files.lines(Paths.get(tmpLocation + multipartFile.getOriginalFilename())).collect(Collectors.toList());
             */

           /*
            2nd option to read data by line by buffer reader
            BufferedReader bufferReader = Files.newBufferedReader(Paths.get(tmpLocation + multipartFile.getOriginalFilename()));
            */

            InputStream inputStream = Files.newInputStream(Paths.get(tmpLocation + multipartFile.getOriginalFilename()));
            BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while (!isEmpty(line = buffer.readLine())) {
                listOfDataByBufferReader.add(line);
            }
            return parseTextService.getEmployeeRecord(listOfDataByBufferReader);
        } catch (IOException ex) {
            log.info("Exception occurred while extracting data from {} due to \n {}", multipartFile.getOriginalFilename(), ex.getStackTrace());
            throw ex;
        }
    }


}

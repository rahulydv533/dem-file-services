package com.demo.file.demofileservices.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ZipFileService {

    public static Logger logger = LoggerFactory.getLogger(ZipFileService.class);

    public byte[] getZipFile() throws IOException {
        Path path1 = Files.createTempFile("firstTestFile", ".txt");
        File firstFile = Files.writeString(path1, "Data written in first file").toFile();

        Path path2 = Files.createTempFile("secondTestFile", ".txt");
        File secondFile = Files.writeString(path2, "Data written in first file").toFile();

        Path path3 = Files.createTempFile("thirdTestFile", ".txt");
        File thirdFile = Files.writeString(path3, "Data written in first file").toFile();

        /*File file1 = new File("firstFile.txt");
        if(file1.createNewFile()){
            FileWriter fileWriter1= new FileWriter(file1);
            fileWriter1.write("Data written in first file");
        }
        *//*
        2nd File
         *//*
        File file2 = new File("secondFile.txt");
        if(file2.createNewFile()){
            FileWriter fileWriter2= new FileWriter(file2);
            fileWriter2.write("Data written in second file");
        }*/
        List<File> fileList = new ArrayList<>();
        fileList.add(firstFile);
        fileList.add(secondFile);
        fileList.add(thirdFile);
        return createZipFile(fileList);

    }

    /**
     * Method to create zip file from multiple files.
     * @param reportFiles
     * @return
     * @throws IOException
     */
    public static byte[] createZipFile(List<File> reportFiles) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zipOut = new ZipOutputStream(baos);
        try {
            for (File file : reportFiles) {
                if(file != null && file.exists()) {
                    try(FileInputStream fis = new FileInputStream(file);){
                        ZipEntry zipEntry = new ZipEntry(file.getName());

                        zipOut.putNextEntry(zipEntry);

                        byte[] bytes = new byte[1024];
                        int length;
                        while((length = fis.read(bytes)) >= 0) {
                            zipOut.write(bytes, 0, length);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            logger.error("A file does not exist: " + e);
            throw e;
        } catch (IOException e) {
            logger.error("I/O error: " + e);
            throw e;
        } finally {
            zipOut.close();
        }
        return baos.toByteArray();
    }





}

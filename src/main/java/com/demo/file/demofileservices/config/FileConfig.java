package com.demo.file.demofileservices.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.FileSystemUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class FileConfig {

    private static Path root;
    private static FileConfig fileConfig;

    public FileConfig() {
        //Do Nothing.
    }

    public static FileConfig getInstance() {
        if (fileConfig == null) {
            fileConfig = new FileConfig();
        }
        return fileConfig;
    }

    public static Path getRootPath() {
        Path rootPath = root;
        return rootPath;
    }

    public static void setRootPath(Path path) throws IOException {
        FileConfig.root = path;
        Files.createDirectories(root);
    }

    @PostConstruct
    void createRootFolder() throws IOException {
        Path folder = Paths.get("uploads/images");
        if (root == null) {

            FileSystemUtils.deleteRecursively(folder.toFile());
            root = Paths.get("");
        }
        setRootPath(folder);


//        if(root!=null){
//            FileSystemUtils.deleteRecursively(root.toFile());
//        }

    }
}

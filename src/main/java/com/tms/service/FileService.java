package com.tms.service;

import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FileService {

    private  final Path ROOT_PATH_FILE = Paths.get("data");

    //Uploading file
    public Boolean uploadFile(MultipartFile file) {
        try {
            if (file.getOriginalFilename() == null) {
                return false;
            }
            Files.copy(file.getInputStream(),  ROOT_PATH_FILE.resolve(file.getOriginalFilename()));
        }catch (IOException e) {
            System.out.println("File not found -> " + e.getMessage());
            return false;
        }
        return true;
    }

    //Download
    public Optional<Resource> getFile(String filename) {
        Resource resource = new PathResource(ROOT_PATH_FILE.resolve(filename));
        if (resource.exists()) {
            return Optional.of(resource);
        }
        return Optional.empty();
    }

    public ArrayList<String> getListOfFiles() throws IOException {
        return  (ArrayList<String>) Files.walk(ROOT_PATH_FILE, 1)
                    .filter(path -> !path.equals(ROOT_PATH_FILE))
                    .map(Path::toString)
                    .collect(Collectors.toList());
    }

    public Boolean deleteFileService(String filename) {
        Path path = ROOT_PATH_FILE.resolve(filename);
        File file = new File(path.toString());
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }
}

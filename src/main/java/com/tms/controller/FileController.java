package com.tms.controller;

import com.tms.service.FileService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @ApiResponses(value = {
            @ApiResponse(description = "File created successfully.", responseCode = "201"),
            @ApiResponse(description = "Conflict during file create", responseCode = "409")
    })
    //Upload file
    @PostMapping
    public ResponseEntity<HttpStatus> uploadFile(@RequestParam("file") MultipartFile file) {
        Boolean result = fileService.uploadFile(file);
        return new ResponseEntity<>(result ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    @ApiResponses(value = {
            @ApiResponse(description = "File is downloaded File is downloaded successfully.", responseCode = "200"),
            @ApiResponse(description = "File not found.", responseCode = "404")
    })
    //Download file
    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Optional<Resource> resource = fileService.getFile(filename);
        if (resource.isPresent()) {
           HttpHeaders headers = new HttpHeaders();
           headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.get().getFilename());
           return new ResponseEntity<>(resource.get(), headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiResponses(value = {
            @ApiResponse(description = "The list with the file is loaded File is downloaded successfully.", responseCode = "200"),
            @ApiResponse(description = "The list with file not found.", responseCode = "404")
    })
    //Download list files
    @GetMapping
    public ResponseEntity<ArrayList<String>> getListOfFiles() {
        ArrayList<String> files;
        try {
            files = fileService.getListOfFiles();
        } catch (IOException e) {
            System.out.println("Files not found -> " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(files.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(description = "File is deleted successfully .", responseCode = "200"),
            @ApiResponse(description = "Conflict during file delete.", responseCode = "409")
    })
    //Delete file
    @DeleteMapping("/{filename}")
    public ResponseEntity<HttpStatus> deleteFile(@PathVariable("filename") String filename) {
        Boolean result = fileService.deleteFileService(filename);
        return new ResponseEntity<>(result ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

}
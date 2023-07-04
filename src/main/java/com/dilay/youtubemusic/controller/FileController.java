package com.dilay.youtubemusic.controller;

import com.dilay.youtubemusic.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    private StorageService storageService;


    @PostMapping
    public ResponseEntity<?> uploadFile(@RequestParam("video") MultipartFile file) throws IOException, IOException {
        String uploadFile = storageService.uploadFile(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadFile);
    }

//    @GetMapping("/{fileName}")
//    public ResponseEntity<?> downloadFile(@PathVariable String fileName){
//        byte[] fileData=storageService.downloadFile(fileName);
//        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.valueOf("video/mp4"))
//                .body(fileData);
//
//    }

}

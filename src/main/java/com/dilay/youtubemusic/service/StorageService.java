package com.dilay.youtubemusic.service;

import com.dilay.youtubemusic.entity.FileData;
import com.dilay.youtubemusic.exception.BusinessException;
import com.dilay.youtubemusic.exception.ErrorCode;
import com.dilay.youtubemusic.repository.FileRepository;
import com.dilay.youtubemusic.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class StorageService {
    @Autowired
    private FileRepository fileRepository;

    public String uploadFile(MultipartFile file) throws IOException {
     FileData fileData = fileRepository.save(FileData.builder().
            name(file.getOriginalFilename()).
            type(file.getContentType()).
            fileData(FileUtil.compressFile(file.getBytes())).
            build());

     if(fileData!= null){
        return  "file uploaded successfully " + file.getOriginalFilename();
     }
    throw new BusinessException(ErrorCode.resource_missing, "There is no file like that!");
    }


//    public byte[] downloadFile(String filename){
//       Optional<FileData> dbFileData =fileRepository.findByName(filename);
//       byte[] file = FileUtil.decompressFile(dbFileData.get().getFileData());
//       return file;
//    }



}

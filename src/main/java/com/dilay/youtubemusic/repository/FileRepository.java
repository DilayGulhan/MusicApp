package com.dilay.youtubemusic.repository;

import com.dilay.youtubemusic.entity.FileData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<FileData , String> {
Optional<FileData> findByName(String fileName);
}


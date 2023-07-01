package com.dilay.youtubemusic.repository;

import com.dilay.youtubemusic.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface VideoRepository extends JpaRepository<Video, String> {

    Optional<Video> findByTitle(String title);


}

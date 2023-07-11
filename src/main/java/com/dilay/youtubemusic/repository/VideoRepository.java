package com.dilay.youtubemusic.repository;

import com.dilay.youtubemusic.entity.Category;
import com.dilay.youtubemusic.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface VideoRepository extends JpaRepository<Video, String> {

    public Optional<Video> findByTitle(String title);
    public List<Category> findCategoriesByCategoriesOfTheVideo(Category category);

}

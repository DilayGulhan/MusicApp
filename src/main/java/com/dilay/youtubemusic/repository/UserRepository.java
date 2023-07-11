package com.dilay.youtubemusic.repository;

import com.dilay.youtubemusic.entity.User;
import com.dilay.youtubemusic.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findFirstByEmail(String email);

    boolean existsByEmail(String email);

    User findUserByEmail(String email);

    List<User> findByFavoriteVideos(Video video );


}

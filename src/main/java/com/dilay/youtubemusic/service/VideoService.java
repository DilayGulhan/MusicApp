package com.dilay.youtubemusic.service;

import com.dilay.youtubemusic.entity.Category;
import com.dilay.youtubemusic.entity.User;
import com.dilay.youtubemusic.entity.UserRole;
import com.dilay.youtubemusic.entity.Video;
import com.dilay.youtubemusic.exception.BusinessException;
import com.dilay.youtubemusic.exception.ErrorCode;
import com.dilay.youtubemusic.model.request.video.CreateVideoRequest;
import com.dilay.youtubemusic.model.request.video.UpdateVideoRequest;
import com.dilay.youtubemusic.model.response.VideoResponse;
import com.dilay.youtubemusic.repository.CategoryRepository;
import com.dilay.youtubemusic.repository.UserRepository;
import com.dilay.youtubemusic.repository.VideoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;


@Service
@Transactional
@AllArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;


    public Page<VideoResponse> listVideos(Pageable pageable ){

        return videoRepository.findAll(pageable).map(VideoResponse::fromEntity);

    }

    public VideoResponse getVideo(String videoTitle) {
        Video video = videoRepository.findByTitle(videoTitle)
                .orElseThrow(() ->
                        new BusinessException
                                (ErrorCode.resource_missing, "There is no video like that!"));

        return VideoResponse.fromEntity(video);
    }

    public VideoResponse addVideo(CreateVideoRequest videoRequest, String authenticatedUserId) {
        determineWhetherAdmin(authenticatedUserId);

        Set<Category> categories = new HashSet<>();
        for (String categoryId : videoRequest.getCategoryIds()) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "The category not found"));
            categories.add(category);
        }
        Video video = new Video();
        video.setCategoriesOfTheVideo(categories);
        video.setTitle(videoRequest.getTitle());
        video.setDuration(videoRequest.getDuration());

        videoRepository.save(video);
        return VideoResponse.fromEntity(video);
    }
    @Transactional
    public void deleteVideo(String videoId, String authenticatedUserId) {

        determineWhetherAdmin(authenticatedUserId);
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing , "There is no video like that!"));

    videoRepository.deleteById(videoId);
    }

    public VideoResponse updateVideo(String videoId , UpdateVideoRequest updateVideoRequest ,
                                     String authenticatedUserId){

        determineWhetherAdmin(authenticatedUserId);
        Video video = videoRepository.findById(videoId).
                orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no video like that!"));

        Set<Category> categories = new HashSet<>();
        for (String categoryId : updateVideoRequest.getCategoryIds()) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "The category not found"));
            categories.add(category);
        }

        video.setCategoriesOfTheVideo(categories);
        video.setTitle(updateVideoRequest.getTitle());

        videoRepository.save(video);
        return VideoResponse.fromEntity(video);

    }


    public void determineWhetherAdmin(  String authenticatedUserId){
        User currentUser = userRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no user like that!"));

        if (!currentUser.getUserRole().equals(UserRole.ADMIN) ) {
            throw new BusinessException(ErrorCode.forbidden, "You dont have the privilege.");
        }
    }


}
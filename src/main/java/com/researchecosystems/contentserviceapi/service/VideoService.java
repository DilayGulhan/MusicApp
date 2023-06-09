package com.researchecosystems.contentserviceapi.service;

import com.researchecosystems.contentserviceapi.entity.Category;
import com.researchecosystems.contentserviceapi.entity.User;
import com.researchecosystems.contentserviceapi.entity.UserRole;
import com.researchecosystems.contentserviceapi.entity.Video;
import com.researchecosystems.contentserviceapi.exception.BusinessException;
import com.researchecosystems.contentserviceapi.exception.ErrorCode;
import com.researchecosystems.contentserviceapi.model.request.video.CreateVideoRequest;
import com.researchecosystems.contentserviceapi.model.request.video.UpdateVideoRequest;
import com.researchecosystems.contentserviceapi.model.response.VideoResponse;
import com.researchecosystems.contentserviceapi.repository.CategoryRepository;
import com.researchecosystems.contentserviceapi.repository.UserRepository;
import com.researchecosystems.contentserviceapi.repository.VideoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

        Category categoryOfTheVideo = categoryRepository.findById(videoRequest.getCategoryId())
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "The parent category not found"));

        Video video = new Video();
        video.setCategoryOfTheVideo(categoryOfTheVideo);
        video.setTitle(videoRequest.getTitle());
        video.setDuration(videoRequest.getDuration());
        categoryOfTheVideo.getVideosList().add(video);

        videoRepository.save(video);
        categoryRepository.save(categoryOfTheVideo);

        return VideoResponse.fromEntity(video);
    }
    @Transactional
    public void deleteVideo(String videoId, String authenticatedUserId) {

        determineWhetherAdmin(authenticatedUserId);
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing , "There is no video like that!"));
        Category categoryOfTheVideo = video.getCategoryOfTheVideo();
        categoryOfTheVideo.getVideosList().remove(video);
        categoryRepository.save(categoryOfTheVideo);
        videoRepository.deleteById(videoId);
    }

    public VideoResponse updateVideo(String videoId , UpdateVideoRequest updateVideoRequest ,
                                     String authenticatedUserId){

       determineWhetherAdmin(authenticatedUserId);
        Video video = videoRepository.findById(videoId).
                orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no video like that!"));
        Category categoryOfTheVideo = categoryRepository.findById(updateVideoRequest.getCategoryId())
                .orElseThrow(()-> new BusinessException(ErrorCode.resource_missing ,"The parent category not found"));


        video.setTitle(updateVideoRequest.getTitle());
        video.setCategoryOfTheVideo(categoryOfTheVideo);
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

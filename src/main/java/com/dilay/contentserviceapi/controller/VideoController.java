package com.dilay.contentserviceapi.controller;

import com.dilay.contentserviceapi.entity.Video;
import com.dilay.contentserviceapi.model.request.video.CreateVideoRequest;
import com.dilay.contentserviceapi.model.request.video.UpdateVideoRequest;
import com.dilay.contentserviceapi.model.response.VideoResponse;
import com.dilay.contentserviceapi.service.AuthenticationService;
import com.dilay.contentserviceapi.service.UserService;
import com.dilay.contentserviceapi.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/video")
public class VideoController {
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final VideoService videoService;


    @GetMapping
    @ApiPageable
    public Page<VideoResponse> listVideo(@ApiIgnore Pageable pageable) {
        return videoService.listVideos(pageable);
    }

    @PostMapping
    public VideoResponse addVideos(@Valid @RequestBody CreateVideoRequest request) {
        return videoService.addVideo(request, authenticationService.getAuthenticatedUserId());
    }

    @PutMapping("/{videoId}")
    public VideoResponse updateVideos(@Valid @RequestBody UpdateVideoRequest updateVideoRequest, @PathVariable String videoId) {
        return videoService.updateVideo(videoId, updateVideoRequest, authenticationService.getAuthenticatedUserId());
    }

    @DeleteMapping("/{videoId}")
    public void deleteVideos(@PathVariable String videoId) {
        videoService.deleteVideo(videoId, authenticationService.getAuthenticatedUserId());
    }

    @GetMapping("/{videoTitle}")
    public VideoResponse getVideo(@PathVariable String videoTitle) {
        return videoService.getVideo(videoTitle);
    }



}

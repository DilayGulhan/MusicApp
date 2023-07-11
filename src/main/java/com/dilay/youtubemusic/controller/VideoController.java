package com.dilay.youtubemusic.controller;

import com.dilay.youtubemusic.model.request.video.CreateVideoRequest;
import com.dilay.youtubemusic.model.request.video.UpdateVideoRequest;
import com.dilay.youtubemusic.model.response.VideoResponse;
import com.dilay.youtubemusic.service.AuthenticationService;
import com.dilay.youtubemusic.service.UserService;
import com.dilay.youtubemusic.service.VideoService;
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

    @GetMapping("/{videoId}")
    public VideoResponse getVideo(@PathVariable String videoId) {
        return videoService.getVideo(videoId);
    }



}

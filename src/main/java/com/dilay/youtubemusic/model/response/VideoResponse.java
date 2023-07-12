package com.dilay.youtubemusic.model.response;

import com.dilay.youtubemusic.entity.Category;
import com.dilay.youtubemusic.entity.Video;
import com.dilay.youtubemusic.exception.BusinessException;
import com.dilay.youtubemusic.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VideoResponse {
    private String id;
    private String title;
    private List<String> categoriesOfTheVideo;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private int duration ;

    public static VideoResponse fromEntity(Video video) {


        if (video.getDuration() <= 0 ){
            throw new BusinessException(ErrorCode.forbidden , "The duration can't be less than 0 ");
        }
        return VideoResponse.builder().
                id(video.getId()).
                title(video.getTitle()).
                categoriesOfTheVideo(video.getCategoriesOfTheVideo().stream().map(Category::getName).collect(Collectors.toList())).
                duration(video.getDuration()).
                createdAt(video.getCreated()).
                updatedAt(video.getUpdated()).build();
    }
}

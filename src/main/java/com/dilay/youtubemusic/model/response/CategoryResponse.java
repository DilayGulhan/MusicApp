package com.dilay.youtubemusic.model.response;

import com.dilay.youtubemusic.entity.Category;
import com.dilay.youtubemusic.entity.Video;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class CategoryResponse {
    private String id ;
    private String name;
    private boolean isSuperCategory;
    private List<String> videos ;
    private Category parent;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public static CategoryResponse fromEntity(Category category){
        return CategoryResponse.builder().id(category.getId())
                .name(category.getName())
                .isSuperCategory(category.isSuperCategory()).
               videos(category.getVideos().stream().map(Video::getTitle).collect(Collectors.toList())).
                parent(category.getParent()).
                createdAt(category.getCreated()).
                updatedAt(category.getUpdated()).build();





    }
}

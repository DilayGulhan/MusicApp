package com.dilay.youtubemusic.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Video extends BaseEntity {
    private String title;


    @ManyToMany
    @JoinTable(name = "category_video",
            joinColumns = @JoinColumn(name = "video_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    @Builder.Default
    private Set<Category> categoriesOfTheVideo = new HashSet<>();

    private Integer duration  ;

//    public void addCategory(Category category) {
//        this.categoriesOfTheVideo.add(category);
//    }
}

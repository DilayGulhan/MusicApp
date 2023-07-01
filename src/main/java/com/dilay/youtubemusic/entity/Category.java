package com.dilay.youtubemusic.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category extends BaseEntity {
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "category_video",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "video_id"))
    @JsonIgnoreProperties("categoryOfTheVideo")
    private List<Video> videosList = new LinkedList<>();
    private boolean isSuperCategory ;





}

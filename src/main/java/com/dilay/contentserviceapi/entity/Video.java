package com.dilay.contentserviceapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Video extends BaseEntity {
    private String title;


    @ManyToMany(mappedBy = "videosList", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("videosList")
    @Builder.Default
    private Set<Category> categoriesOfTheVideo = new HashSet<>();

    private Integer duration  ;

    public void addCategory(Category category) {
        this.categoriesOfTheVideo.add(category);
    }
}

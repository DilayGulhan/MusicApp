package com.researchecosystems.contentserviceapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.LinkedList;
import java.util.List;

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
    private List<Category> categoryOfTheVideo = new LinkedList<>();
    private Integer duration  ;
}

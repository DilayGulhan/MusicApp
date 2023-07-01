package com.dilay.youtubemusic.model.request.category;

import com.dilay.youtubemusic.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCategoryRequest {
    private String name ;
   private  Category parent ;
}

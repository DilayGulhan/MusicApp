package com.dilay.contentserviceapi.model.request.category;

import com.dilay.contentserviceapi.entity.Category;
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

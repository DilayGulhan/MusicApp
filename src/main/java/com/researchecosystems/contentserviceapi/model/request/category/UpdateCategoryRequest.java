package com.researchecosystems.contentserviceapi.model.request.category;

import com.researchecosystems.contentserviceapi.entity.Category;
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

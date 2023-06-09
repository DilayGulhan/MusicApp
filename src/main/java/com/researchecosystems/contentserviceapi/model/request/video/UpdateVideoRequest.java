package com.researchecosystems.contentserviceapi.model.request.video;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Data
@ToString
public class UpdateVideoRequest {
    @NotEmpty(message =  "Title must be filled")
    private String title ;
    @NotEmpty(message =  "Category must be filled")
    private String categoryId ;
}

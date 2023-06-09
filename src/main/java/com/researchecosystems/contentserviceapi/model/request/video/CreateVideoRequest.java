package com.researchecosystems.contentserviceapi.model.request.video;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class CreateVideoRequest {

    @NotEmpty(message =  "Title must be filled")
    private String title ;
    @NotEmpty(message =  "Category must be filled")
    private String categoryId ;
    // @NotEmpty(message =  "Duration must be filled")
    private int duration ;

}

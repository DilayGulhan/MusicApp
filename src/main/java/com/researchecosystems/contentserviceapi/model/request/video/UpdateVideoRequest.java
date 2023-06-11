package com.researchecosystems.contentserviceapi.model.request.video;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@ToString
public class UpdateVideoRequest {
    @NotEmpty(message =  "Title must be filled")
    private String title ;
    @NotEmpty(message =  "Category must be filled")
    private List<String> categoryIds ; // NOTE: I think you should make a different request for adding/removing categories
}

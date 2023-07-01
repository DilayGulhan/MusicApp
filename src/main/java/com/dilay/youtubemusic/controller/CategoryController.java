package com.dilay.youtubemusic.controller;


import com.dilay.youtubemusic.model.request.category.CreateCategoryRequest;
import com.dilay.youtubemusic.model.request.category.UpdateCategoryRequest;
import com.dilay.youtubemusic.model.response.CategoryResponse;
import com.dilay.youtubemusic.service.AuthenticationService;
import com.dilay.youtubemusic.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping("/category")
@Validated
@AllArgsConstructor
public class CategoryController {
    private  final CategoryService categoryService;
    private final AuthenticationService authenticationService;

    @GetMapping
    @ApiPageable
    public Page<CategoryResponse> listCategories(@ApiIgnore Pageable pageable) {
        return categoryService.listCategories(pageable );
    }

    @GetMapping ("/{categoryName}")
    public CategoryResponse getCategory(@PathVariable  String categoryName){
        return categoryService.getCategory(categoryName);
    }
    @PostMapping("/{parentId}")
    public CategoryResponse createCategory(@Valid @RequestBody CreateCategoryRequest createCategoryRequest , @PathVariable  String parentId) {
        return categoryService.addCategory( createCategoryRequest, parentId , authenticationService.getAuthenticatedUserId() );
    }
    @DeleteMapping("/{id}")
    public void deleteCategory( @PathVariable String id ){
        categoryService.deleteCategory(id,
                authenticationService.getAuthenticatedUserId());
    }

    @PutMapping("/{id}")
    public CategoryResponse updateCategory(@Valid @RequestBody UpdateCategoryRequest updateCategoryRequest , @PathVariable  String id){
        return categoryService.updateCategory(updateCategoryRequest , id , authenticationService.getAuthenticatedUserId());
    }

}

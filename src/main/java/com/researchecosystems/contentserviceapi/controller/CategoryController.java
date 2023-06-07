package com.researchecosystems.contentserviceapi.controller;


import com.researchecosystems.contentserviceapi.model.request.category.CreateCategoryRequest;
import com.researchecosystems.contentserviceapi.model.request.category.UpdateCategoryRequest;
import com.researchecosystems.contentserviceapi.model.response.CategoryResponse;
import com.researchecosystems.contentserviceapi.service.AuthenticationService;
import com.researchecosystems.contentserviceapi.service.CategoryService;
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

    @PostMapping("{parentId}")
    public CategoryResponse createCategory(@Valid @RequestBody CreateCategoryRequest createCategoryRequest , @PathVariable  String parentId) {
        return categoryService.addCategory( createCategoryRequest, parentId , authenticationService.getAuthenticatedUserId() );
    }
    @DeleteMapping("{id}")
    public void deleteCategory( @PathVariable String id ){
        categoryService.deleteCategory(id,
                authenticationService.getAuthenticatedUserId());
    }

    @PutMapping("{id}")
    public CategoryResponse updateCategory(@Valid @RequestBody UpdateCategoryRequest updateCategoryRequest , @PathVariable  String id){
        return categoryService.updateCategory(updateCategoryRequest , id , authenticationService.getAuthenticatedUserId());
    }

}

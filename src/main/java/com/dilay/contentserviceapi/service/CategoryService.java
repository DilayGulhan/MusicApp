package com.dilay.contentserviceapi.service;

import com.dilay.contentserviceapi.entity.Category;
import com.dilay.contentserviceapi.entity.User;
import com.dilay.contentserviceapi.entity.UserRole;
import com.dilay.contentserviceapi.exception.BusinessException;
import com.dilay.contentserviceapi.exception.ErrorCode;

import com.dilay.contentserviceapi.model.request.category.CreateCategoryRequest;
import com.dilay.contentserviceapi.model.request.category.UpdateCategoryRequest;
import com.dilay.contentserviceapi.model.response.CategoryResponse;
import com.dilay.contentserviceapi.repository.CategoryRepository;
import com.dilay.contentserviceapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public Page<CategoryResponse> listCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(CategoryResponse::fromEntity);
    }

    public CategoryResponse getCategory(String categoryName){
        Category category = categoryRepository.findCategoryByName(categoryName) .
                orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no category like that!"));
        return CategoryResponse.fromEntity(category);
    }

    public CategoryResponse addCategory(CreateCategoryRequest request, String parentId, String authenticatedUserId) {
        User currentUser = userRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no user like that!"));

        if (!currentUser.getUserRole().equals(UserRole.ADMIN)) {
            throw new BusinessException(ErrorCode.forbidden, "You dont have the privilege.");
        }
        Category parent = categoryRepository.findById(parentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "The parent category not found"));
        Category category = new Category();
        category.setParent(parent);
        category.setSuperCategory(false);
        category.setName(request.getName());
        category.setSuperCategory(false);

        categoryRepository.save(category);
        categoryRepository.save(parent);
        return CategoryResponse.fromEntity(category);
    }

    public void deleteCategory(String id, String authenticatedUserId) {   // following user listesi ekle
        User currentUser = userRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no user like that!"));

        if (!currentUser.getUserRole().equals(UserRole.ADMIN)) {
            throw new BusinessException(ErrorCode.forbidden, "You dont have the privilege.");
        }
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new BusinessException(ErrorCode.resource_missing, "The category id can't found"));
        if (category.isSuperCategory()) {
            throw new BusinessException(ErrorCode.forbidden, "The super category can't be deleted ");
        }

        categoryRepository.findAllByParent(category)
                .forEach(child ->  child.setParent(category.getParent()));

        categoryRepository.deleteById(id);
    }

    public CategoryResponse updateCategory(UpdateCategoryRequest request, String id, String authenticatedUserId) {
        User currentUser = userRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no user like that!"));

        if (!currentUser.getUserRole().equals(UserRole.ADMIN)) {
            throw new BusinessException(ErrorCode.forbidden, "You dont have the privilege.");
        }
        Category parent = categoryRepository.findById(request.getParent().getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "The parent category not found"));
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new BusinessException(ErrorCode.resource_missing, "The category id can't found"));
        category.setParent(request.getParent());
        category.setName(request.getName());
        categoryRepository.save(category);
        return CategoryResponse.fromEntity(category);

    }


}

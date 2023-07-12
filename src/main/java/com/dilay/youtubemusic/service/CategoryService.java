package com.dilay.youtubemusic.service;

import com.dilay.youtubemusic.entity.Category;
import com.dilay.youtubemusic.entity.User;
import com.dilay.youtubemusic.entity.UserRole;
import com.dilay.youtubemusic.entity.Video;
import com.dilay.youtubemusic.exception.BusinessException;
import com.dilay.youtubemusic.exception.ErrorCode;

import com.dilay.youtubemusic.model.request.category.CreateCategoryRequest;
import com.dilay.youtubemusic.model.request.category.UpdateCategoryRequest;
import com.dilay.youtubemusic.model.response.CategoryResponse;
import com.dilay.youtubemusic.repository.CategoryRepository;
import com.dilay.youtubemusic.repository.UserRepository;
import com.dilay.youtubemusic.repository.VideoRepository;
import lombok.AllArgsConstructor;
import org.hibernate.tool.schema.internal.IndividuallySchemaValidatorImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final VideoRepository videoRepository ;
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

        categoryRepository.save(parent);
        categoryRepository.save(category);

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


        for(Video video : category.getVideos() ){
            video.getCategoriesOfTheVideo().remove(category);
            video.getCategoriesOfTheVideo().add(category.getParent());
            videoRepository.save(video);
        }

//        categoryRepository.findAllByParent(category)
//                .forEach(child ->  child.setParent(category.getParent()));
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

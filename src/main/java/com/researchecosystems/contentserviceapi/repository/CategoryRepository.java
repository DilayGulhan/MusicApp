package com.researchecosystems.contentserviceapi.repository;

import com.researchecosystems.contentserviceapi.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category , String > {
  public Optional<Category> findCategoryByName(String name);



}

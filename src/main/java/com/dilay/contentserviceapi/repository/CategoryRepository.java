package com.dilay.contentserviceapi.repository;

import com.dilay.contentserviceapi.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category , String > {
  public Optional<Category> findCategoryByName(String name);
  public Set<Category> findAllByParent(Category parent);

}

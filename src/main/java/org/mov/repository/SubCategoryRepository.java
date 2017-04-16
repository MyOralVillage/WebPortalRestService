package org.mov.repository;

import org.mov.entity.SubCategory;

import java.util.Collection;

public interface SubCategoryRepository {
    void saveSubCategory(SubCategory subCategory);

    void removeSubCategory(SubCategory subCategory);

    SubCategory findSubCategoryById(Long id);

    Collection<SubCategory> findAllSubCategories();
}

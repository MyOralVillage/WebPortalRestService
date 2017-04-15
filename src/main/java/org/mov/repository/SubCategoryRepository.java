package org.mov.repository;

import org.mov.entity.SubCategory;

import java.util.Collection;

public interface SubCategoryRepository {
    void saveSubCategory(SubCategory subCategory);

    void removeSubCategory(SubCategory subCategory);

    SubCategory findSubCategoryById(Long id);

    SubCategory findSubcategoryByName(String name);

    Collection<SubCategory> findAllSubCategories();
}

package org.mov.api;

import org.mov.entity.SubCategory;
import org.mov.service.MOVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/subcategories")
public class SubCategoryController {
    private MOVService movService;

    @Autowired
    public SubCategoryController(MOVService movService) {
        this.movService = movService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<SubCategory> showSubCategories() {
        return new ArrayList<>(movService.findAllSubCategories());
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public void addSubcategory(@RequestBody SubCategory subCategory) {
        movService.saveSubCategory(subCategory);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public void saveSubCategory(@PathVariable("id") Long id,
                                @RequestBody SubCategory subCategory) {
        subCategory.setId(id);
        movService.saveSubCategory(subCategory);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void removeSubCategory(@PathVariable("id") Long id) {
        SubCategory subCategory = new SubCategory();
        subCategory.setId(id);
        movService.removeSubCategory(subCategory);
    }
}

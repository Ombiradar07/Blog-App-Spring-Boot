package com.osmblog.Services;

import com.osmblog.Payload.CategoryDto;
import java.util.List;


public interface CategoryService {

    //create
    CategoryDto createCategory(CategoryDto  categoryDto);

    //update
    CategoryDto updateCategory(Long categoryId , CategoryDto  categoryDto);

    //delete
    void deleteCategory(Long categoryId);

    // get
    CategoryDto getCategoryById(Long categoryId);

    // get all

    List<CategoryDto> getAllCategories();
}

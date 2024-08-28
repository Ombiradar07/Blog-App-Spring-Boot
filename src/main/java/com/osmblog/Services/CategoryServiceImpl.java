package com.osmblog.Services;

import com.osmblog.Entities.Category;
import com.osmblog.Exceptions.ResourceNotFoundException;
import com.osmblog.Payload.CategoryDto;
import com.osmblog.Repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl  implements CategoryService{

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category categoryEntity = modelMapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(categoryEntity);
       return modelMapper.map(savedCategory,CategoryDto.class);

    }

    @Override
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + categoryId));

        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());

        Category updatedCategory = categoryRepository.save(category);

        return modelMapper.map(updatedCategory,CategoryDto.class);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with id :" + categoryId));

        categoryRepository.deleteById(categoryId);
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with id :" + categoryId));

        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> AllCategories = categoryRepository.findAll();
        return AllCategories.stream().map(((category) -> this.modelMapper.map(category,CategoryDto.class))).collect(Collectors.toList());

    }
}

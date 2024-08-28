package com.osmblog.Controllers;

import com.osmblog.Payload.CategoryDto;
import com.osmblog.Services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CategoryController {

    @Autowired
    CategoryService categoryService;


    @PostMapping("admin/addCategory")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDto categoryDto, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        CategoryDto catDto = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(catDto,HttpStatus.CREATED);

    }


    @PutMapping("admin/category/{catId}")
    public ResponseEntity<?> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Long catId,BindingResult result){
        if (result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.BAD_REQUEST);
        }

        CategoryDto updatedDto = categoryService.updateCategory(catId, categoryDto);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }
    //GetMapping

    @GetMapping("/category/{catId}")
    public ResponseEntity<?> getCategoryByID(@PathVariable Long catId){
        CategoryDto categoryById = categoryService.getCategoryById(catId);
         return new ResponseEntity<>(categoryById,HttpStatus.OK);
    }

    //DeleteMapping

    @DeleteMapping("admin/category/{catId}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable Long catId){
        categoryService.deleteCategory(catId);
        return new ResponseEntity<>("Category deleted Successfully!!!",HttpStatus.OK);
    }

    @GetMapping("/category/getAll")
    public ResponseEntity<?> getAllCategories(){
        List<CategoryDto> allCategories = categoryService.getAllCategories();
        return new ResponseEntity<>(allCategories,HttpStatus.OK);
    }

}

package com.product.api.controller;

import com.product.api.dto.ResponseApi;
import com.product.api.entity.Category;
import com.product.api.service.CategoryService;
import com.product.exception.ExceptionApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController @RequestMapping("/category") public class CategoryController
{

    @Resource
    CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getCategories() throws Exception
    {
        return new ResponseEntity<>(categoryService.getCategories(), HttpStatus.OK);
    }

    @GetMapping("/{category_id}")
    public ResponseEntity<Category> getCategory(@PathVariable int category_id)
    {
        return new ResponseEntity<>(categoryService.getCategory(category_id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseApi> createCategory(@Valid @RequestBody Category category, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            throw new ExceptionApi(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return new ResponseEntity<>(categoryService.createCategory(category), HttpStatus.OK);
    }

    @PutMapping("/{category_id}")
    public ResponseEntity<ResponseApi> updateCategory(
            @PathVariable int category_id, @Valid @RequestBody Category category, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            throw new ExceptionApi(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return new ResponseEntity<>(categoryService.updateCategory(category_id, category), HttpStatus.OK);
    }

    @DeleteMapping("/{category_id}")
    public ResponseEntity<ResponseApi> deleteCategory(@PathVariable int category_id)
    {
        return new ResponseEntity<>(categoryService.deleteCategory(category_id), HttpStatus.OK);
    }
}

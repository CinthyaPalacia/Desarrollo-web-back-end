package com.product.api.service;

import com.product.api.dto.ResponseApi;
import com.product.api.entity.Category;

import java.util.List;

public interface CategoryService
{

    List<Category> getCategories();

    Category getCategory(Integer category_id);

    ResponseApi createCategory(Category category);

    ResponseApi updateCategory(Integer category_id, Category category);

    //Eliminar categoría
    ResponseApi deleteCategory(Integer category_id);
}

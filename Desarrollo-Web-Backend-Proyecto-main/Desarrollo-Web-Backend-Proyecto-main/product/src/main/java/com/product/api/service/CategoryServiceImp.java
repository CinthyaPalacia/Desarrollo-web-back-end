package com.product.api.service;

import com.product.api.dto.ResponseApi;
import com.product.api.entity.Category;
import com.product.api.repository.CategoryRepository;
import com.product.exception.ExceptionApi;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service public class CategoryServiceImp implements CategoryService
{

    @Resource
    CategoryRepository categoryRepository;

    @Override
    public List<Category> getCategories()
    {
        return categoryRepository.findByStatus(1);
    }

    @Override
    public Category getCategory(Integer category_id)
    {
        Category category = categoryRepository.findByCategoryId(category_id);
        if (category == null)
        {
            throw new ExceptionApi(HttpStatus.BAD_REQUEST, "category does not exists");
        }
        else
        {
            return category;
        }
    }

    @Override
    public ResponseApi createCategory(Category category)
    {
        Category categoryS = categoryRepository.findByCategory(category.getCategory());
        if (categoryS != null)
        {
            if (categoryS.getStatus() == 0)
            {
                categoryRepository.activateCategory(categoryS.getCategory_id());
                return new ResponseApi("category has been activated");
            }
            else
            {
                throw new ExceptionApi(HttpStatus.BAD_REQUEST, "category already exists");
            }
        }
        categoryRepository.createCategory(category.getCategory());
        return new ResponseApi("category created");
    }

    @Override
    public ResponseApi updateCategory(Integer category_id, Category category)
    {
        Category categoryS = categoryRepository.findByCategoryId(category_id);
        if (categoryS == null)
        {
            throw new ExceptionApi(HttpStatus.BAD_REQUEST, "category does not exist");
        }
        else
        {
            if (categoryS.getStatus() == 0)
            {
                throw new ExceptionApi(HttpStatus.BAD_REQUEST, "category is not active");
            }
            else
            {
                categoryS = categoryRepository.findByCategory(category.getCategory());
                if (categoryS != null)
                {
                    throw new ExceptionApi(HttpStatus.BAD_REQUEST, "category already exists");
                }
                categoryRepository.updateCategory(category_id, category.getCategory());
                return new ResponseApi("category update");
            }
        }
    }

    @Override
    public ResponseApi deleteCategory(Integer category_id)
    {
        Category categoryS = categoryRepository.findByCategoryId(category_id);
        if (categoryS == null)
        {
            throw new ExceptionApi(HttpStatus.NOT_FOUND, "category does not exist");
        }
        else
        {
            categoryRepository.deleteById(category_id);
            return new ResponseApi("category removed");
        }
    }
}

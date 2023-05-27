package com.product.api.service;

import com.product.api.dto.CategoryDTO;
import com.product.api.dto.ProductResponse;
import com.product.api.dto.ResponseApi;
import com.product.api.entity.Category;
import com.product.api.entity.Product;
import com.product.api.repository.CategoryRepository;
import com.product.api.repository.ProductRepository;
import com.product.exception.ExceptionApi;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Service public class ProductServiceImp implements ProductService
{

    @Resource
    ProductRepository productRepository;

    @Resource
    CategoryRepository categoryRepository;

    @Override
    public Product getProduct(String gtin)
    {
        Product product = productRepository.findByStatusAndGtin(gtin);
        if (product != null)
        {
            product.setCategory(categoryRepository.findByCategoryId(product.getCategory_id()));
            return product;
        }
        else
        {
            throw new ExceptionApi(HttpStatus.NOT_FOUND, "product does not exist");
        }
    }

    @Override
    public List<ProductResponse> getProducts(Integer categoryId)
    {
        List<Product> result = productRepository.findByProdCat(categoryId);
        List<ProductResponse> products = new ArrayList<>();
        for (Product p : result)
        {
            ProductResponse prod = new ProductResponse(p.getProduct_id(), p.getGtin(), p.getProduct(), p.getPrice());
            products.add(prod);
        }
        return products;
    }

    @Override
    public ResponseApi createProduct(Product in)
    {
        Product product = productRepository.findByGtin(in.getGtin());
        Category categoryS = categoryRepository.findByCategoryId(in.getCategory_id());

        if (categoryS == null)
        {
            throw new ExceptionApi(HttpStatus.NOT_FOUND, "Category not found");
        }

        if (product != null)
        {
            if (product.getStatus() == 0)
            {
                productRepository.activateProduct(product.getProduct_id());
                return new ResponseApi("product activated");
            }
            else
            {
                throw new ExceptionApi(HttpStatus.BAD_REQUEST, "Product gtin already exists");
            }
        }

        product = productRepository.findByProduct(in.getProduct());
        if (product != null)
        {
            throw new ExceptionApi(HttpStatus.BAD_REQUEST, "product name already exists");
        }

        productRepository.createProduct(in.getGtin(), in.getProduct(), in.getDescription(), in.getPrice(), in.getStock(),
                                        in.getCategory_id());
        return new ResponseApi("product created");
    }

    @Override
    public ResponseApi updateProduct(Product in, Integer id)
    {
        Integer updated = 0;
        try
        {
            updated = productRepository.updateProduct(id, in.getGtin(), in.getProduct(), in.getDescription(), in.getPrice(), in.getStock(),
                                                      in.getCategory_id());
        }
        catch (DataIntegrityViolationException e)
        {
            if (e.getLocalizedMessage().contains("gtin"))
            {
                throw new ExceptionApi(HttpStatus.BAD_REQUEST, "product gtin already exist");
            }
            if (e.getLocalizedMessage().contains("product"))
            {
                throw new ExceptionApi(HttpStatus.BAD_REQUEST, "product name already exist");
            }
            if (e.contains(SQLIntegrityConstraintViolationException.class))
            {
                throw new ExceptionApi(HttpStatus.BAD_REQUEST, "category not found");
            }
        }
        if (updated == 0)
        {
            throw new ExceptionApi(HttpStatus.BAD_REQUEST, "product cannot be updated");
        }
        else
        {
            return new ResponseApi("product updated");
        }
    }

    @Override
    public ResponseApi deleteProduct(Integer id)
    {
        if (productRepository.deleteProduct(id) > 0)
        {
            return new ResponseApi("product removed");
        }
        else
        {
            throw new ExceptionApi(HttpStatus.BAD_REQUEST, "product cannot be deleted");
        }
    }

    @Override
    public ResponseApi updateProductStock(String gtin, Integer stock)
    {
        Product product = getProduct(gtin);
        if (stock > product.getStock())
        {
            throw new ExceptionApi(HttpStatus.BAD_REQUEST, "stock to update is invalid");
        }

        productRepository.updateProductStock(gtin, product.getStock() - stock);
        return new ResponseApi("product stock updated");
    }

    @Override
    public ResponseApi updateProdCategory(String gtin, CategoryDTO catId)
    {
        Category cat = categoryRepository.findByCategoryId(catId.getId());
        Product prod = productRepository.findByGtin(gtin);
        if (prod == null)
        {
            throw new ExceptionApi(HttpStatus.BAD_REQUEST, "category not found");
        }
        if (prod.getStatus() == 0)
        {
            throw new ExceptionApi(HttpStatus.NOT_FOUND, "product does not exists");
        }

        productRepository.updateProdCat(gtin, catId.getId());
        return new ResponseApi("product category updated");
    }
}

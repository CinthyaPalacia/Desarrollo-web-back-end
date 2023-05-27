package com.product.api.controller;

import com.product.api.dto.CategoryDTO;
import com.product.api.dto.ProductResponse;
import com.product.api.dto.ResponseApi;
import com.product.api.entity.Product;
import com.product.api.service.ProductService;
import com.product.exception.ExceptionApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController @RequestMapping("/product") public class ProductController
{

    @Resource
    ProductService productService;

    @GetMapping("/{gtin}")
    public ResponseEntity<Product> getProduct(@PathVariable String gtin)
    {
        return new ResponseEntity<>(productService.getProduct(gtin), HttpStatus.OK);
    }

    @GetMapping("/category/{category_id}")
    public ResponseEntity<List<ProductResponse>> listProducts(@PathVariable("category_id") Integer category_id)
    {
        return new ResponseEntity<>(productService.getProducts(category_id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseApi> createProduct(@Valid @RequestBody Product in, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            throw new ExceptionApi(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return new ResponseEntity<>(productService.createProduct(in), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseApi> updateProduct(
            @PathVariable("id") Integer id, @Valid @RequestBody Product in, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            throw new ExceptionApi(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return new ResponseEntity<>(productService.updateProduct(in, id), HttpStatus.OK);
    }

    @PutMapping("/{gtin}/stock/{stock}")
    public ResponseEntity<ResponseApi> updateProductStock(@PathVariable("gtin") String gtin, @PathVariable("stock") Integer stock)
    {
        return new ResponseEntity<>(productService.updateProductStock(gtin, stock), HttpStatus.OK);
    }

    @PutMapping("/{gtin}/category")
    public ResponseEntity<ResponseApi> updateProductCategory(
            @PathVariable("gtin") String gtin, @Valid @RequestBody CategoryDTO dto, BindingResult bindingResult)
    {
        return new ResponseEntity<>(productService.updateProdCategory(gtin, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseApi> deleteProduct(@PathVariable("id") Integer id)
    {
        return new ResponseEntity<>(productService.deleteProduct(id), HttpStatus.OK);
    }
}

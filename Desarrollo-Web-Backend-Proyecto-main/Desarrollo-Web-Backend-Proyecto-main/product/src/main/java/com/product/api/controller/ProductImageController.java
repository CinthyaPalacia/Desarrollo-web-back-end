package com.product.api.controller;

import com.product.api.dto.ResponseApi;
import com.product.api.entity.ProductImage;
import com.product.api.service.ProductImageService;
import com.product.exception.ExceptionApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController @RequestMapping("/product-image") public class ProductImageController
{

    @Resource
    ProductImageService productImageService;

    @GetMapping("/{product_id}")
    public ResponseEntity<List<ProductImage>> getProductImages(@PathVariable("product_id") Integer product_id)
    {
        return new ResponseEntity<>(productImageService.getProductImages(product_id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseApi> uploadProductImage(@Valid @RequestBody ProductImage in, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            throw new ExceptionApi(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return new ResponseEntity<>(productImageService.createProductImage(in), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseApi> deleteProductImage(@PathVariable("id") Integer id)
    {
        return new ResponseEntity<>(productImageService.deleteProductImage(id), HttpStatus.OK);
    }

}

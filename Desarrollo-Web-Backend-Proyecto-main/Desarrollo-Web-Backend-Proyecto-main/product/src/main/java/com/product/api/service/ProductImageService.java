package com.product.api.service;

import com.product.api.dto.ResponseApi;
import com.product.api.entity.ProductImage;

import java.util.List;

public interface ProductImageService
{

    List<ProductImage> getProductImages(Integer product_id);

    ResponseApi createProductImage(ProductImage in);

    ResponseApi deleteProductImage(Integer id);

}

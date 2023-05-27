package com.product.api.service;

import com.product.api.dto.CategoryDTO;
import com.product.api.dto.ProductResponse;
import com.product.api.dto.ResponseApi;
import com.product.api.entity.Product;

import java.util.List;

public interface ProductService
{

    Product getProduct(String gtin);

    List<ProductResponse> getProducts(Integer catId);

    ResponseApi createProduct(Product in);

    ResponseApi updateProduct(Product in, Integer id);

    ResponseApi updateProductStock(String gtin, Integer stock);

    ResponseApi updateProdCategory(String gtin, CategoryDTO categorydto);

    ResponseApi deleteProduct(Integer id);

}

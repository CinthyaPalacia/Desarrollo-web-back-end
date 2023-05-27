package com.invoice.configuration.client;

import com.invoice.api.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@FeignClient(name = "product-service") public interface ProductClient
{
    @GetMapping("product/{gtin}")
    ResponseEntity<ProductDto> getProduct(@PathVariable String gtin);

    @PutMapping("product/{gtin}/stock/{stock}")
    ResponseEntity<ProductDto> updateProductStock(@PathVariable("gtin") String gtin, @PathVariable("stock") Integer stock);

}

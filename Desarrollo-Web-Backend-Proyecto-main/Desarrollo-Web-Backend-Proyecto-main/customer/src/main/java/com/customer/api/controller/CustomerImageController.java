package com.customer.api.controller;

import com.customer.api.dto.ResponseApi;
import com.customer.api.entity.CustomerImage;
import com.customer.api.service.CustomerImageService;
import com.customer.exception.ExceptionApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController @RequestMapping("/customer-image") public class CustomerImageController
{

    @Resource
    CustomerImageService customerImageService;

    @PutMapping
    public ResponseEntity<ResponseApi> setCustomerImage(@Valid @RequestBody CustomerImage in, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            throw new ExceptionApi(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        return new ResponseEntity<>(customerImageService.setCustomerImage(in), HttpStatus.OK);
    }
}

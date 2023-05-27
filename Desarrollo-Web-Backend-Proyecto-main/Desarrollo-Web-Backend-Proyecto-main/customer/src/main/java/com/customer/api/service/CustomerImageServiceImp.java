package com.customer.api.service;

import com.customer.api.dto.ResponseApi;
import com.customer.api.entity.CustomerImage;
import com.customer.api.repository.CustomerImageRepository;
import com.customer.exception.ExceptionApi;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service public class CustomerImageServiceImp implements CustomerImageService
{

    @Resource
    CustomerImageRepository customerImageRepository;

    @Override
    public ResponseApi setCustomerImage(CustomerImage in)
    {
        Integer updated = customerImageRepository.setCustomerImage(in.getCustomer_image_id(), in.getCustomer_image());
        if (updated == 1)
        {
            return new ResponseApi("customer image updated");
        }
        else
        {
            throw new ExceptionApi(HttpStatus.NOT_FOUND, "customer image does not exist");
        }
    }

}

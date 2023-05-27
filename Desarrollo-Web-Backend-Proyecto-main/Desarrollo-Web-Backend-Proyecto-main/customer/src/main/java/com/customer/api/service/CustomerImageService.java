package com.customer.api.service;

import com.customer.api.dto.ResponseApi;
import com.customer.api.entity.CustomerImage;

public interface CustomerImageService
{

	ResponseApi setCustomerImage(CustomerImage in);
}

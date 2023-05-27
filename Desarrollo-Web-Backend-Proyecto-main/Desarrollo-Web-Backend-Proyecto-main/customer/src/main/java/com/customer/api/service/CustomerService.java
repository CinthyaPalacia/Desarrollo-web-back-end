package com.customer.api.service;

import com.customer.api.dto.CustomerListDto;
import com.customer.api.dto.ResponseApi;
import com.customer.api.entity.Customer;
import com.customer.api.entity.Region;

import java.util.List;

public interface CustomerService
{

    List<CustomerListDto> getCustomers();

    Customer getCustomer(String rfc);

    ResponseApi createCustomer(Customer in);

    ResponseApi updateCustomer(Customer in, Integer id);

    ResponseApi deleteCustomer(Integer id);

    ResponseApi updateCustomerRegion(Region region, Integer id);

}

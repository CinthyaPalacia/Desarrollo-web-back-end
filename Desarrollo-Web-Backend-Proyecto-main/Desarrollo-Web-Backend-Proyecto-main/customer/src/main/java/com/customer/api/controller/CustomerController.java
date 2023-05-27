package com.customer.api.controller;

import com.customer.api.dto.CustomerListDto;
import com.customer.api.dto.ResponseApi;
import com.customer.api.entity.Customer;
import com.customer.api.entity.Region;
import com.customer.api.service.CustomerService;
import com.customer.exception.ExceptionApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController @RequestMapping("/customer") public class CustomerController
{

    @Resource
    CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerListDto>> getCustomers()
    {
        return new ResponseEntity<>(customerService.getCustomers(), HttpStatus.OK);
    }

    @GetMapping("/{rfc}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("rfc") String rfc)
    {
        return new ResponseEntity<>(customerService.getCustomer(rfc), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseApi> createCustomer(@Valid @RequestBody Customer in, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            throw new ExceptionApi(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return new ResponseEntity<>(customerService.createCustomer(in), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseApi> updateCustomer(
            @PathVariable("id") Integer id, @Valid @RequestBody Customer in, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            throw new ExceptionApi(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return new ResponseEntity<>(customerService.updateCustomer(in, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseApi> deleteCustomer(@PathVariable("id") Integer id)
    {
        return new ResponseEntity<>(customerService.deleteCustomer(id), HttpStatus.OK);
    }

    @PutMapping("/{id}/region")
    public ResponseEntity<ResponseApi> updateCustomerRegion(@PathVariable("id") Integer id, @RequestBody Region in)
    {
        return new ResponseEntity<>(customerService.updateCustomerRegion(in, id), HttpStatus.OK);
    }

}


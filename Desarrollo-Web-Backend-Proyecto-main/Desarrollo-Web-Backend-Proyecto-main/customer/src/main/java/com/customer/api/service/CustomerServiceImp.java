package com.customer.api.service;

import com.customer.api.dto.CustomerListDto;
import com.customer.api.dto.ResponseApi;
import com.customer.api.entity.Customer;
import com.customer.api.entity.CustomerImage;
import com.customer.api.entity.Region;
import com.customer.api.repository.CustomerListRepository;
import com.customer.api.repository.CustomerRepository;
import com.customer.exception.ExceptionApi;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service public class CustomerServiceImp implements CustomerService
{

    @Resource
    CustomerRepository customerRepository;

    @Resource
    CustomerListRepository customerListRepository;


    @Override
    public List<CustomerListDto> getCustomers()
    {
        return customerListRepository.findByStatus(1);
    }

    @Override
    public Customer getCustomer(String rfc)
    {
        Customer customer = customerRepository.findByRfcAndStatus(rfc, 1);
        if (customer != null)
        {
            return customer;
        }
        else
        {
            throw new ExceptionApi(HttpStatus.NOT_FOUND, "customer does not exist");
        }
    }

    @Override
    public ResponseApi createCustomer(Customer in)
    {
        in.setStatus(1);
        in.setCustomerImage(new CustomerImage());
        try
        {
            customerRepository.save(in);
        }
        catch (DataIntegrityViolationException e)
        {
            if (e.getLocalizedMessage().contains("rfc"))
            {
                throw new ExceptionApi(HttpStatus.BAD_REQUEST, "customer rfc already exist");
            }
            if (e.getLocalizedMessage().contains("mail"))
            {
                throw new ExceptionApi(HttpStatus.BAD_REQUEST, "customer mail already exist");
            }
        }
        return new ResponseApi("customer created");
    }

    @Override
    public ResponseApi updateCustomer(Customer in, Integer id)
    {
        try
        {
            customerRepository.updateCustomer(id, in.getName(), in.getSurname(), in.getDate_birth(), in.getRfc(), in.getMail(),
                                              in.getAddress());
        }
        catch (DataIntegrityViolationException e)
        {
            if (e.getLocalizedMessage().contains("rfc"))
            {
                throw new ExceptionApi(HttpStatus.BAD_REQUEST, "customer rfc already exist");
            }
            if (e.getLocalizedMessage().contains("mail"))
            {
                throw new ExceptionApi(HttpStatus.BAD_REQUEST, "customer mail already exist");
            }
        }
        return new ResponseApi("customer updated");
    }

    @Override
    public ResponseApi deleteCustomer(Integer id)
    {
        if (customerRepository.deleteCustomer(id) > 0)
        {
            return new ResponseApi("customer removed");
        }
        else
        {
            throw new ExceptionApi(HttpStatus.BAD_REQUEST, "customer cannot be deleted");
        }
    }

    @Override
    public ResponseApi updateCustomerRegion(Region region, Integer id)
    {
        try
        {
            if (customerRepository.updateCustomerRegion(region.getRegion_id(), id) > 0)
            {
                return new ResponseApi("customer region updated");
            }
            else
            {
                throw new ExceptionApi(HttpStatus.BAD_REQUEST, "customer region cannot be updated");
            }
        }
        catch (DataIntegrityViolationException e)
        {
            throw new ExceptionApi(HttpStatus.NOT_FOUND, "region not found");
        }
    }

}

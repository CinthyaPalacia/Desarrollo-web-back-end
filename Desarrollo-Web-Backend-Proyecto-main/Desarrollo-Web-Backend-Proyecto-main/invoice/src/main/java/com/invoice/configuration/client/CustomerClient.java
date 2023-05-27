package com.invoice.configuration.client;

import com.invoice.api.dto.CustomerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service") public interface CustomerClient
{

    @GetMapping("customer/{rfc}")
    ResponseEntity<CustomerDto> getCustomer(@PathVariable("rfc") String rfc);
}

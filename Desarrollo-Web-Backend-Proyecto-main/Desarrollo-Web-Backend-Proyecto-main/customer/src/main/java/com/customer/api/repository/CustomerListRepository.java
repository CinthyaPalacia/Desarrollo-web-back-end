package com.customer.api.repository;

import com.customer.api.dto.CustomerListDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository public interface CustomerListRepository extends JpaRepository<CustomerListDto, Integer>
{

    List<CustomerListDto> findByStatus(Integer status);

}

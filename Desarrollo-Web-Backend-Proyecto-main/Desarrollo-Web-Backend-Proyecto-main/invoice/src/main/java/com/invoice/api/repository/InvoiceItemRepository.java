package com.invoice.api.repository;

import com.invoice.api.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository public interface InvoiceItemRepository extends JpaRepository<Item, Integer>
{

    @Query(value = "SELECT * FROM item WHERE invoice_id = :invoice_id AND status = 1", nativeQuery = true)
    List<Item> getInvoiceItems(@Param("invoice_id") Integer invoice_id);

}

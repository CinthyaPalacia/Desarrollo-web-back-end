package com.invoice.api.controller;

import com.invoice.api.dto.ResponseApi;
import com.invoice.api.entity.Invoice;
import com.invoice.api.entity.Item;
import com.invoice.api.service.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController @RequestMapping("/invoice") public class InvoiceController
{

    @Resource
    InvoiceService invoiceService;

    @GetMapping("/{rfc}")
    public ResponseEntity<List<Invoice>> getInvoices(@PathVariable("rfc") String rfc)
    {
        return new ResponseEntity<>(invoiceService.getInvoices(rfc), HttpStatus.OK);
    }

    @GetMapping("/{id}/item")
    public ResponseEntity<List<Item>> getInvoiceItems(@PathVariable("id") Integer id)
    {
        return new ResponseEntity<>(invoiceService.getInvoiceItems(id), HttpStatus.OK);
    }

    @PostMapping("/{rfc}")
    public ResponseEntity<ResponseApi> generateInvoice(@PathVariable("rfc") String rfc)
    {
        return new ResponseEntity<>(invoiceService.generateInvoice(rfc), HttpStatus.CREATED);
    }
}

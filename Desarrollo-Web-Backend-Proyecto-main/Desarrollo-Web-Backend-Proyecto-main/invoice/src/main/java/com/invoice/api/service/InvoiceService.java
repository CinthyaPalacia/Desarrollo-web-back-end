package com.invoice.api.service;

import java.util.List;

import com.invoice.api.dto.ResponseApi;
import com.invoice.api.entity.Invoice;
import com.invoice.api.entity.Item;

public interface InvoiceService
{

    List<Invoice> getInvoices(String rfc);

    List<Item> getInvoiceItems(Integer invoice_id);

    ResponseApi generateInvoice(String rfc);
}

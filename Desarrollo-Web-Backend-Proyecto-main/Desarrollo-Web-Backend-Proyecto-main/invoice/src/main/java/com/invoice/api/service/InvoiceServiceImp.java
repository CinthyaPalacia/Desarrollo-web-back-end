package com.invoice.api.service;

import com.invoice.api.dto.ResponseApi;
import com.invoice.api.entity.Cart;
import com.invoice.api.entity.Invoice;
import com.invoice.api.entity.Item;
import com.invoice.api.repository.CartRepository;
import com.invoice.api.repository.InvoiceItemRepository;
import com.invoice.api.repository.InvoiceRepository;
import com.invoice.configuration.client.ProductClient;
import com.invoice.exception.ExceptionApi;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service public class InvoiceServiceImp implements InvoiceService
{

    @Resource
    InvoiceRepository invoiceRepository;

    @Resource
    InvoiceItemRepository invoiceItemRepository;

    @Resource
    CartRepository cartRepository;

    @Resource
    ProductClient productClient;

    @Override
    public List<Invoice> getInvoices(String rfc)
    {
        return invoiceRepository.findByRfcAndStatus(rfc, 1);
    }

    @Override
    public List<Item> getInvoiceItems(Integer invoice_id)
    {
        return invoiceItemRepository.getInvoiceItems(invoice_id);
    }

    @Override
    public ResponseApi generateInvoice(String rfc)
    {
        List<Cart> carros = cartRepository.findByRfcAndStatus(rfc, 1);
        if (carros.isEmpty())
        {
            throw new ExceptionApi(HttpStatus.NOT_FOUND, "cart has nos items");
        }
        Invoice invoice = new Invoice();
        invoice.setRfc(rfc);
        invoice.setTaxes(0.0);
        invoice.setTotal(-1.0);
        invoice.setSubtotal(0.0);
        invoice.setCreated_at(LocalDateTime.now());
        invoice.setStatus(1);
        invoiceRepository.save(invoice);

        List<Invoice> facturas = invoiceRepository.findByRfcAndStatus(rfc, 1);
        Invoice factura = null;
        for (Invoice i : facturas)
        {
            if (i.getTotal() == -1)
            {
                factura = i;
                System.out.println(factura.getInvoice_id());
                break;
            }
        }

        List<Item> articulos = new ArrayList<Item>();
        for (Cart c : carros)
        {
            articulos.add(createItemAux(rfc, c, factura));
            productClient.updateProductStock(c.getGtin(), c.getQuantity());

            cartRepository.clearCart(c.getRfc());
        }

        double total = 0.0;
        double impuestos = 0.0;
        double subtotal = 0.0;
        for (Item it : articulos)
        {
            total += it.getTotal();
            impuestos += it.getTaxes();
            subtotal += it.getSubtotal();
        }
        factura.setSubtotal(subtotal);
        factura.setTaxes(impuestos);
        factura.setTotal(total);
        factura.setCreated_at(LocalDateTime.now());
        factura.setStatus(1);
        invoiceRepository.updateInvoice(factura.getInvoice_id(), factura.getRfc(), factura.getSubtotal(), factura.getTaxes(),
                                        factura.getTotal(), factura.getCreated_at());

        return new ResponseApi("invoice generated");
    }

    private Item createItemAux(String rfc, Cart carro, Invoice factura)
    {
        Item articulo = new Item();
        articulo.setId_invoice(factura.getInvoice_id());
        articulo.setGtin(carro.getGtin());
        articulo.setQuantity(carro.getQuantity());
        double precio = productClient.getProduct(carro.getGtin()).getBody().getPrice();
        articulo.setUnit_price(precio);
        double total = carro.getQuantity() * precio;
        articulo.setTaxes(total * 0.16);
        articulo.setTotal(total);
        articulo.setSubtotal(total - (articulo.getTaxes()));
        articulo.setStatus(1);

        invoiceItemRepository.save(articulo);
        return articulo;
    }

}

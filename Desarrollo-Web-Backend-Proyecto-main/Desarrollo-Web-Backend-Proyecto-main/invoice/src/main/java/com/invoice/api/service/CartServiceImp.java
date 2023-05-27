package com.invoice.api.service;

import com.invoice.api.dto.CustomerDto;
import com.invoice.api.dto.ProductDto;
import com.invoice.api.dto.ResponseApi;
import com.invoice.api.entity.Cart;
import com.invoice.api.repository.CartRepository;
import com.invoice.configuration.client.CustomerClient;
import com.invoice.configuration.client.ProductClient;
import com.invoice.exception.ExceptionApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service public class CartServiceImp implements CartService
{

    @Resource
    CartRepository cartRepository;

    @Resource
    CustomerClient customerClient;

    @Resource
    ProductClient productClient;

    @Override
    public List<Cart> getCart(String rfc)
    {
        return cartRepository.findByRfcAndStatus(rfc, 1);
    }

    @Override
    public ResponseApi addToCart(Cart cart)
    {
        if (!validateCustomer(cart.getRfc()))
        {
            throw new ExceptionApi(HttpStatus.BAD_REQUEST, "customer does not exist");
        }

        Integer product_stock = getStockProduct(cart.getGtin());

        if (cart.getQuantity() > product_stock)
        {
            throw new ExceptionApi(HttpStatus.BAD_REQUEST, "invalid quantity");
        }

        Cart car = cartRepository.findCartbygtinAndRfc(cart.getGtin(), cart.getRfc());
        if (car != null)
        {
            if (car.getQuantity() + cart.getQuantity() > product_stock)
            {
                throw new ExceptionApi(HttpStatus.BAD_REQUEST, "invalid quantity");
            }
            else
            {
                product_stock = cart.getQuantity() + car.getQuantity();
                car.setQuantity(product_stock);
                cartRepository.updateCartQuantity(car.getQuantity(), car.getGtin(), car.getRfc());
                return new ResponseApi("quantity updated");
            }
        }
        else
        {
            cart.setStatus(1);
            cartRepository.save(cart);
        }
        return new ResponseApi("item added");
    }

    @Override
    public ResponseApi removeFromCart(Integer cart_id)
    {
        if (cartRepository.removeFromCart(cart_id) > 0)
        {
            return new ResponseApi("item removed");
        }
        else
        {
            throw new ExceptionApi(HttpStatus.BAD_REQUEST, "item cannot be removed");
        }
    }

    @Override
    public ResponseApi clearCart(String rfc)
    {
        if (cartRepository.clearCart(rfc) > 0)
        {
            return new ResponseApi("cart removed");
        }
        else
        {
            throw new ExceptionApi(HttpStatus.BAD_REQUEST, "cart cannot be removed");
        }
    }

    private boolean validateCustomer(String rfc)
    {
        try
        {
            ResponseEntity<CustomerDto> response = customerClient.getCustomer(rfc);
            return response.getStatusCode() == HttpStatus.OK;
        }
        catch (Exception e)
        {
            throw new ExceptionApi(HttpStatus.BAD_REQUEST, "unable to retrieve customer information");
        }
    }

    private boolean validateProduct(String gtin)
    {
        try
        {
            ResponseEntity<ProductDto> response = productClient.getProduct(gtin);
            return response.getStatusCode() == HttpStatus.OK;
        }
        catch (Exception e)
        {
            throw new ExceptionApi(HttpStatus.BAD_REQUEST, "unable to retrieve product information");
        }
    }

    private Integer getStockProduct(String gtin)
    {
        if (validateProduct(gtin))
        {
            ProductDto productDto = productClient.getProduct(gtin).getBody();
            return productDto.getStock();
        }
        else
        {
            throw new ExceptionApi(HttpStatus.BAD_REQUEST, "product doesn't exist");
        }
    }

}

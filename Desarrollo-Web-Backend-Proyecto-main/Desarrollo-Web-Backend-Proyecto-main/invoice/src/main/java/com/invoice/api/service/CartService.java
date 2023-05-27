package com.invoice.api.service;

import java.util.List;

import com.invoice.api.dto.ResponseApi;
import com.invoice.api.entity.Cart;

public interface CartService
{

    List<Cart> getCart(String rfc);

    ResponseApi addToCart(Cart cart);

    ResponseApi removeFromCart(Integer cart_id);

    ResponseApi clearCart(String rfc);

}

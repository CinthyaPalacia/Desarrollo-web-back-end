package com.invoice.api.controller;

import com.invoice.api.dto.ResponseApi;
import com.invoice.api.entity.Cart;
import com.invoice.api.service.CartService;
import com.invoice.exception.ExceptionApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController @RequestMapping("/cart") public class CartController
{

    @Resource
    CartService cartService;

    @GetMapping("/{rfc}")
    public ResponseEntity<List<Cart>> getCart(@PathVariable("rfc") String rfc)
    {
        return new ResponseEntity<>(cartService.getCart(rfc), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseApi> addToCart(@Valid @RequestBody Cart in, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            throw new ExceptionApi(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return new ResponseEntity<>(cartService.addToCart(in), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseApi> removeFromCart(@PathVariable("id") Integer id)
    {
        return new ResponseEntity<>(cartService.removeFromCart(id), HttpStatus.OK);
    }

    @DeleteMapping("/clear/{rfc}")
    public ResponseEntity<ResponseApi> deleteCart(@PathVariable("rfc") String rfc)
    {
        return new ResponseEntity<>(cartService.clearCart(rfc), HttpStatus.OK);
    }
}

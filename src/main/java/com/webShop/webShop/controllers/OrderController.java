package com.webShop.webShop.controllers;

import com.webShop.webShop.exceptions.EmptyOrderException;
import com.webShop.webShop.exceptions.OrderNotFoundException;
import com.webShop.webShop.exceptions.ProductNotFoundException;
import com.webShop.webShop.exceptions.UserNotFoundException;
import com.webShop.webShop.models.OrderDTO;
import com.webShop.webShop.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/confirmOrder", method = RequestMethod.POST)
    public ResponseEntity confirmOrder(@RequestBody(required = true) OrderDTO orderDTO) throws OrderNotFoundException {
        orderService.confirmOrder(orderDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/checkOrder", method = RequestMethod.GET)
    public ResponseEntity<OrderDTO> checkOrder(@RequestParam(name = "order_id") int order_id) throws OrderNotFoundException {
        OrderDTO orderDTO = orderService.checkOrder(order_id);
        return new ResponseEntity(orderDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteProduct", method = RequestMethod.POST)
    public ResponseEntity<OrderDTO> deleteProductFromOrder(@RequestParam(name = "product_id") int product_id) throws UserNotFoundException, ProductNotFoundException, EmptyOrderException {
        OrderDTO orderDTO = orderService.removeProductFromOrder(product_id);
        return new ResponseEntity(orderDTO, HttpStatus.OK);
    }
}

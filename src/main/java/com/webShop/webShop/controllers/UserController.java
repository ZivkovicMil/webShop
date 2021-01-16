package com.webShop.webShop.controllers;

import com.webShop.webShop.entities.Order;
import com.webShop.webShop.exceptions.UserNotFoundException;
import com.webShop.webShop.models.CommentDTO;
import com.webShop.webShop.models.OrderDTO;
import com.webShop.webShop.services.OrderService;
import com.webShop.webShop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/changeAddress/{newAddress}", method = RequestMethod.POST)
    public ResponseEntity changeAddress(@PathVariable("newAddress") String newAddress, HttpServletRequest request) {
        userService.changeAddress(newAddress, request);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/changePhoneNumber/{phoneNumber}", method = RequestMethod.POST)
    public ResponseEntity changePhoneNumber(@PathVariable("phoneNumber") String phoneNumber, HttpServletRequest request) {
        userService.changePhoneNumber(phoneNumber, request);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/allOrders", method = RequestMethod.GET)
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orderList = orderService.allOrders();
        return new ResponseEntity<List<OrderDTO>>(orderList, HttpStatus.OK);
    }

    @RequestMapping(value = "/allComments", method = RequestMethod.GET)
    public ResponseEntity<List<CommentDTO>> getAllComments() throws UserNotFoundException {
        List<CommentDTO> commentDTOList = userService.getAllComments();
        return new ResponseEntity<List<CommentDTO>>(commentDTOList, HttpStatus.OK);
    }
}

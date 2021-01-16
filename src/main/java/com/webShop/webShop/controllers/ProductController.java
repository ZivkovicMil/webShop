package com.webShop.webShop.controllers;

import com.webShop.webShop.exceptions.ProductNotFoundException;
import com.webShop.webShop.exceptions.ProductQuantityException;
import com.webShop.webShop.exceptions.UserNotFoundException;
import com.webShop.webShop.models.OrderDTO;
import com.webShop.webShop.models.ProductDTO;
import com.webShop.webShop.repository.ProductRepository;
import com.webShop.webShop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(value = "/lastProducts", method = RequestMethod.GET)
    public ResponseEntity<Page<ProductDTO>> lastProducts(@PageableDefault(page = 0, size = 3, sort = "created", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<ProductDTO> page = productService.getNewProducts(pageable);
        return new ResponseEntity(page, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public ResponseEntity addProduct(@RequestBody ProductDTO product) {
        productService.addNewProduct(product);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @RequestMapping(value = "/addToCart", method = RequestMethod.POST)
    public ResponseEntity<OrderDTO> addToCart(@RequestParam(name = "product_id") Integer product_id, @RequestParam(name = "quantity") Integer quantity) throws ProductQuantityException, UserNotFoundException, ProductNotFoundException {
        OrderDTO orderDTO = productService.addToCart(product_id, quantity);
        return new ResponseEntity(orderDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/allCategories", method = RequestMethod.GET)
    public ResponseEntity allCategories() {
        return new ResponseEntity(productService.allCategories(), HttpStatus.OK);
    }

    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public ResponseEntity getByCategory(@RequestParam(name = "category") String category, @PageableDefault(page = 0, size = 3, sort = "created", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity(productService.getByCategory(category, pageable), HttpStatus.OK);
    }

    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public ResponseEntity<ProductDTO> getProduct(@RequestParam(name = "product_id") int product_id) throws ProductNotFoundException {
        ProductDTO productDTO = productService.getProduct(product_id);
        return new ResponseEntity(productDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/addQuantity", method = RequestMethod.POST)
    public ResponseEntity addQuantity(@RequestParam(name = "product_id") Integer product_id, @RequestParam(name = "quantity") Integer quantity) throws ProductNotFoundException {
        productService.addQuantity(product_id, quantity);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public ResponseEntity addComment(@RequestParam(name = "product_id") Integer product_id, @RequestParam(name = "comment") String comment) throws ProductNotFoundException, UserNotFoundException {
        productService.addComment(product_id, comment);
        return new ResponseEntity(HttpStatus.OK);
    }
}

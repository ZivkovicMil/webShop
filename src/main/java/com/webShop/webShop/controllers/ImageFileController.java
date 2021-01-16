package com.webShop.webShop.controllers;

import com.webShop.webShop.exceptions.ImageNotExistException;
import com.webShop.webShop.exceptions.ProductNotFoundException;
import com.webShop.webShop.exceptions.UserNotFoundException;
import com.webShop.webShop.services.FileService;
import com.webShop.webShop.services.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import org.springframework.core.io.Resource;

@RestController
@RequestMapping(value = "/image")
public class ImageFileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private ProductImageService productImageService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/uploadImages", method = RequestMethod.POST)
    public ResponseEntity createMainDir(@RequestParam(name = "images") MultipartFile[] images, @RequestParam(name = "product_id") Integer product_id) throws IOException, UserNotFoundException, ProductNotFoundException {
        productImageService.saveImages(images, product_id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/getImage", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Resource> getImage(@RequestParam(name = "product_id") Integer product_id, @RequestParam(name = "image_number") Integer image_number) throws IOException, ProductNotFoundException, ImageNotExistException {
        Resource resource = productImageService.getImages(product_id, image_number);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }
}

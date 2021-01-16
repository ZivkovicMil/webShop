package com.webShop.webShop.services;

import com.webShop.webShop.exceptions.ImageNotExistException;
import com.webShop.webShop.exceptions.ProductNotFoundException;
import com.webShop.webShop.exceptions.UserNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductImageService {

    Resource getImages(Integer product_id, Integer image_number) throws IOException, ProductNotFoundException, ImageNotExistException;

    void saveImages(MultipartFile[] files, Integer product_id) throws IOException, UserNotFoundException, ProductNotFoundException;

}

package com.webShop.webShop.services;

import com.webShop.webShop.exceptions.ImageNotExistException;
import com.webShop.webShop.exceptions.ProductNotFoundException;
import com.webShop.webShop.exceptions.UserNotFoundException;
import com.webShop.webShop.repository.ProductImageRepository;
import com.webShop.webShop.repository.ProductRepository;
import com.webShop.webShop.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    private static final Logger log = LoggerFactory.getLogger(ProductImageServiceImpl.class);

    @Autowired
    private FileService fileService;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Resource getImages(Integer product_id, Integer image_number) throws IOException, ProductNotFoundException, ImageNotExistException {
        return fileService.getImages(product_id, image_number);
    }

    @Override
    public void saveImages(MultipartFile[] files, Integer product_id) throws IOException, UserNotFoundException, ProductNotFoundException {
        fileService.saveIntoTemp(files, product_id);
    }
}

package com.webShop.webShop.services;

import com.webShop.webShop.exceptions.ImageNotExistException;
import com.webShop.webShop.exceptions.ProductNotFoundException;
import com.webShop.webShop.exceptions.UserNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    String createProductDirectory(String product_id);

    String createTempDirectory();

    void saveIntoTemp(MultipartFile[] files, Integer product_id) throws IOException, UserNotFoundException, ProductNotFoundException;

    Resource getImages(Integer product_id, Integer image_number) throws ProductNotFoundException, IOException, ImageNotExistException;
}

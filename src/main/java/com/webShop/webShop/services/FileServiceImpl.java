package com.webShop.webShop.services;

import com.webShop.webShop.Messages;
import com.webShop.webShop.entities.Product;
import com.webShop.webShop.entities.ProductImage;
import com.webShop.webShop.entities.User;
import com.webShop.webShop.exceptions.ImageNotExistException;
import com.webShop.webShop.exceptions.ProductNotFoundException;
import com.webShop.webShop.exceptions.UserNotFoundException;
import com.webShop.webShop.repository.ProductImageRepository;
import com.webShop.webShop.repository.ProductRepository;
import com.webShop.webShop.repository.UserRepository;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    @Value("${file.directory}")
    private String directory;

    @Value("${file.image.database.name}")
    private String imageDatabaseName;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public String createProductDirectory(String product_id) {
        File dir = new File(directory + imageDatabaseName);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File productDir = new File(dir + File.separator + product_id);
        if (!productDir.exists()) {
            productDir.mkdir();
        }
        log.info("Directory for product " + product_id + " is created at path " + productDir.getPath());
        return productDir.getPath();
    }

    @Override
    public String createTempDirectory() {
        File dir = new File(directory + imageDatabaseName);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File tempDir = new File(dir + File.separator + "temp");
        if (!tempDir.exists()) {
            tempDir.mkdir();
        }
        log.info("Directory for temporary saving images is created at path " + tempDir.getPath());
        return tempDir.getPath();
    }

    @Override
    public void saveIntoTemp(MultipartFile[] files, Integer product_id) throws IOException, UserNotFoundException, ProductNotFoundException {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Product product = productRepository.findByIdd(product_id);
        try {
            if (user == null) throw new UserNotFoundException(Messages.USER_NOT_FOUND);
            if (product == null) throw new ProductNotFoundException(Messages.PRODUCT_NOT_FOUND);
        } catch (UserNotFoundException u) {
            throw new UserNotFoundException(Messages.USER_NOT_FOUND);
        } catch (ProductNotFoundException p) {
            throw new ProductNotFoundException(Messages.PRODUCT_NOT_FOUND);
        }
        Integer counter = 1;
        List<ProductImage> productImageList = productImageRepository.findAllProductImages(product_id);
        if (!productImageList.isEmpty() || productImageList != null) {
            counter = productImageList.size() + 1;
        }
        String hash = null;
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        for (MultipartFile m : files) {
            Path tempPath = Paths.get(createTempDirectory(), m.getOriginalFilename());
            Thumbnails.of(m.getInputStream()).size(200, 200).outputFormat("jpg").toFile(tempPath.toString());
            log.info("Image is croped and saved into temp folder");
            File originalFile = tempPath.toFile();
            hash = getFileChecksum(messageDigest, originalFile);
            log.info("Hash value for image {} is generated - {}",m.getOriginalFilename(),hash);
            Path productPath = Paths.get(createProductDirectory(product_id.toString()), hash + ".jpg");
            File file = productPath.toFile();
            if (!file.exists()) {
                com.google.common.io.Files.copy(originalFile, file);
                log.info("File from temp folder are copied into product directory");
                productImageRepository.save(new ProductImage(hash, productPath.toString(), counter, product, user));
            }
            originalFile.delete();
            log.info("File is deleted from temp folder");
        }
    }

    @Override
    public Resource getImages(Integer product_id, Integer image_number) throws ProductNotFoundException, IOException, ImageNotExistException {
        Product product = productRepository.findByIdd(product_id);
        if (product == null) throw new ProductNotFoundException(Messages.PRODUCT_NOT_FOUND);
        ProductImage productImage = productImageRepository.findImageByImageNumber(product_id, image_number);
        if (productImage == null) throw new ImageNotExistException(Messages.IMAGE_NOT_FOUND);
        File file = new File(productImage.getImage_location());
        if (checkImages(productImage.getImage_name(), file)) {
            InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
            return isr;
        } else {
            log.info("Image with path: " + productImage.getImage_location() + "is changed!");
        }
        return null;
    }

    private static String getFileChecksum(MessageDigest digest, File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);

        byte[] byteArray = new byte[1024];
        int bytesCount = 0;

        while ((bytesCount = fis.read(byteArray)) != -1) {
            digest.update(byteArray, 0, bytesCount);
        }

        fis.close();

        byte[] bytes = digest.digest();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    private boolean checkImages(String databaseHash, File file) throws IOException {
        String fileHash = null;
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        fileHash = getFileChecksum(messageDigest, file);
        if (databaseHash.equals(fileHash)) {
            return true;
        }
        return false;
    }
}

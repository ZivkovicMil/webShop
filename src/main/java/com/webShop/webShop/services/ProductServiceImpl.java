package com.webShop.webShop.services;

import com.webShop.webShop.Categories;
import com.webShop.webShop.Messages;
import com.webShop.webShop.entities.*;
import com.webShop.webShop.exceptions.ProductNotFoundException;
import com.webShop.webShop.exceptions.ProductQuantityException;
import com.webShop.webShop.exceptions.UserNotFoundException;
import com.webShop.webShop.models.OrderDTO;
import com.webShop.webShop.models.ProductDTO;
import com.webShop.webShop.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CategoryRepository categoryrepository;

    @Autowired
    private ProductQuantityRepository productQuantityRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Page<ProductDTO> getNewProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        Function<Product, ProductDTO> converter = product -> new ProductDTO.ProductDTOBuilder()
                .setProductId(product.getProduct_id())
                .setName(product.getName())
                .description(product.getDescription())
                .setPrice(product.getPrice())
                .setQuantity(product.getQuantity())
                .setCreatedDate(product.getCreated())
                .setCategory(product.getCategory().getCategory_name().getCategory())
                .build();
        Page<ProductDTO> productDTOPage = productPage.map(converter);
        log.info("Products with recent date is returned");
        return productDTOPage;
    }

    @Override
    public void addNewProduct(ProductDTO productdto) throws IllegalArgumentException {
        Category category;
        User user;
        Product product;
        try {
            category = categoryrepository.findByName(Categories.convert(productdto.getCategory()).name());
            if (category == null) {
                throw new IllegalArgumentException();
            }
            user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
            product = new Product(productdto.getName(), productdto.getDescription(), productdto.getPrice(), productdto.getQuantity(), user, category);
            productRepository.save(product);
            log.info("Product with name: " + product.getName() + " is saved!");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public OrderDTO addToCart(Integer product_id, Integer quantity) throws ProductQuantityException, UserNotFoundException, ProductNotFoundException {
        User user;
        Product product;
        Order order = null;
        try {
            user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
            if (user == null) throw new UserNotFoundException(Messages.USER_NOT_FOUND);
            product = productRepository.findByIdd(product_id);
            if (product == null) throw new ProductNotFoundException(Messages.PRODUCT_NOT_FOUND);
            Integer differenceQuantity = product.getQuantity() - quantity;
            if (differenceQuantity != 0 && differenceQuantity < 0) {
                throw new ProductQuantityException(Messages.PRODUCT_OUT_OF_STOCK);
            }
            if(quantity <= 0)throw new ProductQuantityException(Messages.QUANTITY_EXCEPTION);
        } catch (ProductQuantityException p) {
            if(p.getMessages().equals(Messages.PRODUCT_OUT_OF_STOCK)){
                throw new ProductQuantityException(Messages.PRODUCT_OUT_OF_STOCK);
            }
            throw new ProductQuantityException(Messages.QUANTITY_EXCEPTION);

        } catch (UserNotFoundException u) {
            throw new UserNotFoundException(Messages.USER_NOT_FOUND);
        } catch (ProductNotFoundException e) {
            throw new ProductNotFoundException(Messages.PRODUCT_NOT_FOUND);
        }
        order = findActiveOrder(user);
        orderRepository.save(order);
        log.info("Active order {} is found or created",order.getOrder_id());
        ProductQuantity productQuantity = null;
        if (!order.getProductQuantityList().isEmpty()) {
            for (ProductQuantity p : order.getProductQuantityList()) {
                if (p.getProduct().getProduct_id().equals(product_id)) {
                    p.setQuantity(p.getQuantity() + quantity);
                    productQuantity = p;
                    break;
                }
            }
        }
        if (productQuantity == null) {
            productQuantity = new ProductQuantity(new ProductQuantityKey(product_id, order.getOrder_id()), quantity);
            productQuantity.setProduct(product);
            productQuantity.setOrder(order);
            productQuantityRepository.save(productQuantity);
            order.getProductQuantityList().add(productQuantity);
        }
        product.setQuantity(product.getQuantity() - quantity);
        product.setProduct_sold(product.getProduct_sold() + quantity);
        order.setTotal_price(totalPrice(order.getProductQuantityList()));
        productRepository.save(product);
        orderRepository.save(order);
        userRepository.save(user);
        OrderDTO orderDTO = new OrderDTO.OrderDTOBuilder()
                .setOrderId(order.getOrder_id())
                .setUserDTO(user)
                .setListOfProducts(order.getProductQuantityList())
                .setTotalPrice(totalPrice(order.getProductQuantityList()))
                .build();
        return orderDTO;
    }

    @Override
    public void addQuantity(Integer product_id, Integer quantity) throws ProductNotFoundException {
        Product product = productRepository.findByIdd(product_id);

        try {
            if (product == null) {
                throw new ProductNotFoundException(Messages.PRODUCT_NOT_FOUND);
            }
        } catch (ProductNotFoundException p) {
            throw new ProductNotFoundException(Messages.PRODUCT_NOT_FOUND);
        }
        product.setQuantity(product.getQuantity() + quantity);
        productRepository.save(product);
        log.info("Product with id: " + product_id + " is added " + quantity);
    }

    @Override
    public List<String> allCategories() {
        return Arrays.asList(Categories.values()).stream().map(Categories::getCategory).collect(Collectors.toList());
    }

    @Override
    public Page<ProductDTO> getByCategory(String category, Pageable pageable) {
        Category category1 = categoryrepository.findByName(category);
        Page<Product> productList = productRepository.findByCategory(category1.getCategory_id(), pageable);
        if (productList != null) {
            Function<Product, ProductDTO> converter = product -> new ProductDTO.ProductDTOBuilder()
                    .setProductId(product.getProduct_id())
                    .setName(product.getName())
                    .description(product.getDescription())
                    .setPrice(product.getPrice())
                    .setQuantity(product.getQuantity())
                    .setCreatedDate(product.getCreated())
                    .setCategory(product.getCategory().getCategory_name().getCategory())
                    .build();
            Page<ProductDTO> productDTOPage = productList.map(converter);
            log.info("Products with category {} is generated and returned",category);
            return productDTOPage;
        }
        return null;
    }

    @Override
    public ProductDTO getProduct(int product_id) throws ProductNotFoundException {
        Product product = productRepository.findByIdd(product_id);
        ProductDTO productDTO;
        try {
            if (product == null) {
                throw new ProductNotFoundException(Messages.PRODUCT_NOT_FOUND);
            }
        } catch (ProductNotFoundException e) {
            throw new ProductNotFoundException(Messages.PRODUCT_NOT_FOUND);
        }
        ProductDTO.ProductDTOBuilder productDTOBuilder = new ProductDTO.ProductDTOBuilder();
        productDTOBuilder
                .setProductId(product.getProduct_id())
                .setName(product.getName())
                .setPrice(product.getPrice())
                .description(product.getDescription())
                .setCreatedDate(product.getCreated())
                .setQuantity(product.getQuantity())
                .setCategory(product.getCategory().getCategory_name().getCategory())
                .build();
        productDTO = new ProductDTO(productDTOBuilder);
        log.info("Product {} is returned",product_id);
        return productDTO;
    }

    @Override
    public void addComment(Integer product_id, String comment) throws ProductNotFoundException, UserNotFoundException {
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
        Comment comment1 = new Comment(comment, product, user);
        commentRepository.save(comment1);
        log.info("Comment from User {} is added for product {}",user.getEmail(),product.getName());
    }

    private Order findActiveOrder(User user) {
        if (user.getOrderList().isEmpty() || user.getOrderList() == null) {
            return new Order(user, new ArrayList<>());
        }
        for (Order o : user.getOrderList()) {
            if (!o.isConfirmed()) {
                return o;
            }
        }
        return new Order(user, new ArrayList<>());
    }

    private Double totalPrice(List<ProductQuantity> productList) {
        Double total = Double.valueOf(0);
        for (ProductQuantity p : productList) {
            total = total + p.getProduct().getPrice() * p.getQuantity();
        }
        return total;
    }
}

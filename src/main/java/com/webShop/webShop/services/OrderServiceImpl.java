package com.webShop.webShop.services;

import com.webShop.webShop.Messages;
import com.webShop.webShop.entities.Order;
import com.webShop.webShop.entities.Product;
import com.webShop.webShop.entities.ProductQuantity;
import com.webShop.webShop.entities.User;
import com.webShop.webShop.exceptions.EmptyOrderException;
import com.webShop.webShop.exceptions.OrderNotFoundException;
import com.webShop.webShop.exceptions.ProductNotFoundException;
import com.webShop.webShop.exceptions.UserNotFoundException;
import com.webShop.webShop.models.OrderDTO;
import com.webShop.webShop.repository.OrderRepository;
import com.webShop.webShop.repository.ProductQuantityRepository;
import com.webShop.webShop.repository.ProductRepository;
import com.webShop.webShop.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductQuantityRepository productQuantityRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public void confirmOrder(OrderDTO orderDTO) throws OrderNotFoundException {
        Order order = orderRepository.findByIdd(orderDTO.getOrder_id());
        try {
            if (order == null) {
                throw new OrderNotFoundException(Messages.ORDER_NOT_FOUND);
            }
        } catch (OrderNotFoundException e) {
            throw new OrderNotFoundException(Messages.ORDER_NOT_FOUND);
        }
        order.setTotal_price(orderDTO.getTotal_price());
        order.setOrder_confirmed(LocalDateTime.now());
        order.setConfirmed(true);
        log.info("Order with ID {} is confirmed", order.getOrder_id());
        orderRepository.save(order);
        emailService.sendOrderDetailsEmail(order.getUser().getEmail(), orderDTO);
    }

    @Override
    public OrderDTO checkOrder(int id) throws OrderNotFoundException {
        Order order = orderRepository.findByIdd(id);
        if (order == null) {
            throw new OrderNotFoundException(Messages.ORDER_NOT_FOUND);
        }
        OrderDTO orderDTO = new OrderDTO.OrderDTOBuilder()
                .setOrderId(order.getOrder_id())
                .setCreatedTime(order.getOrder_created())
                .setUserDTO(order.getUser())
                .setListOfProducts(order.getProductQuantityList())
                .setTotalPrice(order.getTotal_price())
                .build();
        return orderDTO;
    }

    @Override
    public OrderDTO removeProductFromOrder(int product_id) throws UserNotFoundException, ProductNotFoundException, EmptyOrderException {
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
        List<Order> orders = user.getOrderList().stream().filter(o -> !o.isConfirmed()).collect(Collectors.toList());
        if (orders.get(0).getProductQuantityList().isEmpty() || orders.get(0).getProductQuantityList() == null) {
            log.info("Order is empty, so product can't be deleted");
            throw new EmptyOrderException(Messages.EMPTY_ORDER);
        }
        ProductQuantity productQuantity = null;
        for (ProductQuantity p : orders.get(0).getProductQuantityList()) {
            if (p.getProduct().getProduct_id().equals(product_id)) {
                product.setQuantity(product.getQuantity() + p.getQuantity());
                log.info("Quantity for product {} is added from deleting product from order {}",product_id,p.getOrder().getOrder_id());
                product.setProduct_sold(product.getProduct_sold() - p.getQuantity());
                productQuantity = p;
                break;
            }
        }
        if (productQuantity != null) {
            product.getProductQuantityList().remove(productQuantity);
            orders.get(0).getProductQuantityList().remove(productQuantity);
        }
        productQuantityRepository.deleteByOrder(productQuantity.getOrder().getOrder_id(), productQuantity.getProduct().getProduct_id());
        log.info("Product with ID {} is deleted from order {}",product_id,orders.get(0).getOrder_id());
        productRepository.save(product);
        userRepository.save(user);
        return buildOrderDTO(user.getEmail());
    }

    @Override
    public List<OrderDTO> allOrders() {
        List<Order> orderList = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getOrderList();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (Order o : orderList) {
            orderDTOList.add(new OrderDTO.OrderDTOBuilder()
                    .setOrderId(o.getOrder_id())
                    .setCreatedTime(o.getOrder_created())
                    .setUserDTO(o.getUser())
                    .setListOfProducts(o.getProductQuantityList())
                    .setTotalPrice(o.getTotal_price())
                    .build());
        }
        log.info("All orders for user {} is returned",SecurityContextHolder.getContext().getAuthentication().getName());
        return orderDTOList;
    }

    private OrderDTO buildOrderDTO(String user_email) {
        User user = userRepository.findByEmail(user_email);
        if (user.getOrderList().isEmpty() || user.getOrderList() == null) {
            return null;
        }
        for (Order o : user.getOrderList()) {
            if (!o.isConfirmed()) {
                OrderDTO orderDTO = new OrderDTO.OrderDTOBuilder()
                        .setOrderId(o.getOrder_id())
                        .setCreatedTime(o.getOrder_created())
                        .setUserDTO(o.getUser())
                        .setListOfProducts(o.getProductQuantityList())
                        .setTotalPrice(totalPrice(o.getProductQuantityList()))
                        .build();
                return orderDTO;
            }
        }
        return null;
    }

    private Double totalPrice(List<ProductQuantity> productList) {
        Double total = Double.valueOf(0);
        for (ProductQuantity p : productList) {
            total = total + p.getProduct().getPrice() * p.getQuantity();
        }
        return total;
    }
}

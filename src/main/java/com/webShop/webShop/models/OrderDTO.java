package com.webShop.webShop.models;

import com.webShop.webShop.entities.ProductQuantity;
import com.webShop.webShop.entities.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO {

    private int order_id;
    private LocalDateTime order_created;
    private UserDTO userDTO;
    private List<ProductDTO> productList;
    private double total_price;

    public OrderDTO() {
    }

    public OrderDTO(OrderDTOBuilder orderDTOBuilder) {
        this.order_id = orderDTOBuilder.order_id;
        this.order_created = orderDTOBuilder.order_created;
        this.userDTO = orderDTOBuilder.userDTO;
        this.productList = orderDTOBuilder.productList;
        this.total_price = orderDTOBuilder.total_price;
    }

    public int getOrder_id() {
        return order_id;
    }

    public LocalDateTime getOrder_created() {
        return order_created;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public List<ProductDTO> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductDTO> productList) {
        this.productList = productList;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public static class OrderDTOBuilder {

        private int order_id;
        private LocalDateTime order_created;
        private UserDTO userDTO;
        private List<ProductDTO> productList;
        private double total_price;

        public OrderDTOBuilder() {
        }

        public OrderDTOBuilder setOrderId(int order_id) {
            this.order_id = order_id;
            return this;
        }

        public OrderDTOBuilder setCreatedTime(LocalDateTime order_created) {
            this.order_created = order_created;
            return this;
        }

        public OrderDTOBuilder setUserDTO(User user) {
            this.userDTO = new UserDTO.UserDTOBuilder()
                    .setEmail(user.getEmail())
                    .setFirstName(user.getFirst_name())
                    .setLastName(user.getLast_name())
                    .setAddress(user.getAddress())
                    .setPhoneNUmber(user.getPhone_number())
                    .isEnabled(user.isIs_enabled())
                    .build();
            return this;
        }

        public OrderDTOBuilder setListOfProducts(List<ProductQuantity> productList) {
            List<ProductDTO> productDTOList = new ArrayList<>();
            for (ProductQuantity p : productList) {
                productDTOList.add(new ProductDTO.ProductDTOBuilder()
                        .setProductId(p.getProductQuantityKey().getProduct_id())
                        .setName(p.getProduct().getName())
                        .setPrice(p.getProduct().getPrice())
                        .description(p.getProduct().getDescription())
                        .setCreatedDate(p.getProduct().getCreated())
                        .setCategory(p.getProduct().getCategory().getCategory_name().getCategory())
                        .setQuantity(p.getQuantity())
                        .build());
            }
            this.productList = productDTOList;
            return this;
        }

        public OrderDTOBuilder setTotalPrice(Double total_price) {
            this.total_price = total_price;
            return this;
        }

        public OrderDTO build() {
            OrderDTO orderDTO = new OrderDTO(this);
            return orderDTO;
        }
    }
}

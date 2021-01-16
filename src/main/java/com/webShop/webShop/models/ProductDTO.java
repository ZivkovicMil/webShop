package com.webShop.webShop.models;

import java.time.LocalDateTime;

public class ProductDTO {

    private Integer product_id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private LocalDateTime created;
    private String category;


    public ProductDTO() {
    }

    public ProductDTO(ProductDTOBuilder productDTOBuilder) {
        this.product_id = productDTOBuilder.product_id;
        this.name = productDTOBuilder.name;
        this.description = productDTOBuilder.description;
        this.price = productDTOBuilder.price;
        this.quantity = productDTOBuilder.quantity;
        this.created = productDTOBuilder.created;
        this.category = productDTOBuilder.category;
    }

    public ProductDTO(String name, String description, Double price, Integer quantity, LocalDateTime created, String user_email, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.created = created;
        this.category = category;
    }

    public int getProductId() {
        return product_id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public String getCategory() {
        return category;
    }

    public static class ProductDTOBuilder {

        private Integer product_id;
        private String name;
        private String description;
        private Double price;
        private Integer quantity;
        private LocalDateTime created;
        private String category;

        public ProductDTOBuilder() {
        }

        public ProductDTOBuilder setProductId(Integer product_id) {
            this.product_id = product_id;
            return this;
        }

        public ProductDTOBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ProductDTOBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProductDTOBuilder setPrice(Double price) {
            this.price = price;
            return this;
        }

        public ProductDTOBuilder setQuantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public ProductDTOBuilder setCreatedDate(LocalDateTime created) {
            this.created = created;
            return this;
        }

        public ProductDTOBuilder setCategory(String category) {
            this.category = category;
            return this;
        }

        public ProductDTO build() {
            ProductDTO productDTO = new ProductDTO(this);
            return productDTO;
        }
    }
}

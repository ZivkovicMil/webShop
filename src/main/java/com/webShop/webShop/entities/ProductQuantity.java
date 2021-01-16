package com.webShop.webShop.entities;

import javax.persistence.*;

@Entity
@Table(name = "product_quantity")
public class ProductQuantity {

    @EmbeddedId
    public ProductQuantityKey productQuantityKey;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @MapsId("product_id")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @MapsId("order_id")
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "quantity")
    private Integer quantity;

    public ProductQuantity() {
    }

    public ProductQuantity(ProductQuantityKey productQuantityKey, Integer quantity) {
        this.productQuantityKey = productQuantityKey;
        this.quantity = quantity;
    }

    public ProductQuantity(ProductQuantityKey productQuantityKey, Product product, Order order, Integer quantity) {
        this.productQuantityKey = productQuantityKey;
        this.product = product;
        this.order = order;
        this.quantity = quantity;
    }

    public ProductQuantityKey getProductQuantityKey() {
        return productQuantityKey;
    }

    public void setProductQuantityKey(ProductQuantityKey productQuantityKey) {
        this.productQuantityKey = productQuantityKey;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

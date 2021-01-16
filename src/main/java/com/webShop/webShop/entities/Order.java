package com.webShop.webShop.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "XOrder")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Integer order_id;

    @Column(name = "order_created")
    private LocalDateTime order_created;

    @Column(name = "order_confirmed")
    private LocalDateTime order_confirmed;

    @Column(name = "total_price")
    private Double total_price;

    @Column(name = "confirmed")
    private boolean confirmed;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order")
    private List<ProductQuantity> productQuantityList;

    public Order() {
        this.order_created = LocalDateTime.now();
        this.confirmed = false;
    }

    public Order(User user, List<ProductQuantity> productQuantityList) {
        this.order_created = LocalDateTime.now();
        this.user = user;
        this.productQuantityList = productQuantityList;
        this.order_created = LocalDateTime.now();
        this.confirmed = false;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public LocalDateTime getOrder_created() {
        return order_created;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public void setOrder_created(LocalDateTime order_created) {
        this.order_created = order_created;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getOrder_confirmed() {
        return order_confirmed;
    }

    public List<ProductQuantity> getProductQuantityList() {
        return productQuantityList;
    }

    public void setProductQuantityList(List<ProductQuantity> productQuantityList) {
        this.productQuantityList = productQuantityList;
    }

    public void setOrder_confirmed(LocalDateTime order_confirmed) {
        this.order_confirmed = order_confirmed;
    }

    public Double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Double total_price) {
        this.total_price = total_price;
    }

    private Double calculatePrice(List<Product> productList) {
        Double totalPrice = Double.valueOf(0);
        for (Product p : productList) {
            totalPrice = total_price + (p.getPrice() * p.getQuantity());
        }
        return totalPrice;
    }
}

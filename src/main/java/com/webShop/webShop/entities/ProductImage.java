package com.webShop.webShop.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_image")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_image_id")
    private Integer product_image_id;

    @Column(name = "image_name")
    private String image_name;

    @Column(name = "image_location")
    private String image_location;

    @Column(name = "image_added")
    private LocalDateTime image_added;

    @Column(name = "image_number")
    private Integer image_number;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public ProductImage() {
    }

    public ProductImage(String image_name, String image_location,Integer image_number, Product product, User user) {
        this.image_name = image_name;
        this.image_location = image_location;
        this.image_number = image_number;
        this.image_added = LocalDateTime.now();
        this.product = product;
        this.user = user;
    }

    public Integer getProduct_image_id() {
        return product_image_id;
    }

    public void setProduct_image_id(Integer product_image_id) {
        this.product_image_id = product_image_id;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getImage_location() {
        return image_location;
    }

    public void setImage_location(String image_location) {
        this.image_location = image_location;
    }

    public LocalDateTime getImage_added() {
        return image_added;
    }

    public void setImage_added(LocalDateTime image_added) {
        this.image_added = image_added;
    }

    public Integer getImage_number() {
        return image_number;
    }

    public void setImage_number(Integer image_number) {
        this.image_number = image_number;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

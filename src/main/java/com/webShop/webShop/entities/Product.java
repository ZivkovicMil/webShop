package com.webShop.webShop.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Integer product_id;

    @Column(name = "product_name")
    private String name;

    @Column(name = "product_description")
    private String description;

    @Column(name = "product_price")
    private Double price;

    @Column(name = "product_quantity")
    private Integer quantity;

    @Column(name = "product_sold")
    private Integer product_sold;

    @Column(name = "created")
    private LocalDateTime created;

    @OneToMany(mappedBy = "product")
    private List<ProductQuantity> productQuantityList;

    @OneToMany(mappedBy = "product")
    private List<Comment> commentList;

    @OneToMany(mappedBy = "product")
    private List<ProductImage> productImageList;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public Product() {
    }

    public Product(String name, String description, Double price, Integer quantity, User user, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.created = LocalDateTime.now();
        this.user = user;
        this.category = category;
        this.product_sold = 0;
    }

    public Product(Product product) {
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.created = product.getCreated();
        this.user = product.user;
        this.category = product.getCategory();
        this.product_sold = product.getProduct_sold();
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getProduct_sold() {
        return product_sold;
    }

    public void setProduct_sold(Integer product_sold) {
        this.product_sold = product_sold;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public List<ProductQuantity> getProductQuantityList() {
        return productQuantityList;
    }

    public void setProductQuantityList(List<ProductQuantity> productQuantityList) {
        this.productQuantityList = productQuantityList;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public List<ProductImage> getProductImageList() {
        return productImageList;
    }

    public void setProductImageList(List<ProductImage> productImageList) {
        this.productImageList = productImageList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

package com.webShop.webShop.entities;

import com.webShop.webShop.Categories;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private Integer category_id;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_name")
    private Categories category_name;

    @OneToMany(mappedBy = "category")
    private List<Product> productList;

    public Category() {
    }

    public Category(Categories category_name) {
        this.category_name = category_name;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public Categories getCategory_name() {
        return category_name;
    }

    public void setCategory_name(Categories category_name) {
        this.category_name = category_name;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}

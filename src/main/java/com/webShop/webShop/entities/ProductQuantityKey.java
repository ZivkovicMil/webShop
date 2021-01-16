package com.webShop.webShop.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductQuantityKey implements Serializable {

    @Column(name = "product_id")
    private Integer product_id;

    @Column(name = "order_id")
    private Integer order_id;

    public ProductQuantityKey() {
    }

    public ProductQuantityKey(Integer product_id, Integer order_id) {
        this.product_id = product_id;
        this.order_id = order_id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductQuantityKey that = (ProductQuantityKey) o;
        return Objects.equals(product_id, that.product_id) && Objects.equals(order_id, that.order_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product_id, order_id);
    }
}

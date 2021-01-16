package com.webShop.webShop.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "token_id")
    @JsonIgnore
    private Integer id;

    @Column(name = "token")
    private String token;

    @Column(name = "token_created")
    private LocalDateTime localDateTime;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public ConfirmationToken() {
    }

    public ConfirmationToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.localDateTime = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}

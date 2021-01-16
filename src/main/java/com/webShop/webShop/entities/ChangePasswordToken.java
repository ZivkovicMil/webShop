package com.webShop.webShop.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "change_password_token")
public class ChangePasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "change_password_token_id")
    private Integer id;

    @Column(name = "token")
    private String token;

    @Column(name = "created_time")
    private LocalDateTime localDateTime;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public ChangePasswordToken() {
    }

    public ChangePasswordToken(String token) {
        this.token = token;
    }

    public ChangePasswordToken(String token, User user) {
        this.token = token;
        this.user = user;
        localDateTime = LocalDateTime.now();
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

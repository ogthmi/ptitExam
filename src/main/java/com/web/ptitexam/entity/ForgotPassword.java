package com.web.ptitexam.entity;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "forgot_passwords")
public class ForgotPassword {
    @Id
    @Column(updatable = false, nullable = false)
    private String id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String token;

    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT+7")
    private Instant createdAt;

    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT+7")
    private Instant expiredAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Instant expiredAt) {
        this.expiredAt = expiredAt;
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = Instant.now();
        // thời gian hết hạn token là 3 phút
        this.expiredAt = this.createdAt.plusSeconds(60 * 3);
    }

}

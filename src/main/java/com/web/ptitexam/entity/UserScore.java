package com.web.ptitexam.entity;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class UserScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private int score;

    // Constructors
    public UserScore() {}

    public UserScore(Long userId, int score) {
        this.userId = userId;
        this.score = score;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    // toString method
    @Override
    public String toString() {
        return "UserScore{" +
                "id=" + id +
                ", userId=" + userId +
                ", score=" + score +
                '}';
    }

    // hashCode and equals methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserScore that = (UserScore) o;
        return score == that.score &&
                Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, score);
    }
}


package com.ratelimiter.Project.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Column(unique = true)
    private String apiKey;

    private int capacity;      // max tokens
    private int refillRate;    // tokens per minute
}

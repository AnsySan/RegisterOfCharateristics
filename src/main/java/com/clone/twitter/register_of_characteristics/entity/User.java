package com.clone.twitter.register_of_characteristics.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", length = 40, unique = true, nullable = false)
    private String username;

    @Column(name = "password", length = 80, nullable = false)
    private String password;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Column(name = "surname", length = 20, nullable = false)
    private String surname;

    @Column(name = "parentName", length = 20, nullable = false)
    private String parentName;

    private LocalDateTime creationDate;

    private LocalDateTime lastEditDate;
}
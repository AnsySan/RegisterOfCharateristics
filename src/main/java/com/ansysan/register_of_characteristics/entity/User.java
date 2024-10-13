package com.ansysan.register_of_characteristics.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User  {

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

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role roles;

}
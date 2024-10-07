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
@Table(name = "comments")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "text",length = 300, nullable = false)
    private String text;

    @Column(name = "creationDate", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "lastEditDate", nullable = false)
    private LocalDateTime lastEditDate;

    @ManyToOne
    @JoinColumn(name = "insertedById", nullable = false)
    private User insertedById;

    @ManyToOne
    @JoinColumn(name = "idNews", nullable = false)
    private News idNews;
}

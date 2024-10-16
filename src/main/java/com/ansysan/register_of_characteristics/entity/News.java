package com.ansysan.register_of_characteristics.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", length = 150, nullable = false)
    private String title;

    @Column(name = "text", length = 2000, nullable = false)
    private String text;

    @Column(name = "creationDate")
    private LocalDateTime creationDate;

    @Column(name = "lastEditDate")
    private LocalDateTime lastEditDate;

    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL)
    private List<Comment> comment;

    @ManyToOne
    @JoinColumn(name = "insertedById", nullable = false)
    private User inserterById;

    @ManyToOne
    @JoinColumn(name = "updatedById", nullable = false)
    private User updatedById;

    public String toStringForFind(){
        return id + " " + " " + title + " " +
                text + " " + creationDate + " " + lastEditDate;
    }
}

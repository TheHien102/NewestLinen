package com.example.newestlinen.storage.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = TablePrefix.PREFIX_TABLE+"news")
public class News extends Auditable<String>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;

    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;
    private String avatar;
    private String banner;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id")
    private Category category;
    private Integer pinTop = 0; // 0 unpin, 1 pin

    private Integer kind; // 0 internal, ctv
}
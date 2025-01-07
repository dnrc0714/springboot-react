package com.ccbb.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "HEALTH_POST")
public class Post {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String postType;
    private String title;
    private String content;

    private Long createUserId;
    private Date createDate;
    private Long updateUserId;
    private Date updateDate;
}

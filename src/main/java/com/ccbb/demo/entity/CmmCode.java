package com.ccbb.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "cmm_code")
public class CmmCode {
    @Id
    @Column(name = "cmm_code")
    private String code;

    @Column(name = "cmm_codename")
    private String codeName;

    @Column(name = "creator_id")
    private Long creatorId;

    @Column(name = "updator_id")
    private Long updatorId;

    @Column(name = "sys_code")
    private String sysCode;

    @Column(name = "seq")
    private Integer seq;
}

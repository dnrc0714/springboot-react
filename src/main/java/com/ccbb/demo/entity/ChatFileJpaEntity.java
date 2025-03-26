package com.ccbb.demo.entity;

import com.ccbb.demo.chat.adapter.out.persistence.ChatMessageJpaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_file")
public class ChatFileJpaEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private ChatMessageJpaEntity chatMessage;

    @Id
    @Column(name = "seq", nullable = false)
    private Integer seq;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(name = "file_type", nullable = false)
    private String fileType;

    @Column(name = "s3_url", nullable = false)
    private String s3Url;

    @Column(name = "uploaded_by", nullable = false)
    private Long uploadedBy;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}

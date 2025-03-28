package com.ccbb.demo.entity;

import com.ccbb.demo.chat.adapter.out.persistence.ChatMessageJpaEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ChatFileJpaEntity.ChatFileId.class)
@Table(name = "chat_file")
public class ChatFileJpaEntity {

    @Id
    @Column(name = "chat_message_id", nullable = false)
    private Long id;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chat_message_id", nullable = false)
    private ChatMessageJpaEntity chatMessage;

    // 복합 키 클래스
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class ChatFileId implements Serializable {
        private Long id;
        private Integer seq;
    }
}
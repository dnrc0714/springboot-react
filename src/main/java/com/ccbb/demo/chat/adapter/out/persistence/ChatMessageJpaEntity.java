package com.ccbb.demo.chat.adapter.out.persistence;

import com.ccbb.demo.entity.UserJpaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Getter
@Table(name = "chat_messages")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Id;

    @Column(name = "content")
    private String content;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private UserJpaEntity creator;

    @Column(name = "creator_id")
    private Long creatorId;

    @Column(name = "createdAt")
    private OffsetDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false) // FK 설정
    private ChatRoomJpaEntity chatRoom;

    public ChatMessageJpaEntity(String content, ChatRoomJpaEntity chatRoom) {
        this.content = content;
        this.chatRoom = chatRoom;
    }
}

package com.ccbb.demo.chat.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "chat_rooms")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "chat_room_id")
    private Long chatRoomId;

    //    @BatchSize(size = 10)
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final List<ChatMessageJpaEntity> chatMessageList = new ArrayList<>();

    public void createMessage(ChatMessageJpaEntity chatMessageJpaEntity){
        chatMessageList.add(chatMessageJpaEntity);
    }

}

package com.tgd.trip.chatting.domain;

import com.tgd.trip.user.domain.ChatParticipant;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;
    private String content;
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "chat_participant_id")
    private ChatParticipant chatParticipant;
}

package com.tgd.trip.chatting.domain;

import com.tgd.trip.user.domain.ChatParticipant;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;
    @NotNull
    @Column(columnDefinition = "TEXT")
    private String content;
    private LocalDate createdAt = LocalDate.now();
    @ManyToOne
    @JoinColumn(name = "chat_participant_id")
    private ChatParticipant chatParticipant;
}

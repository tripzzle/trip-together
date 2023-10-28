package com.tgd.trip.user.domain;

import com.tgd.trip.chatting.domain.ChatRoom;
import com.tgd.trip.global.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatParticipant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatParticipantId;
    private Boolean isAdmin;
    @ManyToOne
    @JoinColumn(name = "participant_id")
    private User participant;
    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;
}

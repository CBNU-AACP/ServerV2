package aacp.server.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ChattingRoom {

    @Id @GeneratedValue
    @Column(name = "chatting_room_id")
    private Long id;

    private String name;

    @OneToOne(mappedBy = "chattingRoom", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Course course;

    @OneToMany(mappedBy = "chattingRoom", cascade = CascadeType.ALL)
    private List<ChattingMessage> chattingMessages = new ArrayList<>();
}

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
public class Course {

    @Id @GeneratedValue
    @Column(name = "course_id")
    private Long id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "course")
    private List<CourseAdmin> courseAdminList = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    private List<CourseParticipate> courseParticipates = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "chatting_room_id")
    private ChattingRoom chattingRoom;
}

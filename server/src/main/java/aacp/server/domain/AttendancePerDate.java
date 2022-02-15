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
public class AttendancePerDate extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "attendance_per_date_id")
    private Long id;

    private Boolean classStatus;

    @OneToMany(mappedBy = "attendancePerDate",cascade = CascadeType.ALL)
    List<Attendee> attendees = new ArrayList<>();

}

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
public class Attendee extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "attendee_id")
    private Long id;

    private Boolean isChecked;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "attendance_per_date_id")
    private AttendancePerDate attendancePerDate;
}

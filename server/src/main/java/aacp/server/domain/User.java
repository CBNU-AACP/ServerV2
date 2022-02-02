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
@Table(name = "USERS")
public class User extends Member {

    private String identifier;

    private String studentId;

    private String schoolCode;

    private String phoneNumber;

    private String macAddress;

    @OneToMany(mappedBy = "user")
    private List<CourseAdmin> courseAdmins = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<CourseParticipate> courseParticipates = new ArrayList<>();

}

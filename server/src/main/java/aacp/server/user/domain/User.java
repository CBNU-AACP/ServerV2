package aacp.server.user.domain;

import aacp.server.domain.CourseAdmin;
import aacp.server.domain.CourseParticipate;
import aacp.server.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "USERS")
public class User extends Member {

    private String identifier;

    private String name;

    private String email;

    private String studentId;

    private String schoolCode;

    private String phoneNumber;

    private String macAddress;

    @Builder
    public User(String identifier,String name, String password, String email, String studentId, String schoolCode, String phoneNumber) {
        this.identifier = identifier;
        this.name = name;
        this.password = encryptPassword(password);
        this.email = email;
        this.studentId = studentId;
        this.schoolCode = schoolCode;
        this.phoneNumber = phoneNumber;
    }

    public void changeMacAddress(String macAddress){ this.macAddress = macAddress; }
    private String encryptPassword(String rawPassword) {
        return new BCryptPasswordEncoder().encode(rawPassword);
    }
    @OneToMany(mappedBy = "user")
    private List<CourseAdmin> courseAdmins = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<CourseParticipate> courseParticipates = new ArrayList<>();

}

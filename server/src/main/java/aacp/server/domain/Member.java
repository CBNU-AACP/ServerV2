package aacp.server.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
@Entity
public abstract class Member extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String identifier;

    private String password;

    private String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "messenger_id")
    private Messenger messenger;
}

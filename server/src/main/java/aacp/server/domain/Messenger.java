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
public class Messenger extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "messenger_id")
    private Long id;

    @OneToOne(mappedBy = "messenger", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(mappedBy = "messenger")
    List<DirectMessage> directMessages = new ArrayList<>();
}

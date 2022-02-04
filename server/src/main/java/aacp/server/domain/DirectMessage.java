package aacp.server.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class DirectMessage extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "direct_message_id")
    private Long id;

    private String sender;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "messenger_id")
    private Messenger messenger;

}

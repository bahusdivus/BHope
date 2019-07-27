package ru.bahusdivus.bhope.entities;

import lombok.*;
import ru.bahusdivus.bhope.dto.UserDto;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@ToString(exclude = {"user", "deleted", "date", "comments"})
@Entity
@Table(name = "T_POSTS")
@SequenceGenerator(name = "postSeqGenerator",
        sequenceName = "POST_SEQ", allocationSize = 10)
public class Post {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "postSeqGenerator")
    private Long id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(name = "DATE", nullable = false)
    private LocalDateTime date;

    @Column(name = "DELETED", nullable = false)
    private boolean deleted;

    @Column(name = "LIKE_COUNT")
    private int likeCount;

    @OneToMany(
            mappedBy = "post",
            fetch = FetchType.EAGER
    )
    private Set<Comment> comments = new LinkedHashSet<>();

    public Post(UserDto userDto) {
        this.user = new User(userDto.getId());
        this.date = LocalDateTime.now();
    }

    public Post(Long id) {
        this.id = id;
    }
}

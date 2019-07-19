package ru.bahusdivus.bhope.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@ToString(exclude = {"user", "deleted", "date", "comments"})
@Entity
@Table(name = "POSTS")
public class Post {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POST_SEQ")
    @SequenceGenerator(name = "POST_SEQ",
            sequenceName = "POST_SEQ", allocationSize = 10)
    private long id;

    @Column(name = "HEADER", nullable = false)
    private String header;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(name = "DATE", nullable = false)
    private LocalDateTime date;

    @Column(name = "DELETED", nullable = false)
    private boolean deleted;

    @OneToMany(
            mappedBy = "post",
            fetch = FetchType.EAGER
    )
    private Set<Comment> comments = new LinkedHashSet<>();

    public Post(long id, String header, String content) {
        this.id = id;
        this.header = header;
        this.content = content;
    }

    public Post(long userId) {
        this.user = new User(userId);
        this.date = LocalDateTime.now();
    }
}

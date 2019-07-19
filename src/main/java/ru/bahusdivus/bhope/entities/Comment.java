package ru.bahusdivus.bhope.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"user", "deleted", "date", "parent", "children", "post"})
@Entity
@Table(name = "COMMENTS")
public class Comment {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commentGenerator")
    @SequenceGenerator(name = "commentGenerator",
            sequenceName = "COMMENT_SEQ", allocationSize = 10)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PARENT_ID")
    private Comment parent;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "POST_ID", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "DATE", nullable = false)
    private LocalDateTime date;

    @Column(name = "DELETED", nullable = false)
    private boolean deleted;

    @OneToMany(
            mappedBy = "parent",
            fetch = FetchType.EAGER
    )
    private Set<Comment> children = new LinkedHashSet<>();

    public Comment(Long user, Long post, Long parent) {
        this.user = new User(user);
        this.post = new Post(post);
        if (parent != 0) {
            this.parent = new Comment(parent);
        }
        this.date = LocalDateTime.now();
    }

    public Comment(Long id) {
        this.id = id;
    }
}

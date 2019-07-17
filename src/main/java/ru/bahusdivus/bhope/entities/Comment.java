package ru.bahusdivus.bhope.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"user", "deleted", "date", "parent"})
@Entity
@Table(name = "COMMENTS")
public class Comment {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMENTS_SEQ")
    @SequenceGenerator(name = "COMMENT_SEQ",
            sequenceName = "COMMENT_SEQ", allocationSize = 10)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PARENT_ID", nullable = true)
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
    private Timestamp date;

    @Column(name = "DELETED", nullable = false)
    private boolean deleted;

    @OneToMany(
            mappedBy = "parent",
            fetch = FetchType.EAGER
    )
    private Set<Comment> children = new LinkedHashSet<>();
}

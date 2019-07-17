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
@ToString(exclude = {"deleted", "date"})
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    @SequenceGenerator(name = "USER_SEQ",
            sequenceName = "USER_SEQ", allocationSize = 10)
    private long id;

    @Column(name = "LOGIN", nullable = false)
    private String login;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "IS_ADMIN", nullable = false)
    private boolean admin;

    @Column(name = "DATE", nullable = false)
    private Timestamp date;

    @Column(name = "DELETED", nullable = false)
    private boolean deleted;

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.EAGER
    )
    private Set<Post> posts = new LinkedHashSet<>();

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.EAGER
    )
    private Set<Comment> comments = new LinkedHashSet<>();

}

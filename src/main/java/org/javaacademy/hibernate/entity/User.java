package org.javaacademy.hibernate.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "nickname")
    private String nickName;

    @OneToMany(mappedBy = "user")
    private List<Video> videos;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    public User(String nickName) {
        this.nickName = nickName;
        videos = new ArrayList<>();
        comments = new ArrayList<>();
    }
}

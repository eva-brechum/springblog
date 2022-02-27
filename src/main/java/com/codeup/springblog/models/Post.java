package com.codeup.springblog.models;

import javax.persistence.*;

@Entity
@Table(name= "posts")
public class Post {
    @Id
    @ManyToOne(optional = false)
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false)
    private String body;

    public void setId(GenerateValue id) {
        this.id = id;
    }

    public GenerateValue getId() {
        return id;
    }

    public Post() {};

    public Post(long id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

package com.training.fullstack.admin.model;

import javax.persistence.*;

@Entity
@Table(name = "skill")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "prerequisites")
    private String prerequisites;

    public Skill() {
    }

    public Skill(String title, String content, String prerequisites) {
        this.title = title;
        this.content = content;
        this.prerequisites = prerequisites;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

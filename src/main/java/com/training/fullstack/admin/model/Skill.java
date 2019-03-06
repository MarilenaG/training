package com.training.fullstack.admin.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "skill")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;
    @JsonProperty
    @Column(name = "title")
    private String title;
    @JsonProperty
    @Column(name = "content")
    private String content;
    @JsonProperty
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(String prerequisites) {
        this.prerequisites = prerequisites;
    }
}

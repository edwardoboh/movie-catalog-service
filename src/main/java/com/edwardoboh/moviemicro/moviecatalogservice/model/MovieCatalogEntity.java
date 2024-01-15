package com.edwardoboh.moviemicro.moviecatalogservice.model;

import jakarta.persistence.*;

@Entity
public class MovieCatalogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    @Column(name = "description")
    private String desc;

    private int rating;

    public MovieCatalogEntity(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public MovieCatalogEntity(String name, String desc, int rating) {
        this.name = name;
        this.desc = desc;
        this.rating = rating;
    }
}

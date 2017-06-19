package com.doapps.petservices.Network.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NriKe on 12/06/2017.
 */

public class PostResponse implements Serializable{
    private String name;
    private String description;
    private String type;
    private String id;
    private String userId;
    private String date;
    private String picture;
    private Photo image;
    private List<String> like = new ArrayList<>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Photo getImage() {
        return image;
    }

    public void setImage(Photo image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<String> getLike() {
        return like;
    }

    public void setLike(List<String> like) {
        this.like = like;
    }
}
